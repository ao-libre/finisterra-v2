package game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.transition.ScreenTransition;
import events.AppEventBus;
import screens.AOScreen;
import screens.LoadingScreen;
import screens.LoginScreen;
import screens.Screens;

import java.util.logging.Logger;

import static events.AppEvent.*;

public class AOGame extends ManagedGame<AOScreen, ScreenTransition> {

    private static final Logger LOG = Logger.getLogger("AOGame");

    private final AppEventBus appEventBus;
    private final AssetManager assetManager;

    public AOGame() {
        appEventBus = new AppEventBus();
        assetManager = new AssetManager();
    }

    @Override
    public void create() {
        LOG.info("Initializing AOGame");
		super.create();
		
        registerEvents();
        setLoaders();

        // Register available screens to display
        screenManager.addScreen(Screens.LOADING.name(), new LoadingScreen(appEventBus, assetManager));
        screenManager.addScreen(Screens.LOGIN.name(), new LoginScreen(appEventBus, assetManager));
        screenManager.addScreen(Screens.GAME.name(), new GameScreen(appEventBus, assetManager));

        this.appEventBus.fire(CLIENT_INIT);
    }

    private void registerEvents() {
		appEventBus.subscribe(CLIENT_INIT, () -> toScreen(Screens.LOADING));
        appEventBus.subscribe(LOADING_FINISHED, () -> toScreen(Screens.LOGIN));
        appEventBus.subscribe(LOG_OUT, () -> toScreen(Screens.LOGIN));
        appEventBus.subscribe(LOG_IN, () -> toScreen(Screens.ACCOUNT));
    }

    private void toScreen(Screens screen) {
		// Log.info("[AOGame] Moving to screen @", screen.name());
        switch (screen) {
            case LOADING:
                screenManager.pushScreen(Screens.LOADING.name(), null);
                break;
            case LOGIN:
                screenManager.pushScreen(Screens.LOGIN.name(), null);
                break;
			case GAME:
                screenManager.pushScreen(Screens.GAME.name(), null);
                break;
            default:
                break;
        }
        this.screen.show();
    }

    @Override
    public void render() {
		// Core.graphics.clear(Color.black);
        // Log.info("FPS: @", Core.graphics.getFramesPerSecond());
        screenManager.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
		super.dispose();
        screenManager.dispose();
        assetManager.dispose();
    }
}