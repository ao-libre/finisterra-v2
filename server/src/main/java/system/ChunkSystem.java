package system;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.utils.ImmutableBag;
import network.EntityUpdateDTO;

import java.util.Objects;

@Wire
public class ChunkSystem extends BaseSystem {

    public static final String CHUNK_IDENTIFIER = "chunk::";
    private ChangeRegistry changeRegistry;
    private EntityUpdateSystem entityUpdateSystem;

    @Override
    protected void processSystem() {
        changeRegistry.getWhoChange(Position.class).ifPresent(entities -> entities.forEach(this::checkChunk));
    }

    private void checkChunk(Integer entityId) {
        // entity has changed its position
        String oldChunkId = null;
        String currentChunkId = null;
        E entity = E.E(entityId);
        ImmutableBag<String> entityGroups = entity.groups();
        for (int i = entityGroups.size() - 1; i >= 0; i--) {
            if (entityGroups.get(i).startsWith(CHUNK_IDENTIFIER)) {
                oldChunkId = entityGroups.get(i);
                break;
            }
        }
        if (entity.hasPosition()) {
            currentChunkId = getChunkId(entity.getPosition());

        }
        updateChunk(entity, oldChunkId, currentChunkId);
    }

    private void updateChunk(E entity, String oldChunkId, String currentChunkId) {
        if (!Objects.equals(oldChunkId, currentChunkId)) {
            if (currentChunkId != null) {
                join(entity, currentChunkId);
            }
            if (oldChunkId != null) {
                entity.removeGroup(oldChunkId);
            }
        }
    }

    private void join(E entity, String chunkID) {
        entity.group(chunkID);
        EBag entities = getEntities(chunkID);
        entities.forEach(entityInChunk -> {
            if (entityInChunk.id() != entity.id()) {
                entityUpdateSystem.sendEntity(entity.id(), entityInChunk.id());
            }
        });
    }

    String getChunkId(Position position) {
        return CHUNK_IDENTIFIER + position.getX() / 10 + "@" + position.getY() / 10;
    }

    public EBag getEntities(String chunkId) {
        return E.withGroup(chunkId);
    }

    // @todo: refactor this to return all near entities, from neighboring chunks too
    public EBag getNearEntities(String chunkId) {
        return E.withGroup(chunkId);
    }

}
