package org.morro.ccqfabric.client.mixins;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DisplayEntity.TextDisplayEntity.class)
public interface TextDisplayEntityAccessor {

    @Accessor("TEXT")
    static TrackedData<Text> getTextTrackedData() {
        throw new AssertionError("Mixin not applied");
    }

}
