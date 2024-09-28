package domrbeeson.gamma.entity.metadata;

import domrbeeson.gamma.entity.metadata.values.BooleanMetadataValue;

import java.io.DataOutputStream;
import java.io.IOException;

public class GhastMetadata extends LivingEntityMetadata {

    private final BooleanMetadataValue fireballMeta = new BooleanMetadataValue(16);

    public boolean hasFireballFace() {
        return fireballMeta.getValue();
    }

    public void setFireballFace(boolean fireballFace) {
        fireballMeta.setValue(fireballFace);
        setChanged();
    }

    @Override
    public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
        super.writeChanges(protocol, stream);
        if (fireballMeta.hasChanged()) {
            fireballMeta.writeChanges(protocol, stream);
        }
    }
}
