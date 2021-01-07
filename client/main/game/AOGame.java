package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class AOGame extends Game {

    private GameContext gameContext;

    public AOGame() {
        this.gameContext = new GameContext();
    }

    @Override
    public void create() {
        setScreen(gameContext.getScreen());
    }

    @Override
    public void render() {
        if (gameContext.getScreen() != null) gameContext.getScreen().render(Gdx.graphics.getDeltaTime());
    }
}