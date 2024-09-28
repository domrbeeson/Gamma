package domrbeeson.gamma.nbt.tags;

import domrbeeson.gamma.nbt.NBTTag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTDouble extends NBTTag {

	private double value;

	public NBTDouble() {
		this(0);
	}

	public NBTDouble(double value) {
		super((byte) 6, "TAG_Double");
		this.value = value;
	}

	@Override
	public void readValue(DataInput in) throws IOException {
		value = in.readDouble();
	}

	@Override
	public void writeValue(DataOutput out) throws IOException {
		out.writeDouble(value);
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "" + value;
	}
	
}
