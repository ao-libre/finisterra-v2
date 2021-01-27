package system;

import com.artemis.Aspect;
import com.artemis.E;
import com.artemis.FluidIteratingSystem;
import com.artemis.Position;
import com.artemis.annotations.Wire;

import java.util.logging.Logger;

@Wire
public class MovementSystem extends FluidIteratingSystem {

    private static final Logger LOGGER = Logger.getLogger("MovementSystem");

    public MovementSystem() {
        super(Aspect.all(Position.class));
    }

    @Override
    protected void process(E e) {

    }

    public void moveEntity(E e, Position newPosition) {
        e.positionX(newPosition.getX()).positionY(newPosition.getY());
    }

    public void assignPosition(E e, Position positionAssigned) {
        if(e.hasPosition()) {
            LOGGER.severe("Should not have position at this time");
            return;
        }
        e.positionX(positionAssigned.getX()).positionY(positionAssigned.getY());
    }
}
