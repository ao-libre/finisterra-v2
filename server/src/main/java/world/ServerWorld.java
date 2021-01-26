package world;

import com.artemis.*;
import com.artemis.managers.GroupManager;
import communication.Messenger;
import net.mostlyoriginal.api.event.common.EventSystem;
import network.RegisterEvent;
import system.*;

import java.util.HashMap;

public class ServerWorld extends World {

    public ServerWorld() {
        super(ServerWorldConfiguration.build());
    }

    private static class ServerWorldConfiguration {

        static WorldConfiguration build() {
            WorldConfigurationBuilder builder = new WorldConfigurationBuilder();
            return builder
                    .with(new InputProcessor(new HashMap<>()))
                    .with(new EventSystem())
                    .with(new SuperMapper())
                    .with(new ChunkSystem())
                    .with(new MovementSystem())
                    .with(new ChangeNotifierSystem())
                    .with(new EntityUpdateSystem())
                    .with(new OutputProcessor())
                    .with(new GroupManager())
                    .with(new ChangeRegistry())
                    .build();
        }
    }

    public int register(RegisterEvent event) {
        return create();
    }

    public void receive(int entityId, Object event) {
        getSystem(InputProcessor.class).push(entityId, event);
    }

    public void subscribe(Messenger messenger) {
        getSystem(OutputProcessor.class).register(messenger);
    }

    public void unsubscribe(Messenger messenger) {
        getSystem(OutputProcessor.class).unregister(messenger);
    }
}
