package screens;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import events.AppEvent;
import events.AppEventBus;
import game.handlers.AssetManager;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.util.Set;
import java.util.regex.Pattern;

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
        loadAssets();
    }

    @Override
    public void render(float delta) {
        if (assetManager.update()) {
            this.appEventBus.fire(AppEvent.LOADING_FINISHED);
        }
    }

    // Loads almost all the required assets
    private void loadAssets() {
        loadAtlas();
    }

    private void loadAtlas() {
        Reflections reflections = new Reflections("atlas", new ResourcesScanner());
        Set<String> atlases = reflections.getResources(Pattern.compile(".*\\.atlas"));
        atlases.forEach(atlas -> assetManager.load(atlas, TextureAtlas.class));
    }
}
