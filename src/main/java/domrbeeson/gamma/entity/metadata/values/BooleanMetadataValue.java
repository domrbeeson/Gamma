package domrbeeson.gamma.entity.metadata.values;

import java.io.DataOutputStream;
import java.io.IOException;

public class BooleanMetadataValue extends MetadataValue {

    private boolean value = false;

    public BooleanMetadataValue(int id) {
        super(BYTE_ID, id);
    }

    public void setValue(boolean value) {
        this.value = value;
        setChanged();
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
        super.writeChanges(protocol, stream);
        stream.writeByte(getDataTypeAndId());
        stream.writeBoolean(value);
    }
}
