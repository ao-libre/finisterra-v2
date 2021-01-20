package system;

import com.artemis.BaseSystem;
import utils.Pool;
import utils.Poolable;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class InputProcessor extends BaseSystem {

    private final  Pool<InputEvent> inputEventPool = new Pool<>(InputEvent.class);
    private final  Queue<InputEvent> events = new ConcurrentLinkedQueue<>();
    private final Map<Class<? extends InputEvent>, Consumer<InputEvent>> processors;

    public InputProcessor(Map<Class<? extends InputEvent>, Consumer<InputEvent>> processors) {
        this.processors = processors;
    }

    // This method will be call asynchronous
    public void push(int id, Object event) {
        InputEvent inputEvent = inputEventPool.obtain();
        inputEvent.entityId = id;
        inputEvent.object = event;
        events.add(inputEvent);
    }

    @Override
    protected void processSystem() {
        events.forEach(event ->
                Optional.ofNullable(processors.get(event.getClass()))
                        .ifPresent(processor -> processor.accept(event)));
    }

    private static class InputEvent extends Poolable {
        int entityId;
        Object object;

        public InputEvent(int entityId, Object object) {
            this.entityId = entityId;
            this.object = object;
        }

        @Override
        protected void reset() {
            entityId = 0;
            object = null;
        }
    }
}
