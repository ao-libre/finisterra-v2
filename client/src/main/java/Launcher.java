import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import game.AOGame;

import java.util.Locale;

public class Launcher {
    public static void main(String[] args) {

        // Startup configuration
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.useOpenGL3(true, 3, 2);
        config.setWindowedMode(1280, 720);
        config.useVsync(false);
        config.setMaximized(false);

        new Lwjgl3Application(new AOGame(), config);
    }

    private static boolean shouldRunOnce(String[] args) {
        return args.length > 0 && args[0].toLowerCase(Locale.ROOT).equals("once");
    }
}