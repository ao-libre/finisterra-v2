package system;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import communication.Messenger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Wire
public class OutputProcessor extends BaseSystem {

    private final Set<Messenger> messengers = new HashSet<>();

    private final Map<Integer, Queue<WorldEvent>> worldEvents = new HashMap<>();

    public void register(Messenger messenger) {
        messengers.add(messenger);
    }

    public void unregister(Messenger messenger) {
        messengers.remove(messenger);
    }

    public void push(int id, Object event) {
        worldEvents.computeIfAbsent(id, (entityId) -> new ConcurrentLinkedQueue<>()).add(new WorldEvent(id, event));
    }

    @Override
    protected void processSystem() {
        worldEvents.forEach((entityId, events) ->
                messengers.forEach((messenger -> messenger.send(entityId, events.toArray()))));
        worldEvents.clear();
    }

    private static class WorldEvent {
        int entityId;
        Object object;

        public WorldEvent(int entityId, Object object) {
            this.entityId = entityId;
            this.object = object;
        }
    }
}
