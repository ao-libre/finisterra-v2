package events;

import arc.util.Log;

import java.util.*;

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

