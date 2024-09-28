package domrbeeson.gamma.nbt.tags;

import domrbeeson.gamma.nbt.NBTTag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTInt extends NBTTag {

	private int value;

	public NBTInt() {
		this(0);
	}

	public NBTInt(int value) {
		super((byte) 3, "TAG_Int");
		this.value = value;
	}

	@Override
	public void readValue(DataInput in) throws IOException {
		value = in.readInt();
	}

	@Override
	public void writeValue(DataOutput out) throws IOException {
		out.writeInt(value);
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "" + value;
	}
	
}
