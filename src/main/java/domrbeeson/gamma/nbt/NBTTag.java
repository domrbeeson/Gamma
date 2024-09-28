package domrbeeson.gamma.nbt;

import domrbeeson.gamma.nbt.tags.*;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class NBTTag {

	private static final NBTUnknown UNKNOWN_TAG = new NBTUnknown();

	private final byte id;
	private final String name;

	public NBTTag(byte id, String name) {
		this.id = id;
		this.name = name;
	}

	public void saveToFile(File file) {
		try {
			DataOutputStream out = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
			out.writeByte(id);
			if (id == 10) {
				out.writeUTF("");
			}
			writeValue(out);
			if (id == 10) {
				out.writeByte(0);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static NBTTag loadFromFile(File file) {
		NBTTag tag = UNKNOWN_TAG;
		try {
			DataInputStream in = new DataInputStream(new GZIPInputStream(new FileInputStream(file)));
			tag = getTagFromId(in.readByte());
			if (tag.id > 0) {
				if (tag.id == 10) {
					in.readUTF();
				}
				tag.readValue(in);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tag;
	}

	protected static NBTTag getTagFromId(byte id) {
		NBTTag tag;
		switch (id) {
			case 1 -> tag = new NBTByte();
			case 2 -> tag = new NBTShort();
			case 3 -> tag = new NBTInt();
			case 4 -> tag = new NBTLong();
			case 5 -> tag = new NBTFloat();
			case 6 -> tag = new NBTDouble();
			case 7 -> tag = new NBTBytes();
			case 8 -> tag = new NBTString();
			case 9 -> tag = new NBTList();
			case 10 -> tag = new NBTCompound();
			default -> tag = UNKNOWN_TAG;
		}
		return tag;
	}

	public byte getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public abstract void readValue(DataInput in) throws IOException;
	public abstract void writeValue(DataOutput out) throws IOException;
	public abstract String toString();

}
