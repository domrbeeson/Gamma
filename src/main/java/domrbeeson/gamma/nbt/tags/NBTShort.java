package domrbeeson.gamma.nbt.tags;

import domrbeeson.gamma.nbt.NBTTag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTShort extends NBTTag {

	private short value;

	public NBTShort() {
		this((short) 0);
	}

	public NBTShort(int value) {
		this((short) value);
	}

	public NBTShort(short value) {
		super((byte) 2, "TAG_Short");
		this.value = value;
	}

	@Override
	public void readValue(DataInput in) throws IOException {
		value = in.readShort();
	}

	public void writeValue(DataOutput out) throws IOException {
		out.writeShort(value);
	}

	public short getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "" + value;
	}
	
}
