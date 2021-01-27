package world.event;

import net.mostlyoriginal.api.event.common.Event;

public class PositionAssignmentEvent implements Event {
    private final int entityId;

    public PositionAssignmentEvent(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityId() {
        return entityId;
    }
}
