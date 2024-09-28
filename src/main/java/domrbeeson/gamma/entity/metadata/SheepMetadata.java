package domrbeeson.gamma.entity.metadata;

import domrbeeson.gamma.block.WoolColour;
import domrbeeson.gamma.entity.metadata.values.MetadataValue;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class SheepMetadata extends LivingEntityMetadata {

    private final WoolMetadataValue woolMeta = new WoolMetadataValue();

    public SheepMetadata setWoolColour(WoolColour woolColour) {
        woolMeta.setWoolColour(woolColour);
        setChanged();
        return this;
    }

    public WoolColour getWoolColour() {
        return woolMeta.getWoolColour();
    }

    public SheepMetadata setSheared(boolean sheared) {
        woolMeta.setSheared(sheared);
        setChanged();
        return this;
    }

    public boolean isSheared() {
        return woolMeta.isSheared();
    }

    @Override
    public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
        super.writeChanges(protocol, stream);
        if (woolMeta.hasChanged()) {
            woolMeta.writeChanges(protocol, stream);
        }
    }

    private static class WoolMetadataValue extends MetadataValue {

        private boolean sheared = false;
        private WoolColour woolColour;

        public WoolMetadataValue() {
            super(BYTE_ID, 16);

            // TODO This is ripped directly from the minecraft beta 1.7.3 server source code, convert to a weighted table or something later
            int randomNumber = ThreadLocalRandom.current().nextInt(100);
            byte woolColour = (byte) (randomNumber < 5 ? 15 : (randomNumber < 10 ? 7 : (randomNumber < 15 ? 8 : (randomNumber < 18 ? 12 : (ThreadLocalRandom.current().nextInt(500) == 0 ? 6 : 0)))));
            this.woolColour = WoolColour.values()[woolColour];
        }

        public void setSheared(boolean sheared) {
            this.sheared = sheared;
            setChanged();
        }

        public boolean isSheared() {
            return sheared;
        }

        public void setWoolColour(WoolColour woolColour) {
            this.woolColour = woolColour;
            setChanged();
        }

        public WoolColour getWoolColour() {
            return woolColour;
        }

        @Override
        public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
            super.writeChanges(protocol, stream);
            stream.writeByte(getDataTypeAndId());
            stream.writeByte((byte) ((sheared ? 1 : 0) << 4 | (byte) woolColour.ordinal()));
        }
    }

}
