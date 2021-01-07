package screens;

import com.badlogic.gdx.ScreenAdapter;
import game.GameContext;

public class ContextScreen extends ScreenAdapter {

    private GameContext context;

    public ContextScreen(GameContext context) {
        this.context = context;
    }

    public GameContext getContext() {
        return context;
    }

}
