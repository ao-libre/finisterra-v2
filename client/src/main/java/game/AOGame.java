package game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import events.AppEventBus;
import screens.AOScreen;
import screens.LoadingScreen;
import screens.LoginScreen;
import screens.Screens;

import static events.AppEvent.*;

public class AOGame extends ApplicationAdapter {

    private final AppEventBus appEventBus;
    private final AssetManager assetManager;
    private AOScreen screen;

    public AOGame() {
        appEventBus = new AppEventBus();
        assetManager = new AssetManager();
    }

    @Override
    public void create() {
//        Log.info("[AOGame] init()");

        //debug GL information
//        Log.info("[GL] Version: @", Core.graphics.getGLVersion());
//        Log.info("[GL] Max texture size: @", Gl.getInt(Gl.maxTextureSize));
//        Log.info("[GL] Using @ context.", Core.gl30 != null ? "OpenGL 3" : "OpenGL 2");
//        Log.info("[JAVA] Version: @", System.getProperty("java.version"));

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
//        Log.info("[AOGame] Moving to screen @", screen.name());
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
        this.screen.show();
    }

    @Override
    public void render() {
//        Core.graphics.clear(Color.black);
        // Log.info("FPS: @", Core.graphics.getFramesPerSecond());
        screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}