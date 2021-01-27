package system;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.utils.ImmutableBag;

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
        String oldChunkId = getChunkId(entityId);

        String currentChunkId = null;
        E entity = E.E(entityId);
        if (entity.hasPosition()) {
            currentChunkId = getChunkId(entity.getPosition());

        }
        updateChunk(entity, oldChunkId, currentChunkId);
    }

    private String getChunkId(int entityId) {
        ImmutableBag<String> entityGroups = E.E(entityId).groups();
        for (int i = entityGroups.size() - 1; i >= 0; i--) {
            if (entityGroups.get(i).startsWith(CHUNK_IDENTIFIER)) {
                return entityGroups.get(i);
            }
        }
        return null;
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
