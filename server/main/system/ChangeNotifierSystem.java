package system;

import com.artemis.BaseSystem;
import com.artemis.ChangeRegistry;
import com.artemis.Component;
import com.artemis.annotations.Wire;

import java.util.Set;

@Wire
public class ChangeNotifierSystem extends BaseSystem {
    ChangeRegistry changeRegistry;
    NetworkSystem networkSystem;
    @Override
    protected void processSystem() {
        changeRegistry.getRemoved().forEach(this::remove);
        changeRegistry.getMarked().forEach(this::mark);
    }

    private void mark(int entityId, Set<Component> components) {
        networkSystem.addChanges(entityId, components);
    }

    private void remove(int entityId, Set<Class<? extends Component>> components) {
        networkSystem.addRemoved(entityId, components);
    }
}
