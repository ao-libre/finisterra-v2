package system;

import com.artemis.BaseSystem;
import com.artemis.Component;
import network.EntityUpdate;
import utils.Pool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.artemis.E.E;

public class NetworkSystem extends BaseSystem {

    private final Pool<EntityUpdate> updatePool = new Pool<>(EntityUpdate.class);
    private final Map<Integer, EntityUpdate> updates = new HashMap<>();

    @Override
    protected void processSystem() {
        updates.forEach(this::addUpdate);
    }

    private void addUpdate(Integer integer, EntityUpdate entityUpdate) {
        E(integer).entity().edit().add(entityUpdate.toDTO());
        updatePool.free(entityUpdate);
    }

    public void addChanges(int entityId, Set<Component> components) {
        updates.computeIfAbsent(entityId, updatePool.obtain())
                .update(components);
    }

    public void addRemoved(int entityId, Set<Class<? extends Component>> components) {
        updates.computeIfAbsent(entityId, updatePool.obtain())
                .remove(components);
    }

}
