package game;

import arc.ApplicationListener;
import arc.Core;
import arc.assets.AssetManager;
import arc.assets.loaders.TextureLoader;
import arc.files.Fi;
import arc.graphics.Color;
import arc.graphics.Gl;
import arc.graphics.Texture;
import arc.util.Log;
import events.AppEventBus;
import screens.LoadingScreen;
import screens.LoginScreen;
import screens.Screen;
import screens.Screens;

import static events.AppEvent.*;

public class AOGame implements ApplicationListener {

    private final AppEventBus appEventBus;
    private final AssetManager assetManager;
    private Screen screen;

    public AOGame() {
        appEventBus = new AppEventBus();
        assetManager = new AssetManager();
    }

    @Override
    public void init() {
        Log.info("[AOGame] init()");

        //debug GL information
        Log.info("[GL] Version: @", Core.graphics.getGLVersion());
        Log.info("[GL] Max texture size: @", Gl.getInt(Gl.maxTextureSize));
        Log.info("[GL] Using @ context.", Core.gl30 != null ? "OpenGL 3" : "OpenGL 2");
        Log.info("[JAVA] Version: @", System.getProperty("java.version"));

        registerEvents();
        setLoaders();

        appEventBus.fire(CLIENT_INIT);
        toScreen(Screens.LOADING);
    }

    private void setLoaders() {

    }

    private void registerEvents() {
        appEventBus.subscribe(LOADING_FINISHED, () -> toScreen(Screens.LOGIN));
        appEventBus.subscribe(LOG_OUT, () -> toScreen(Screens.LOGIN));
        appEventBus.subscribe(LOG_IN, () -> toScreen(Screens.ACCOUNT));
    }

    private void toScreen(Screens screen) {
        Log.info("[AOGame] Moving to screen @", screen.name());
        switch (screen) {
            case LOADING:
                this.screen = new LoadingScreen(appEventBus, assetManager);
                break;
            case LOGIN:
                this.screen = new LoginScreen();
                break;
            default:
                break;
        }
        this.screen.init();
        assetManager.load(this.screen);
    }

    @Override
    public void update() {
        Core.graphics.clear(Color.black);
        // Log.info("FPS: @", Core.graphics.getFramesPerSecond());
        screen.update();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}