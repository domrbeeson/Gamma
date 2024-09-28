package domrbeeson.gamma.entity.metadata;

import domrbeeson.gamma.entity.metadata.values.BooleanMetadataValue;

import java.io.DataOutputStream;
import java.io.IOException;

public class PigMetadata extends LivingEntityMetadata {

    private final BooleanMetadataValue saddleMeta = new BooleanMetadataValue(16);

    public boolean hasSaddle() {
        return saddleMeta.getValue();
    }

    public PigMetadata setSaddle(boolean saddle) {
        saddleMeta.setValue(saddle);
        setChanged();
        return this;
    }

    @Override
    public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
        super.writeChanges(protocol, stream);
        if (saddleMeta.hasChanged()) {
            saddleMeta.writeChanges(protocol, stream);
        }
    }
}
