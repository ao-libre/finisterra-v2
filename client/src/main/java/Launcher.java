import arc.Application;
import arc.Core;
import arc.backend.sdl.SdlApplication;
import arc.backend.sdl.SdlConfig;
import game.AOGame;

import java.util.Arrays;
import java.util.Locale;

public class Launcher {
    public static void main(String[] args) {
        AOGame game = new AOGame();
        SdlConfig config = new SdlConfig();
        config.title = "Finisterrax";
//        config.decorated = false;
        config.gl30 = false;
        config.vSyncEnabled = false;
        config.stencil = 1;
        config.maximized = true;
        if (shouldRunOnce(args)) {
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    Application app = Core.app;
                    app.post(app::exit);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        new SdlApplication(game, config);
    }

    private static boolean shouldRunOnce(String[] args) {
        return args.length > 0 && args[0].toLowerCase(Locale.ROOT).equals("once");
    }
}