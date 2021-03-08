import com.esotericsoftware.kryonet.Server;
import world.ServerWorld;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Logger;

public class Application {

    private static final Logger LOGGER = Logger.getLogger("Main");
    private static final long TICK_TIME_MS = 20;
    private final ServerWorld world;

    private Application() {
        this.world = new ServerWorld();
    }

    public static void run(String[] args) throws InterruptedException, IOException {
        new Application().start(shouldRunOnce(args));
    }

    private void start(boolean runOnce) throws InterruptedException, IOException {
        Server server = new Server();
        server.bind(7666);
        server.start();
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
            long startTime = System.currentTimeMillis();
            world.process();
            time += System.currentTimeMillis() - startTime;
            if (runOnce) {
                server.dispose();
                running = false;
            }
        }
        System.exit(0);
    }

    private static boolean shouldRunOnce(String[] args) {
        LOGGER.info("Program Args: " + Arrays.toString(args));
        return args.length > 0 && args[0].toLowerCase(Locale.ROOT).equals("once");
    }

}
