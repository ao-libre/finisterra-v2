package system;

import annotations.Public;
import com.artemis.BaseSystem;
import com.artemis.Component;
import com.artemis.EBag;
import com.artemis.annotations.Wire;
import com.artemis.utils.Bag;
import network.EntityUpdate;
import network.EntityUpdateDTO;
import utils.Pool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.artemis.E.E;

@Wire
public class EntityUpdateSystem extends BaseSystem {

    private OutputProcessor outputProcessor;
    private ChunkSystem chunkSystem;

    private final Pool<EntityUpdate> updatePool = new Pool<>(EntityUpdate.class);
    private final Map<Integer, EntityUpdate> publicUpdates = new HashMap<>();
    private final Map<Integer, EntityUpdate> privateUpdates = new HashMap<>();

    @Override
    protected void processSystem() {
        publicUpdates.values().forEach(this::sendPublicUpdate);
        privateUpdates.values().forEach(this::sendPrivateUpdate);

        publicUpdates.clear();
        privateUpdates.clear();
    }

    private void sendPublicUpdate(EntityUpdate entityUpdate) {
        int entityId = entityUpdate.getEntityId();
        String chunkId = chunkSystem.getChunkId(E(entityId).getPosition());
        EBag nearEntities = chunkSystem.getNearEntities(chunkId);
        EntityUpdateDTO entityUpdateDTO = entityUpdate.toDTO();
        nearEntities.forEach(entity -> outputProcessor.push(entity.id(), entityUpdateDTO));
        updatePool.free(entityUpdate);
    }

    private void sendPrivateUpdate(EntityUpdate entityUpdate) {
        outputProcessor.push(entityUpdate.getEntityId(), entityUpdate.toDTO());
        updatePool.free(entityUpdate);
    }

    private EntityUpdate createEntityUpdate(Integer id) {
        EntityUpdate update = updatePool.obtain();
        update.setEntityId(id);
        return update;
    }

    public void addChanges(int entityId, Set<Component> components) {
        components.forEach(component -> {
            Map<Integer, EntityUpdate> updates;
            if (component.getClass().isAnnotationPresent(Public.class)) {
                updates = publicUpdates;
            } else {
                updates = privateUpdates;
            }
            updates.computeIfAbsent(entityId, this::createEntityUpdate).update(component);
        });
    }

    public void addRemoved(int entityId, Set<Class<? extends Component>> components) {
        components.forEach(component -> {
            Map<Integer, EntityUpdate> updates;
            if (component.isAnnotationPresent(Public.class)) {
                updates = publicUpdates;
            } else {
                updates = privateUpdates;
            }
            updates.computeIfAbsent(entityId, this::createEntityUpdate).remove(component);
        });
    }

    public void sendEntity(int receiver, int entity) {
        Bag<Component> components = E(entity).entity().getComponents(new Bag<>());
        Set<Component> publicComponents = new HashSet<>();
        components.forEach(component -> {
            if (component.getClass().isAnnotationPresent(Public.class)) {
                publicComponents.add(component);
            }
        });
        EntityUpdate update = createEntityUpdate(entity);
        update.update(publicComponents);
        outputProcessor.push(receiver, update.toDTO());
        updatePool.free(update);
    }
}
