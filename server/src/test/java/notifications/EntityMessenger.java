package notifications;

import communication.Messenger;
import network.RegisterEvent;
import world.ServerWorld;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EntityMessenger implements Messenger {

    private final Map<Integer, Queue<Object>> receivedEvents = new HashMap<>();
    private final Map<Integer, Queue<Object>> sentEvents = new HashMap<>();
    private final Map<String, Integer> entities = new HashMap<>();
    private final ServerWorld world;

    public EntityMessenger(ServerWorld world) {
        this.world = world;
        this.world.subscribe(this);
    }

    @Override
    public int register(String id) {
        int entityId = world.register(new RegisterEvent());
        entities.put(id, entityId);
        return entityId;
    }

    @Override
    public void receive(String id, Object object) {
        world.receive(entities.get(id), object);
    }

    @Override
    public void send(int id, Object object) {
        sentEvents.computeIfAbsent(id, (someId) -> new ConcurrentLinkedQueue<>()).add(object);
    }

    public Optional<Queue<Object>> getReceivedEvents(int id) {
        return Optional.ofNullable(receivedEvents.get(id));
    }

    public Optional<Queue<Object>> getSentEvents(int id) {
        return Optional.ofNullable(sentEvents.get(id));
    }
}
