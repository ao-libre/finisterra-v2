package system;

import com.artemis.BaseSystem;
import com.artemis.ChangeRegistry;
import com.artemis.annotations.Wire;

import java.util.HashSet;

@Wire
public class ChangeNotifierSystem extends BaseSystem {
    ChangeRegistry changeRegistry;
    @Override
    protected void processSystem() {
        changeRegistry.getRemoved().forEach(this::remove);
        changeRegistry.getMarked().forEach(this::mark);
    }

    private void mark(int entityId, HashSet<Class> components) {
        // TODO do something with marked components
    }

    private void remove(int entityId, HashSet<Class> components) {
        // TODO do something with removed components
    }
}
