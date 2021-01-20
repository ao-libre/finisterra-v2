package notifications;

import communication.Messenger;
import network.RegisterEvent;
import world.ServerWorld;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EntityMessenger implements Messenger {

    private Queue<Object> receivedEvents = new ConcurrentLinkedQueue<>();
    private Queue<Object> sentEvents = new ConcurrentLinkedQueue<>();
    private ServerWorld world;
    private int entityId;

    public EntityMessenger(ServerWorld world) {
        this.world = world;
        this.world.subscribe(this);
    }

    @Override
    public int register(String id) {
        return entityId = world.register(new RegisterEvent());
    }

    @Override
    public void receive(String id, Object object) {
        world.receive(entityId, object);
    }

    @Override
    public void send(int id, Object object) {
        if (id == entityId) {
            sentEvents.add(object);
        }
    }

    public Queue<Object> getReceivedEvents() {
        return receivedEvents;
    }

    public Queue<Object> getSentEvents() {
        return sentEvents;
    }
}
