package screens;

import arc.Core;
import arc.Events;
import arc.scene.Scene;
import arc.scene.ui.layout.Table;
import events.EventType;

public class LoadingScreen extends Screen {
    Scene scene;

    public LoadingScreen() {
        scene = new Scene();
        Table table = new Table();
        scene.add(table);
    }

    @Override
    public void loadSync() {
        // @todo load assets for this screen
    }

    @Override
    public void init() {
        Events.fire(EventType.Loading.STARTED);
        Core.scene = scene;
        load();
    }

    private void load() {
    }

    @Override
    public void update() {
        if (Core.assets.update()) {
            Events.fire(EventType.Loading.FINISHED);
        }
    }
}
