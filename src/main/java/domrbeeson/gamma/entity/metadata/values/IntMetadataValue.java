package domrbeeson.gamma.entity.metadata.values;

import java.io.DataOutputStream;
import java.io.IOException;

public class IntMetadataValue extends MetadataValue {

    private int value = 0;

    public IntMetadataValue(int id) {
        super(INT_ID, id);
    }

    public void setValue(int value) {
        this.value = value;
        setChanged();
    }

    public int getValue() {
        return value;
    }

    @Override
    public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
        super.writeChanges(protocol, stream);
        stream.writeByte(getDataTypeAndId());
        stream.writeInt(value);
    }
}
