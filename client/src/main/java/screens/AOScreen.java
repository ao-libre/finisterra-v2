package screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.eskalon.commons.screen.ManagedScreen;
import events.AppEventBus;
import game.utils.Skins;

public class AOScreen extends ManagedScreen {
    protected AssetManager assetManager;
    protected AppEventBus appEventBus;

    protected Stage stage;
    protected Table mainTable;

    protected void generateUserInterface() {
        mainTable = new Table(Skins.CURRENT.get());
        mainTable.setFillParent(true);

        if (stage == null) stage = new Stage();
        stage.addActor(mainTable);
    }

    public void loadSync() {

    }

    @Override
    protected void create() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }
}