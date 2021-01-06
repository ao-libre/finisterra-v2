package world;

import com.artemis.*;
import system.ChangeNotifierSystem;
import system.NetworkSystem;

public class ServerWorld extends World {

    public ServerWorld() {
        super(ServerWorldConfiguration.build());
    }

    private static class ServerWorldConfiguration {

        static WorldConfiguration build() {
            WorldConfigurationBuilder builder = new WorldConfigurationBuilder();
            return builder
                    .with(new ChangeRegistry())
                    .with(new SuperMapper())
                    .with(new ChangeNotifierSystem())
                    .with(new NetworkSystem())
                    .build();
        }

    }
}