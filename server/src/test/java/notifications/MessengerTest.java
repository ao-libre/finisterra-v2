package notifications;

import network.EntityUpdateDTO;
import org.junit.jupiter.api.Test;
import shared.Position;
import system.MovementSystem;
import world.ServerWorld;

import java.util.Arrays;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import static com.artemis.E.E;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessengerTest {

    private ServerWorld world;
    private int entityId;
    private int entityIdWithPublicComponent;
    private EntityMessenger messenger;

    @Test
    public void entityChangesShouldBeNotifiedToMessenger() {
        givenAWorld();
        givenAnEntityMessenger();
        givenAnEntity();

        whenTheEntityChange();

        thenMessengerIsNotified();
    }

    @Test
    public void publicComponentChangesShouldBeNotifiedToChunkEntities() {
        givenAWorld();
        givenAnEntityMessenger();
        givenAnEntity();
        givenAnEntityWithPublicComponent();

        whenEntityWithPositionChanges();

        thenEntityShouldReceivePositionChange();
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
                        .anyMatch(component -> component.getClass().equals(Position.class))
                ));
    }

    private void thenMessengerIsNotified() {
        assertTrue(messenger.getSentEvents(entityId).isPresent());
        assertEquals(messenger.getSentEvents(entityId).get().size(), 1);
    }

    private void whenTheEntityChange() {
        E(entityId).sharedComponent(false);
        world.process();
    }

    private void whenEntityWithPositionChanges() {
        world.getSystem(MovementSystem.class).moveEntity(E(entityId), new Position(5, 4));
        world.process();
    }

    private void givenAnEntityMessenger() {
        messenger = new EntityMessenger(world);
    }

    private void givenAnEntity() {
        entityId = messenger.register("fakeId");
        E(entityId).sharedComponent();
        E(entityId).positionY(6).positionX(6);
    }

    private void givenAnEntityWithPublicComponent() {
        entityIdWithPublicComponent = messenger.register("otherFakeId");
        E(entityIdWithPublicComponent).positionX(5).positionY(5);
    }

    private void givenAWorld() {
        world = new ServerWorld();
    }


}
