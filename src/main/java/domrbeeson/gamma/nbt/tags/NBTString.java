package domrbeeson.gamma.nbt.tags;

import domrbeeson.gamma.nbt.NBTTag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTString extends NBTTag {

	private String value;

	public NBTString() {
		this("");
	}

	public NBTString(String value) {
		super((byte) 8, "TAG_String");
		this.value = value;
	}

	@Override
	public void readValue(DataInput in) throws IOException {
		value = in.readUTF();
	}

	@Override
	public void writeValue(DataOutput out) throws IOException {
		if (value == null) {
			value = "";
		}
		out.writeUTF(value);
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
	
}
