package org.morro.ccqfabric.client.mixins;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MinecraftClient.class)
public interface MinecraftAccessor {
    @Invoker("doItemUse")
    void invokeRightClickMouse();
}
