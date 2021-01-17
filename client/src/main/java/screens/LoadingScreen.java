package screens;

import arc.Core;
import arc.assets.AssetManager;
import arc.scene.Scene;
import arc.scene.ui.layout.Table;
import events.AppEvent;
import events.AppEventBus;

public class LoadingScreen extends Screen {
    private final AssetManager assetManager;
    Scene scene;

    private final AppEventBus appEventBus;

    public LoadingScreen(AppEventBus appEventBus, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.appEventBus = appEventBus;
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
        this.appEventBus.fire(AppEvent.LOADING_STARTED);
        Core.scene = scene;
    }

    @Override
    public void update() {
        if (assetManager.update()) {
            this.appEventBus.fire(AppEvent.LOADING_FINISHED);
        }
    }
}
