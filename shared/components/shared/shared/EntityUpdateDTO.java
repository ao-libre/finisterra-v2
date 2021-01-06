package shared;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

import java.util.Collection;
import java.util.List;

@PooledWeaver
public class EntityUpdateDTO extends Component {
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
}
