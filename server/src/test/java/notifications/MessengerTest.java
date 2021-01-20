package notifications;

import org.junit.jupiter.api.Test;
import world.ServerWorld;

import static com.artemis.E.E;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessengerTest {

    private ServerWorld world;
    private int entityId;
    private EntityMessenger messenger;

    @Test
    public void entityChangesShouldBeNotifiedToMessenger() {
        givenAWorld();
        givenAnEntityMessenger();

        whenTheEntityChange();

        thenMessengerIsNotified();
    }

    private void thenMessengerIsNotified() {
        assertEquals(messenger.getSentEvents().size(), 1);
    }

    private void whenTheEntityChange() {
        E(entityId).sharedComponent(false);
        world.process();
    }

    private void givenAnEntityMessenger() {
        messenger = new EntityMessenger(world);
        entityId = messenger.register("fakeId");
        world.process();
        E(entityId).sharedComponent();
    }

    private void givenAWorld() {
        world = new ServerWorld();
        world.process();
    }



}
