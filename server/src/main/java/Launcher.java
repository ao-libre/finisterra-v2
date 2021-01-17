import com.artemis.World;
import world.ServerWorld;

import java.util.logging.Logger;

public class Launcher {

    private static final Logger LOGGER = Logger.getLogger("Main");
    private static final long TICK_TIME_MS = 20;

    public static void main(String[] args) throws InterruptedException {
        World world = new ServerWorld();
        LOGGER.info("Server up and running");
        boolean running = true;
        long time = 0;
        while (running) {
            if (time < TICK_TIME_MS) {
                Thread.sleep(TICK_TIME_MS - time);
                time = 0;
            } else {
                // metric
            }
            float startTime = System.currentTimeMillis();
            world.process();
            time += System.currentTimeMillis() - startTime;
        }
    }

}