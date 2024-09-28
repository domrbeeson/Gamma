package domrbeeson.gamma.entity.metadata.values;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class MetadataValue {

    public static final byte BYTE_ID = 0;
    public static final byte SHORT_ID = 1; // unused
    public static final byte INT_ID = 2;
    public static final byte FLOAT_ID = 3; // unused
    public static final byte STRING_ID = 4;
    public static final byte ITEM_ID = 5; // unused
    public static final byte CHUNK_ID = 6; // unused

    private final byte dataTypeAndId;

    private boolean changed = true;

    public MetadataValue(int dataType, int id) {
        dataTypeAndId = (byte) (dataType << 5 | id & 31);
    }

    protected final void setChanged() {
        changed = true;
    }

    public final boolean hasChanged() {
        return changed;
    }

    public final byte getDataTypeAndId() {
        return dataTypeAndId;
    }

    public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
        changed = false;
    }

}
