package domrbeeson.gamma.entity.metadata;

import domrbeeson.gamma.entity.metadata.values.IntMetadataValue;

import java.io.DataOutputStream;
import java.io.IOException;

public class SlimeMetadata extends LivingEntityMetadata {

    private final IntMetadataValue sizeMeta = new IntMetadataValue(16);

    public int getSize() {
        return sizeMeta.getValue();
    }

    public SlimeMetadata setSize(int size) {
        sizeMeta.setValue(size);
        return this;
    }

    @Override
    public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
        super.writeChanges(protocol, stream);
        if (sizeMeta.hasChanged()) {
            sizeMeta.writeChanges(protocol, stream);
        }
    }

}
