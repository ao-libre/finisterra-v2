package system;

import com.artemis.Aspect;
import com.artemis.E;
import com.artemis.FluidIteratingSystem;
import com.artemis.Position;
import com.artemis.annotations.Wire;
import net.mostlyoriginal.api.event.common.EventSystem;
import world.event.MovementEvent;
import world.event.PositionAssignmentEvent;

import java.util.logging.Logger;

@Wire
public class MovementSystem extends FluidIteratingSystem {

    private static final Logger LOGGER = Logger.getLogger("MovementSystem");

    private EventSystem eventSystem;

    public MovementSystem() {
        super(Aspect.all(Position.class));
    }

    @Override
    protected void process(E e) {

    }

    public void moveEntity(E e, Position newPosition) {
        Position oldPosition = new Position(e.positionX(), e.positionY());
        e.positionX(newPosition.getX()).positionY(newPosition.getY());
        eventSystem.dispatch(new MovementEvent(e.id(), oldPosition));
    }

    public void assignPosition(E e, Position positionAssigned) {
        if(e.hasPosition()) {
            LOGGER.severe("Should not have position at this time");
            return;
        }
        e.positionX(positionAssigned.getX()).positionY(positionAssigned.getY());
        eventSystem.dispatch(new PositionAssignmentEvent(e.id()));
    }
}
