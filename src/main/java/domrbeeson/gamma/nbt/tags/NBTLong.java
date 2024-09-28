package domrbeeson.gamma.nbt.tags;

import domrbeeson.gamma.nbt.NBTTag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTLong extends NBTTag {

	private long value;
	
	public NBTLong() {
		this(0);
	}

	public NBTLong(long value) {
		super((byte) 4, "TAG_Long");
		this.value = value;
	}

	@Override
	public void readValue(DataInput in) throws IOException {
		value = in.readLong();
	}

	@Override
	public void writeValue(DataOutput out) throws IOException {
		out.writeLong(value);
	}

	public long getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "" + value;
	}
	
}
