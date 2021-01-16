package shared;

import com.artemis.Component;
import com.artemis.PooledComponent;

import java.util.Collection;
import java.util.List;

public class EntityUpdateDTO extends PooledComponent {
    private int entityId;
    private Class<? extends Component>[] toRemove;
    private Component[] toUpdate;

    public EntityUpdateDTO() {
    }

    public EntityUpdateDTO(int entityId) {
        this.entityId = entityId;
    }

    public EntityUpdateDTO(int entityId, Class<? extends Component>[] toRemove, Component[] toUpdate) {
        this(entityId);
        this.toRemove = toRemove;
        this.toUpdate = toUpdate;
    }

    public int getEntityId() {
        return entityId;
    }

    public Collection<Class<? extends Component>> getToRemove() {
        return List.of(toRemove);
    }

    public Collection<Component> getToUpdate() {
        return List.of(toUpdate);
    }

    @Override
    protected void reset() {
        entityId = 0;
        toRemove = null;
        toUpdate = null;
    }
}
