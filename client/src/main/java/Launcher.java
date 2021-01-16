import arc.backend.sdl.SdlApplication;
import arc.backend.sdl.SdlConfig;
import game.AOGame;

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

        new SdlApplication(game, config);
    }
}