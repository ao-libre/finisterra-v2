package notifications;

import com.artemis.ChangeRegistry;
import com.artemis.World;
import org.junit.jupiter.api.Test;
import world.ServerWorld;

import static com.artemis.E.E;
import static org.junit.jupiter.api.Assertions.*;

public class ChangeNotificationTest {

    private int entityId;
    private World world;
    private ChangeRegistry changeRegistry;

    @Test
    public void removedComponentsAreRegistered() {
        givenAWorld();
        givenAnEntityWithComponents();
        givenAChangeRegistry();

        whenRemoveComponents();

        thenRemovedComponentsAreRegistered();
    }

    @Test
    public void modifiedComponentsAreMarked() {
        givenAWorld();
        givenAnEntityWithComponents();
        givenAChangeRegistry();

        whenModifyComponents();

        thenModifiedComponentsAreMarked();
    }

    @Test
    public void cleanRegistryOnWorldProcess() {
        givenAWorld();
        givenAChangeRegistry();
        givenAnEntityWithModifiedComponents();

        whenProcessWorld();

        thenNoChangesAreRegistered();
    }

    private void thenRemovedComponentsAreRegistered() {
        assertEquals(2, changeRegistry.getRemoved().get(entityId).size());
    }

    private void thenModifiedComponentsAreMarked() {
        assertEquals(1, changeRegistry.getMarked().get(entityId).size());
    }

    private void thenNoChangesAreRegistered() {
        assertTrue(changeRegistry.getMarked().isEmpty());
        assertTrue(changeRegistry.getRemoved().isEmpty());
    }

    private void whenModifyComponents() {
        E(entityId).fooValue(1);
    }

    private void whenRemoveComponents() {
        E(entityId)
                .removeFoo()
                .removeServerComponent();
    }

    private void whenProcessWorld() {
        world.process();
    }

    private void givenAnEntityWithModifiedComponents() {
        E(entityId).fooValue(1);
    }

    private void givenAChangeRegistry() {
        changeRegistry = this.world.getSystem(ChangeRegistry.class);
    }

    private void givenAnEntityWithComponents() {
        entityId = this.world.create();
        E(entityId).serverComponent().foo();
    }

    private void givenAWorld() {
        this.world = new ServerWorld();
        this.world.process();
    }
}
