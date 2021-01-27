package system;

import com.artemis.BaseSystem;
import com.artemis.ChangeRegistry;
import com.artemis.Component;
import com.artemis.annotations.Wire;

import java.util.Set;

@Wire
public class ChangeNotifierSystem extends BaseSystem {
    ChangeRegistry changeRegistry;
    EntityUpdateSystem entityUpdateSystem;

    @Override
    protected void processSystem() {
        changeRegistry.getRemoved().forEach(this::remove);
        changeRegistry.getMarked().forEach(this::mark);
    }

    private void mark(int entityId, Set<Component> components) {
        entityUpdateSystem.addChanges(entityId, components);
    }

    private void remove(int entityId, Set<Class<? extends Component>> components) {
        entityUpdateSystem.addRemoved(entityId, components);
    }
}
