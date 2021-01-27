package world.event;

import com.artemis.Position;
import net.mostlyoriginal.api.event.common.Event;

public class MovementEvent implements Event {
    private final int entityId;

    private final Position oldPosition;

    public MovementEvent(int entityId, Position oldPosition) {
        this.entityId = entityId;
        this.oldPosition = oldPosition;
    }

    public Position getOldPosition() {
        return oldPosition;
    }

    public int getEntityId() {
        return entityId;
    }
}
