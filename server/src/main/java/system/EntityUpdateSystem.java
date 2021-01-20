package system;

import com.artemis.BaseSystem;
import com.artemis.Component;
import com.artemis.annotations.Wire;
import network.EntityUpdate;
import utils.Pool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Wire
public class EntityUpdateSystem extends BaseSystem {

    private OutputProcessor outputProcessor;

    private final Pool<EntityUpdate> updatePool = new Pool<>(EntityUpdate.class);
    private final Map<Integer, EntityUpdate> updates = new HashMap<>();

    @Override
    protected void processSystem() {
        updates.forEach(this::addUpdate);
    }

    private void addUpdate(Integer integer, EntityUpdate entityUpdate) {
        outputProcessor.push(integer, entityUpdate.toDTO());
        updatePool.free(entityUpdate);
    }

    public void addChanges(int entityId, Set<Component> components) {
        updates.computeIfAbsent(entityId, (id) -> updatePool.obtain())
                .update(components);
    }

    public void addRemoved(int entityId, Set<Class<? extends Component>> components) {
        updates.computeIfAbsent(entityId, (id) -> updatePool.obtain())
                .remove(components);
    }

}
