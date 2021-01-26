package system;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import communication.Messenger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Wire
public class OutputProcessor extends BaseSystem {

    private final Set<Messenger> messengers = new HashSet<>();

    private final Map<Integer, Queue<Object>> worldEvents = new HashMap<>();

    public void register(Messenger messenger) {
        messengers.add(messenger);
    }

    public void unregister(Messenger messenger) {
        messengers.remove(messenger);
    }

    public void push(int id, Object event) {
        worldEvents.computeIfAbsent(id, (entityId) -> new ConcurrentLinkedQueue<>()).add(event);
    }

    @Override
    protected void processSystem() {
        messengers.forEach((messenger ->
                worldEvents.forEach((entityId, events) ->
                        messenger.send(entityId, events.toArray()))));
        worldEvents.clear();
    }

}
