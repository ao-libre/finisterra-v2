import com.artemis.World;
import com.esotericsoftware.kryonetty.ServerEndpoint;
import com.esotericsoftware.kryonetty.ThreadedServerEndpoint;
import com.esotericsoftware.kryonetty.kryo.KryoNetty;
import world.ServerWorld;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

public class Launcher {

    private static final Logger LOGGER = Logger.getLogger("Main");
    private static final long TICK_TIME_MS = 20;

    public static void main(String[] args) throws InterruptedException {
        World world = new ServerWorld();
        KryoNetty kryoNetty = new KryoNetty();
        ServerEndpoint server = new ThreadedServerEndpoint(kryoNetty);
        server.start(7666);

        boolean runOnce = shouldRunOnce(args);
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
            if (runOnce) {
                server.close();
                running = false;
            }
        }
    }

    private static boolean shouldRunOnce(String[] args) {
        LOGGER.info("Program Args: " + Arrays.toString(args));
        return args.length > 0 && args[0].toLowerCase(Locale.ROOT).equals("once");
    }

}
