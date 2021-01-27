package notifications;

import com.artemis.Armor;
import network.EntityUpdateDTO;
import org.junit.jupiter.api.Test;
import world.ServerWorld;

import java.util.*;
import java.util.stream.Collectors;

import static com.artemis.E.E;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessengerTest {

    private ServerWorld world;
    private EntityMessenger messenger;

    private int entityId;
    private int entityIdWithPublicComponent;
    private Set<Integer> entitiesInChunk;

    @Test
    public void entityChangesShouldBeNotifiedToMessenger() {
        givenAWorld();
        givenAnEntity();
        givenAnEntityMessenger();

        whenTheEntityChange();

        thenMessengerIsNotified();
    }

    @Test
    public void publicComponentChangesShouldBeNotifiedToChunkEntities() {
        givenAWorld();
        givenAnEntity();
        givenAnEntityWithPublicComponent();
        givenAnEntityMessenger();

        whenEntityPublicComponentChanges();

        thenEntityShouldReceivePositionChange();
    }

    @Test
    public void entitiesShouldBeTransferredWhenEntityEntersAChunk() {
        givenAWorld();
        givenAnEntity();
        givenAFewEntitiesInSameChunk();
        givenAnEntityMessenger();

        whenEntityEntersAChunk();

        thenAllEntitiesAreTransferredToEntity();
    }

    private void thenEntityShouldReceivePositionChange() {
        Optional<Queue<Object>> sentEvents = messenger.getSentEvents(entityId);
        assertTrue(sentEvents.isPresent());
        Queue<Object> messagesForEntityId = sentEvents.get();
        Set<Object> packets = messagesForEntityId.stream()
                .map(Object[].class::cast)
                .flatMap(Arrays::stream)
                .collect(Collectors.toSet());
        assertTrue(packets.stream()
                .filter(EntityUpdateDTO.class::isInstance)
                .map(EntityUpdateDTO.class::cast)
                .filter(entityUpdateDTO -> entityUpdateDTO.getEntityId() == entityIdWithPublicComponent)
                .anyMatch(entityUpdateDTO -> entityUpdateDTO.getToUpdate().stream()
                        .anyMatch(component -> component.getClass().equals(Armor.class))
                ));
    }

    private void thenMessengerIsNotified() {
        assertTrue(messenger.getSentEvents(entityId).isPresent());
        assertEquals(messenger.getSentEvents(entityId).get().size(), 1);
    }

    private void thenAllEntitiesAreTransferredToEntity() {
        Optional<Queue<Object>> sentEvents = messenger.getSentEvents(entityId);
        assertTrue(sentEvents.isPresent());
        Queue<Object> updates = sentEvents.get();
        assertTrue(updates.stream()
                .map(Object[].class::cast)
                .flatMap(Arrays::stream)
                .allMatch(EntityUpdateDTO.class::isInstance)
        );
        Set<EntityUpdateDTO> entityUpdates = updates.stream()
                .map(Object[].class::cast)
                .flatMap(Arrays::stream)
                .map(EntityUpdateDTO.class::cast)
                .collect(Collectors.toSet());
        entitiesInChunk.forEach(entityInChunk -> {
            Optional<EntityUpdateDTO> update = entityUpdates
                    .stream()
                    .filter(entityUpdateDTO -> entityUpdateDTO.getEntityId() == entityInChunk)
                    .findFirst();
            assertTrue(update.isPresent());
            assertTrue(update.get().getToUpdate().stream()
                    .anyMatch(component -> component.getClass().equals(Armor.class))
            );
        });
    }

    private void whenTheEntityChange() {
        E(entityId).sharedComponent(false);
        world.process();
    }

    private void whenEntityPublicComponentChanges() {
        E(entityIdWithPublicComponent).armorIndex(1);
        world.process();
    }

    private void whenEntityEntersAChunk() {
        E(entityId).positionY(15).positionX(15);
        world.process();
    }

    private void givenAnEntityMessenger() {
        world.process();
        messenger = new EntityMessenger(world);
    }

    private void givenAnEntity() {
        entityId = world.create();
        E(entityId).sharedComponent();
        E(entityId).positionY(6).positionX(6);
    }

    private void givenAnEntityWithPublicComponent() {
        entityIdWithPublicComponent = world.create();
        E(entityIdWithPublicComponent).positionX(5).positionY(5).armorIndex(1);
    }

    private void givenAWorld() {
        world = new ServerWorld();
    }

    private void givenAFewEntitiesInSameChunk() {
        entitiesInChunk = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            int entityId = world.create();
            E(entityId).positionX(10+i).positionY(11).armorIndex(i); // in chunk 1@1
            entitiesInChunk.add(entityId);
        }
    }

}
