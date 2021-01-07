package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import screens.LoadingScreen;
import screens.LoginScreen;
import screens.ScreenEvent;
import screens.ScreenEventListener;

import java.util.List;

public class GameContext implements ScreenEventListener {

    private AssetManager assetManager;
    private Screen screen;
    private List<ScreenEventListener> listeners;

    public GameContext() {
        assetManager = new AssetManager();
        screen = new LoadingScreen(this);
        addScreenListener(this);
    }

    public Screen getScreen() {
        return screen;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void addScreenListener(ScreenEventListener listener) {
        listeners.add(listener);
    }

    public void fire(ScreenEvent event) {
        listeners.forEach(listener -> listener.on(event));
    }

    @Override
    public void on(ScreenEvent event) {
        switch (event) {
            case LOADING_FINISH:
                setScreen(new LoginScreen());
                break;
            case LOADING_START:
                break;
        }
    }

    public void setScreen(Screen screen) {
        if (this.screen != null) this.screen.hide();
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }
}
