package domrbeeson.gamma.nbt.tags;

import domrbeeson.gamma.nbt.NBTTag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTFloat extends NBTTag {

	private float value;

	public NBTFloat() {
		this(0);
	}

	public NBTFloat(float value) {
		super((byte) 5, "TAG_Float");
		this.value = value;
	}

	@Override
	public void readValue(DataInput in) throws IOException {
		value = in.readFloat();
	}

	@Override
	public void writeValue(DataOutput out) throws IOException {
		out.writeFloat(value);
	}

	public float getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "" + value;
	}
	
}
