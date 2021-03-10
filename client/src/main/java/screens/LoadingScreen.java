package screens;

import com.artemis.generator.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;
import com.brashmonkey.spriter.gdx.Drawer;
import com.brashmonkey.spriter.gdx.Loader;
import events.AppEvent;
import events.AppEventBus;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LoadingScreen extends AOScreen {
    private final AssetManager assetManager;

    Set<Player> players;
    ShapeRenderer renderer;
    SpriteBatch batch;
    Drawer drawer;
    Loader loader;
    OrthographicCamera cam;
    GLProfiler glProfiler = new GLProfiler(Gdx.graphics);
    Log log = new Log() {
        @Override
        public void info(String s) {
            System.out.println("INFO: " + s);
        }

        @Override
        public void error(String s) {
            System.out.println(s);
        }
    };

    private final AppEventBus appEventBus;

    public LoadingScreen(AppEventBus appEventBus, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.appEventBus = appEventBus;
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0, 0, 0f);
        cam.update();
        renderer.setProjectionMatrix(cam.combined);
        batch.setProjectionMatrix(cam.combined);
    }

    @Override
    public void loadSync() {
        glProfiler.enable();
        cam = new OrthographicCamera();
        cam.zoom = 1f;
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();

        // @todo load assets for this screen
        FileHandle handle = Gdx.files.internal("assets/character.scml");
        Data data = new SCMLReader(handle.read()).getData();

        loader = new Loader(data);
        loader.load(handle.file());

        drawer = new Drawer(loader, batch, renderer);
        players = new HashSet<>();

        final int height = Gdx.graphics.getHeight();
        final int width = Gdx.graphics.getWidth();
        final Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Player player = new Player(data.getEntity(0));
            Set<Entity.CharacterMap> maps = new HashSet<>();
            maps.add(data.getEntity(0).getCharacterMap("EYEBROW_" + (1 + random.nextInt(3))));
            maps.add(data.getEntity(0).getCharacterMap("HAIR_" + (2 + random.nextInt(5))));
            maps.add(data.getEntity(0).getCharacterMap("FACEHAIR_" + (1 + random.nextInt(2))));
            maps.add(data.getEntity(0).getCharacterMap("HEADGEAR_" + (2 + random.nextInt(5))));
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    maps.add(data.getEntity(0).getCharacterMap("NO_SHIELD"));
                } else {
                    maps.add(data.getEntity(0).getCharacterMap("SHIELD_2"));
                }
            }
            if (random.nextBoolean()) {
                maps.add(data.getEntity(0).getCharacterMap("NO_CAPE"));
            }
            if (random.nextBoolean()) {
                maps.add(data.getEntity(0).getCharacterMap("BODY_1"));
            }
            if (random.nextBoolean()) {
                maps.add(data.getEntity(0).getCharacterMap("NO_NECKLACE"));
            }
            if (random.nextBoolean()) {
                maps.add(data.getEntity(0).getCharacterMap("NO_BACKHAIR"));
            }
            if (random.nextBoolean()) {
                maps.add(data.getEntity(0).getCharacterMap("NO_COVER"));
            }

            player.characterMaps = maps.toArray(Entity.CharacterMap[]::new);
            player.scale(0.4f);
            player.setPosition(random.nextInt(width), random.nextInt(height));
            player.speed = 30;
            player.setAnimation(random.nextInt(12));
            players.add(player);
        }
    }

    @Override
    public void show() {
        this.appEventBus.fire(AppEvent.LOADING_STARTED);
    }

    @Override
    public void render(float delta) {
//        if (assetManager.update()) {
//            this.appEventBus.fire(AppEvent.LOADING_FINISHED);
//        }
        log.info("FPS: " + Gdx.graphics.getFramesPerSecond());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        players.forEach(Player::update);

        batch.begin();
        players.forEach(player -> drawer.draw(player));
        batch.end();

        log.info("SPRITEBATCH - RENDER CALLS: " + batch.renderCalls);
        log.info("GL PROFILER - TEXTURE BINDINGS: " + glProfiler.getTextureBindings());
        log.info("GL PROFILER - SHADER SWITCHES: "+ glProfiler.getShaderSwitches());
        log.info("GL PROFILER - VERTEX COUNT: " + glProfiler.getVertexCount().count);
        log.info("GL PROFILER - DRAW CALLS: " + glProfiler.getDrawCalls());
        log.info("GL PROFILER - CALLS: " + glProfiler.getCalls());
        glProfiler.reset();
    }
}
