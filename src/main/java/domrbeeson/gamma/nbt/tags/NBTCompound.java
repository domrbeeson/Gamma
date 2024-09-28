package domrbeeson.gamma.nbt.tags;

import domrbeeson.gamma.nbt.NBTTag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class NBTCompound extends NBTTag {

	private final Map<String, NBTTag> tags;

	public NBTCompound() {
		this(new HashMap<>());
	}

	public NBTCompound(Map<String, NBTTag> value) {
		super((byte) 10, "TAG_Compound");
		this.tags = value;
	}

	@Override
	public void readValue(DataInput in) throws IOException {
		tags.clear();

		byte id;
		while ((id = in.readByte()) != 0) {
			String fieldName = in.readUTF();
			NBTTag tag = getTagFromId(id);
			tag.readValue(in);
			tags.put(fieldName, tag);
		}
	}

	@Override
	public void writeValue(DataOutput out) throws IOException {
		for (Entry<String, NBTTag> entry : tags.entrySet()) {
			NBTTag tag = entry.getValue();

			byte id = tag.getId();
			out.writeByte(id);
			if (id == 0) {
				// End of compound
				return;
			}

			out.writeUTF(entry.getKey());
			tag.writeValue(out);
		}
		out.writeByte(0);
	}

	@Override
	public String toString() {
		return tags.size() + " entries, " + tags.keySet();
	}

	public boolean hasField(String field) {
		return tags.containsKey(field);
	}
	
	public NBTByte getByte(String field) {
		return (NBTByte) tags.get(field);
	}

	public NBTBytes getBytes(String field) {
		return (NBTBytes) tags.get(field);
	}

	public NBTDouble getDouble(String field) {
		return (NBTDouble) tags.get(field);
	}

	public NBTFloat getFloat(String field) {
		return (NBTFloat) tags.get(field);
	}

	public NBTInt getInt(String field) {
		return (NBTInt) tags.get(field);
	}

	public NBTList getList(String field) {
		return (NBTList) tags.get(field);
	}

	public NBTLong getLong(String field) {
		return (NBTLong) tags.get(field);
	}

	public NBTShort getShort(String field) {
		return (NBTShort) tags.get(field);
	}

	public NBTString getString(String field) {
		return (NBTString) tags.get(field);
	}

	public NBTCompound getComound(String field) {
		return (NBTCompound) tags.get(field);
	}

	public Map<String, NBTTag> getValue() {
		return tags;
	}

}
