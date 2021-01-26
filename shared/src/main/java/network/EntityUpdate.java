package network;

import com.artemis.Component;
import utils.Poolable;

import java.util.*;
import java.util.stream.Collectors;

public class EntityUpdate extends Poolable {
    private int entityId;
    private final Set<Class<? extends Component>> toRemove = new HashSet<>();
    private final Set<Component> toUpdate = new HashSet<>();

    public EntityUpdate() {}

    public EntityUpdate(int entityId) {
        this.entityId = entityId;
    }

    public void update(Component... component) {
        update(Arrays.asList(component));
    }

    public void update(Collection<Component> components) {
        toUpdate.addAll(components);
        toRemove.removeAll(components.stream().map(Component::getClass).collect(Collectors.toSet()));
    }

    public void remove(Class<? extends Component> component) {
        toRemove.add(component);
        toUpdate.removeIf(updatedComponent -> updatedComponent.getClass().equals(component));
    }

    public void remove(Class<? extends Component>... components) {
        for (int i = 0; i < components.length; i++) {
            remove(components[i]);
        }
    }

    public void remove(Collection<Class<? extends Component>> components) {
        components.forEach(this::remove);
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityId() {
        return entityId;
    }

    public EntityUpdateDTO toDTO() {
        return new EntityUpdateDTO(entityId,
                toRemove.toArray(Class[]::new),
                toUpdate.toArray(Component[]::new));
    }

    @Override
    protected void reset() {
        entityId = -1;
        toRemove.clear();
        toUpdate.clear();
    }
}
