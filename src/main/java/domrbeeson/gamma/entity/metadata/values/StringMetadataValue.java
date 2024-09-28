package domrbeeson.gamma.entity.metadata.values;

import java.io.DataOutputStream;
import java.io.IOException;

public class StringMetadataValue extends MetadataValue {

    private String value = "";

    public StringMetadataValue(int id) {
        super(STRING_ID, id);
    }

    public void setValue(String value) {
        this.value = value;
        setChanged();
    }

    public String getValue() {
        return value;
    }

    @Override
    public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
        super.writeChanges(protocol, stream);
        stream.writeByte(getDataTypeAndId());
        if (protocol < 11) {
            stream.writeUTF(value);
        } else {
            stream.writeShort(value.length());
            stream.writeChars(value);
        }
    }
}
