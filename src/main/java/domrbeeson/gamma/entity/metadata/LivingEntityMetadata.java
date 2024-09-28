package domrbeeson.gamma.entity.metadata;

import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.metadata.values.BooleanMetadataValue;
import org.jetbrains.annotations.Nullable;

import java.io.DataOutputStream;
import java.io.IOException;

public class LivingEntityMetadata extends EntityMetadata {

    private final BooleanMetadataValue mountedMeta = new BooleanMetadataValue(2);

    private LivingEntity<?> mountedEntity = null;

    public @Nullable LivingEntity<?> getMountedEntity() {
        return mountedEntity;
    }

    public LivingEntityMetadata setMountedEntity(@Nullable LivingEntity<?> mountedEntity) {
        // TODO don't allow if mounted entity = this entity
        this.mountedEntity = mountedEntity;
        mountedMeta.setValue(mountedEntity != null);
        setChanged();
        return this;
    }

    public void unmount() {
        setMountedEntity(null);
    }

    @Override
    public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
        super.writeChanges(protocol, stream);
        if (mountedMeta.hasChanged()) {
            mountedMeta.writeChanges(protocol, stream);
        }
    }
}
