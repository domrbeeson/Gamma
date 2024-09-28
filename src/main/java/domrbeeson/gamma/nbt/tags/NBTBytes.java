package domrbeeson.gamma.nbt.tags;

import domrbeeson.gamma.nbt.NBTTag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTBytes extends NBTTag {

	private byte[] value;

	public NBTBytes() {
		this(new byte[0]);
	}

	public NBTBytes(byte[] value) {
		super((byte) 7, "TAG_Byte_Array");
		this.value = value;
	}

	public byte[] getValue() {
		return value;
	}

	@Override
	public void readValue(DataInput in) throws IOException {
		value = new byte[in.readInt()];
		in.readFully(value);
	}

	@Override
	public void writeValue(DataOutput out) throws IOException {
		out.writeInt(value.length);
		out.write(value);
	}

	@Override
	public String toString() {
		return value.length + " bytes";
	}
	
}
