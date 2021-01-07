import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import game.AOGame;

public class Launcher {
    public static void main(String[] args) {
        new Lwjgl3Application(new AOGame(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("finisterra");
        configuration.setWindowedMode(640, 480);
        return configuration;
    }
}