package domrbeeson.gamma.entity.metadata;

import domrbeeson.gamma.entity.metadata.values.BooleanMetadataValue;

import java.io.DataOutputStream;
import java.io.IOException;

public class EntityMetadata {

    private final BooleanMetadataValue fireMeta = new BooleanMetadataValue(0);

    private boolean changed = true;

    public boolean isOnFire() {
        return fireMeta.getValue();
    }

    public void setOnFire(boolean fire) {
        fireMeta.setValue(fire);
        setChanged();
    }

    protected final void setChanged() {
        changed = true;
    }

    public final boolean hasChanged() {
        return changed;
    }

    public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
        changed = false;
        if (fireMeta.hasChanged()) {
            fireMeta.writeChanges(protocol, stream);
        }
    }

}
