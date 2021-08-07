package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import events.AppEvent;
import events.AppEventBus;
import game.ui.WidgetFactory;
import game.utils.Resources;
import game.utils.Skins;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class LoadingScreen extends AOScreen {
    private final AssetManager assetManager;
    private final AppEventBus appEventBus;

    // UI components
    private Texture progressBar;
    private Texture progressBarKnob;
    private ProgressBar progress;
	
	// UI - dependencies
    private Texture backgroundImage = new Texture(Gdx.files.internal(Resources.GAME_IMAGES_PATH + "background.jpg"));
    private SpriteDrawable backgroundSprite = new SpriteDrawable(new Sprite(backgroundImage));

    // Profiling & debugging
    private static final Logger LOG = Logger.getLogger("LoadingScreen");
    private long start;

    public LoadingScreen(AppEventBus appEventBus, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.appEventBus = appEventBus;
		this.stage = new Stage(
                new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()),
                new ArrayTextureSpriteBatch(2048, 10));
    }
	
    @Override
    protected void generateUserInterface() {
        super.generateUserInterface();

        // UI - Background Picture
        Texture backgroundImage = new Texture(Gdx.files.internal(Resources.GAME_IMAGES_PATH + "background.jpg"));
        SpriteDrawable backgroundSprite = new SpriteDrawable(new Sprite(backgroundImage));

        // UI - Main Table
        super.generateUserInterface();
        this.mainTable.setBackground(backgroundSprite);

        // UI - Progress Bar
        Table table = new Table();
        this.progress = WidgetFactory.createLoadingProgressBar();
        table.add(progress).width(500).expandX();
        this.mainTable.add(table).expand();

        // Profiling
        this.start = TimeUtils.nanoTime();
    }

    @Override
    public void show() {
        this.appEventBus.fire(AppEvent.LOADING_STARTED);
        generateUserInterface();
        loadAssets();
    }

    @Override
    public void render(float delta) {
        if (assetManager.update()) {
			// TODO: Esto causa "flickering" en la pantalla
            LOG.info("Loading time " + Math.round((TimeUtils.nanoTime() - this.start) * 1.0E-9) + "s");
            //this.appEventBus.fire(AppEvent.LOADING_FINISHED);
        }

        // display loading information
        float varProgress = assetManager.getProgress();
        this.progress.setValue(varProgress * 100);

        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
		this.backgroundImage.dispose();
    }

    /*
     * From this point forward, the folowing methods are ALL AssetManager-related
     */

    // Loads almost all the required assets
    private void loadAssets() {
        // loadAtlas();
    }

    private void loadAtlas() {
        Reflections reflections = new Reflections("atlas", new ResourcesScanner());
        Set<String> atlases = reflections.getResources(Pattern.compile(".*\\.atlas"));
        atlases.forEach(atlas -> assetManager.load(atlas, TextureAtlas.class));
    }
}
