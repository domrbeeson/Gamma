package domrbeeson.gamma.entity.metadata;

import domrbeeson.gamma.entity.metadata.values.BooleanMetadataValue;
import domrbeeson.gamma.entity.metadata.values.IntMetadataValue;
import domrbeeson.gamma.entity.metadata.values.StringMetadataValue;
import org.jetbrains.annotations.Nullable;

import java.io.DataOutputStream;
import java.io.IOException;

public class WolfMetadata extends LivingEntityMetadata {

    private final BooleanMetadataValue sittingMeta = new BooleanMetadataValue(16);
    private final StringMetadataValue ownerMeta = new StringMetadataValue(17);
    private final IntMetadataValue healthMeta = new IntMetadataValue(18);

    public WolfMetadata setSitting(boolean sitting) {
        sittingMeta.setValue(sitting);
        setChanged();
        return this;
    }

    public boolean isSitting() {
        return sittingMeta.getValue();
    }

    public WolfMetadata setOwnerUsername(String username) {
        ownerMeta.setValue(username);
        setChanged();
        return this;
    }

    public boolean isTamed() {
        return !ownerMeta.getValue().isEmpty();
    }

    public @Nullable String getOwnerUsername() {
        String username = ownerMeta.getValue();
        if (username.isEmpty()) {
            return null;
        }
        return username;
    }

    public boolean hasOwner() {
        return !ownerMeta.getValue().isEmpty();
    }

    public WolfMetadata setHealth(int health) {
        healthMeta.setValue(health);
        setChanged();
        return this;
    }

    @Override
    public void writeChanges(int protocol, DataOutputStream stream) throws IOException {
        super.writeChanges(protocol, stream);
        if (sittingMeta.hasChanged()) {
            sittingMeta.writeChanges(protocol, stream);
        }
        if (ownerMeta.hasChanged()) {
            ownerMeta.writeChanges(protocol, stream);
        }
        if (healthMeta.hasChanged()) {
            healthMeta.writeChanges(protocol, stream);
        }
    }
}
