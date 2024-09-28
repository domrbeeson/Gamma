package domrbeeson.gamma.nbt.tags;

import domrbeeson.gamma.nbt.NBTTag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NBTList extends NBTTag {

	private List<NBTTag> value;

	private byte listType = 0;

	public NBTList() {
		this(new ArrayList<>());
	}

	public NBTList(List<NBTTag> value) {
		super((byte) 9, "TAG_List");
		this.value = value;
		if (value.isEmpty()) {
			listType = 0;
		} else {
			listType = value.getFirst().getId();
		}
	}

	@Override
	public void readValue(DataInput in) throws IOException {
		listType = in.readByte();
		int size = in.readInt();
		value = new ArrayList<>(size);

		for (int i = 0; i < size; i++) {
			NBTTag tag = getTagFromId(listType);
			tag.readValue(in);
			value.add(i, tag);
		}
	}

	@Override
	public void writeValue(DataOutput out) throws IOException {
		out.writeByte(listType);
		out.writeInt(value.size());
		for (NBTTag tag : value) {
			tag.writeValue(out);
		}
	}

	public List<NBTTag> getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.size() + " entries of id " + listType;
	}

	public NBTByte getByte(int index) {
		return (NBTByte) value.get(index);
	}

	public NBTBytes getBytes(int index) {
		return (NBTBytes) value.get(index);
	}

	public NBTDouble getDouble(int index) {
		return (NBTDouble) value.get(index);
	}

	public NBTFloat getFloat(int index) {
		return (NBTFloat) value.get(index);
	}

	public NBTInt getInt(int index) {
		return (NBTInt) value.get(index);
	}

	public NBTList getList(int index) {
		return (NBTList) value.get(index);
	}

	public NBTLong getLong(int index) {
		return (NBTLong) value.get(index);
	}

	public NBTShort getShort(int index) {
		return (NBTShort) value.get(index);
	}

	public NBTString getString(int index) {
		return (NBTString) value.get(index);
	}
	
}
