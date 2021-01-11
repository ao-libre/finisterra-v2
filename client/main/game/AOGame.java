package game;

import arc.ApplicationListener;
import arc.Core;
import arc.Events;
import arc.assets.AssetManager;
import arc.graphics.Color;
import arc.graphics.Gl;
import arc.util.Log;
import events.EventType.ChangeScreen;
import events.EventType.Client;
import events.EventType.Game;
import events.EventType.Loading;
import screens.LoadingScreen;
import screens.Screen;
import screens.Screens;

public class AOGame implements ApplicationListener {

    private Screen screen;

    public AOGame() {
        Events.fire(Client.CREATE);
    }

    @Override
    public void init() {
        Log.info("[AOGame] init()");

        //debug GL information
        Log.info("[GL] Version: @", Core.graphics.getGLVersion());
        Log.info("[GL] Max texture size: @", Gl.getInt(Gl.maxTextureSize));
        Log.info("[GL] Using @ context.", Core.gl30 != null ? "OpenGL 3" : "OpenGL 2");
        Log.info("[JAVA] Version: @", System.getProperty("java.version"));

        Core.assets = new AssetManager();
        screen = new LoadingScreen();
        registerEvents();
        screen.init();
        Events.fire(Client.INIT);
    }

    private void registerEvents() {
        Events.run(Loading.FINISHED, () -> toScreen(Screens.LOGIN));
        Events.run(Game.LOG_OUT, () -> toScreen(Screens.LOGIN));
        Events.run(Game.LOG_IN, () -> toScreen(Screens.ACCOUNT));
    }

    private void toScreen(Screens screen) {
        Log.info("[AOGame] Moving to screen @", screen.get().getClass().getName());
        this.screen = screen.get();
        this.screen.init();
        Core.assets.load(this.screen);
        Events.fire(new ChangeScreen(screen));
    }

    @Override
    public void update() {
        Core.graphics.clear(Color.black);
        Log.info("FPS: @", Core.graphics.getFramesPerSecond());
        Events.fire(Client.UPDATE);
        screen.update();
    }
}