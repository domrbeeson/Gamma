package domrbeeson.gamma.nbt.tags;

import domrbeeson.gamma.nbt.NBTTag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTUnknown extends NBTTag {

    public NBTUnknown() {
        super((byte) 0, "UNKNOWN");
    }

    @Override
    public void readValue(DataInput in) throws IOException {

    }

    @Override
    public void writeValue(DataOutput out) throws IOException {

    }

    @Override
    public String toString() {
        return "";
    }

}
