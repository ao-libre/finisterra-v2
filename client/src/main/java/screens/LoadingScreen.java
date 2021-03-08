package screens;

import com.badlogic.gdx.assets.AssetManager;
import events.AppEvent;
import events.AppEventBus;

public class LoadingScreen extends AOScreen {
    private final AssetManager assetManager;


    private final AppEventBus appEventBus;

    public LoadingScreen(AppEventBus appEventBus, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.appEventBus = appEventBus;
    }

    @Override
    public void loadSync() {
        // @todo load assets for this screen

    }

    @Override
    public void show() {
        this.appEventBus.fire(AppEvent.LOADING_STARTED);
    }

    @Override
    public void render(float delta) {
        if (assetManager.update()) {
            this.appEventBus.fire(AppEvent.LOADING_FINISHED);
        }
    }
}
