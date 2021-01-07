package screens;

import game.GameContext;

import static screens.ScreenEvent.*;

public class LoadingScreen extends ContextScreen {

	public LoadingScreen(GameContext context) {
		super(context);
	}

	@Override
	public void show() {
		getContext().fire(LOADING_START);
		// load getContext().getAssetManager();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		if (getContext().getAssetManager().update()) {
			getContext().fire(LOADING_FINISH);
		}
	}

	@Override
	public void dispose() {
		// TODO
	}
}