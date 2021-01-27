package events;

import arc.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AppEventBus {
    private final Map<AppEvent, Set<Runnable>> eventConsumers = new HashMap<>();

    public void fire(AppEvent event) {
        Log.info("Event fire: @", event.name());
        eventConsumers.computeIfAbsent(event, (t) -> new HashSet<>()).forEach(Runnable::run);
    }

    public void subscribe(AppEvent event, Runnable runnable) {
        eventConsumers.computeIfAbsent(event, (t) -> new HashSet<>()).add(runnable);
    }

}

