import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import game.AOGame;

import java.util.Locale;

public class Launcher {
    public static void main(String[] args) {
        AOGame game = new AOGame();

        final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setDecorated(false);
        config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        config.useVsync(true);
        new Lwjgl3Application(game, config);


//        config.title = "Finisterrax";
//        config.decorated = false;
//        config.gl30 = false;
//        config.vSyncEnabled = false;
//        config.stencil = 1;
//        config.maximized = true;
//        if (shouldRunOnce(args)) {
//            new Thread(() -> {
//                try {
//                    Thread.sleep(2000);
//                    Application app = Core.app;
//                    app.post(app::exit);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//            return;
//        }
//        new SdlApplication(game, config);
    }

    private static boolean shouldRunOnce(String[] args) {
        return args.length > 0 && args[0].toLowerCase(Locale.ROOT).equals("once");
    }
}