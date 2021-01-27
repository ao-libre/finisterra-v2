package system;

import com.artemis.EBag;
import com.artemis.Position;
import com.artemis.World;
import org.junit.jupiter.api.Test;
import world.ServerWorld;

import static com.artemis.E.E;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChunkSystemTest {

    private int entityId;
    private World world;
    private MovementSystem movementSystem;
    private ChunkSystem chunkSystem;
    private Position newPosition;
    private Position oldPosition;

    @Test
    public void whenEntityHasPositionIsAddedToChunk() {
        givenAWorld();
        givenAnEntityWithoutPosition();
        givenANewPosition();
        givenAMovementSystem();
        givenAChunkSystem();

        whenEntityReceivesPosition();

        thenEntityIsAddedToNewChunk();
    }

    @Test
    public void whenEntityMovesToAnotherChunkItIsUpdated() {
        givenAWorld();
        givenAMovementSystem();
        givenAChunkSystem();
        givenAnEntityWithPosition();
        givenANewPosition();

        whenEntityMovesToAnotherChunk();

        thenEntityIsMovedToNewChunk();
    }

    private void givenAWorld() {
        this.world = new ServerWorld();
        this.world.process();
    }

    private void givenAChunkSystem() {
        chunkSystem = world.getSystem(ChunkSystem.class);
    }

    private void givenAnEntityWithPosition() {
        oldPosition = new Position(0, 0);
        entityId = this.world.create();
        E(entityId).positionX(oldPosition.getX()).positionY(oldPosition.getY());
    }

    private void givenAnEntityWithoutPosition() {
        entityId = this.world.create();
    }

    private void givenANewPosition() {
        newPosition = new Position(10, 10);
    }

    private void whenEntityReceivesPosition() {
        this.movementSystem.assignPosition(E(entityId), newPosition);
        world.process();
    }

    private void whenEntityMovesToAnotherChunk() {
        this.movementSystem.moveEntity(E(entityId), newPosition);
        world.process();
    }

    private void thenEntityIsAddedToNewChunk() {
        EBag entities = this.chunkSystem.getEntities(this.chunkSystem.getChunkId(newPosition));
        assertEquals(1, entities.size());
        assertEquals(entityId, entities.get(0).id());
    }

    private void thenEntityIsMovedToNewChunk() {
        String newChunkId = this.chunkSystem.getChunkId(newPosition);
        assertEquals(newChunkId, this.chunkSystem.getChunkId(entityId));

        EBag entities = this.chunkSystem.getEntities(newChunkId);
        assertEquals(entities.size(), 1);
        assertEquals(entities.get(0).id(), entityId);

        EBag oldEntities = this.chunkSystem.getEntities(this.chunkSystem.getChunkId(oldPosition));
        assertEquals(oldEntities.size(), 0);
    }

    private void givenAMovementSystem() {
        this.movementSystem = this.world.getSystem(MovementSystem.class);
    }

}
