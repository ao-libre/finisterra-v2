package system;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import communication.Messenger;
import utils.Pool;
import utils.Poolable;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Wire
public class OutputProcessor extends BaseSystem {

    private final Set<Messenger> messengers = new HashSet<>();

    private final Pool<PoolableQueue> queues = new Pool<>(PoolableQueue.class, 500);
    private final Map<Integer, PoolableQueue> worldEvents = new HashMap<>();

    public void register(Messenger messenger) {
        messengers.add(messenger);
    }

    public void unregister(Messenger messenger) {
        messengers.remove(messenger);
    }

    public void push(int id, Object event) {
        worldEvents.computeIfAbsent(id, (entityId) -> queues.obtain()).queue.add(event);
    }

    @Override
    protected void processSystem() {
        messengers.forEach(
                messenger -> worldEvents.forEach(
                        (entityId, events) -> messenger.send(entityId, events.queue.toArray())
                )
        );
        worldEvents.clear();
    }

    public static class PoolableQueue extends Poolable {

        private final ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<>();

        public PoolableQueue() {
        }

        @Override
        protected void reset() {
            queue.clear();
        }
    }
}
