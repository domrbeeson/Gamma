package domrbeeson.gamma.entity.metadata;

import domrbeeson.gamma.entity.metadata.values.BooleanMetadataValue;
import domrbeeson.gamma.entity.metadata.values.ByteMetadataValue;

import java.io.DataOutputStream;
import java.io.IOException;

public class CreeperMetadata extends LivingEntityMetadata {

    private final ByteMetadataValue stateMeta = new ByteMetadataValue(16, (byte) -1);
    private final BooleanMetadataValue chargedMeta = new BooleanMetadataValue(17);

    public boolean isFuseActive() {
        return stateMeta.getValue() == 1;
    }

    public void setFuseActive(boolean fuse) {
        stateMeta.setValue(fuse ? (byte) 1 : (byte) -1);
    }

    public boolean isCharged() {
        return chargedMeta.getValue();
    }

    public CreeperMetadata setCharged(boolean charged) {
        chargedMeta.setValue(charged);
        setChanged();
        return this;
    }

    @Override
    public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
        super.writeChanges(protocol, stream);
        if (stateMeta.hasChanged()) {
            stateMeta.writeChanges(protocol, stream);
        }
        if (chargedMeta.hasChanged()) {
            chargedMeta.writeChanges(protocol, stream);
        }
    }
}
