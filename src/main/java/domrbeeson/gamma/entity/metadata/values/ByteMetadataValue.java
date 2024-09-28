package domrbeeson.gamma.entity.metadata.values;

import java.io.DataOutputStream;
import java.io.IOException;

public class ByteMetadataValue extends MetadataValue {

    private byte value;

    public ByteMetadataValue(int id) {
        this(id, (byte) 0);
    }

    public ByteMetadataValue(int id, byte defaultValue) {
        super(0, id);
        value = defaultValue;
    }

    public void setValue(byte value) {
        this.value = value;
        setChanged();
    }

    public byte getValue() {
        return value;
    }

    @Override
    public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
        super.writeChanges(protocol, stream);
        stream.writeByte(getDataTypeAndId());
        stream.writeByte(value);
    }
}
