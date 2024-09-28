package domrbeeson.gamma.nbt.tags;

import domrbeeson.gamma.nbt.NBTTag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTByte extends NBTTag {

	private byte value = 0;

	public NBTByte() {
		this((byte) 0);
	}

	public NBTByte(int value) {
		this((byte) value);
	}

	public NBTByte(boolean value) {
		this(value ? 1 : 0);
	}

	public NBTByte(byte value) {
		super((byte) 1, "TAG_Byte");
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	@Override
	public void readValue(DataInput in) throws IOException {
		value = in.readByte();
	}

	@Override
	public void writeValue(DataOutput out) throws IOException {
		out.writeByte(value);
	}

	@Override
	public String toString() {
		return "" + value;
	}
	
}
