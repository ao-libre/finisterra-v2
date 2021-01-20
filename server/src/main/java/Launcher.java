import java.util.logging.Level;
import java.util.logging.Logger;

public class Launcher {

    private static final Logger LOGGER = Logger.getLogger("Launcher");

    public static void main(String[] args) throws InterruptedException {
        try {
            Application.run(args);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Shutting down...", e);
        }
    }

}
