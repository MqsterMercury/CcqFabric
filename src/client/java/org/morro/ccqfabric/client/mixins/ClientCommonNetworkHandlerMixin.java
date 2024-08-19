package org.morro.ccqfabric.client.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientCommonNetworkHandler.class)
public class ClientCommonNetworkHandlerMixin {

    /**
     * @author YourName
     * @reason Automatically accept resource packs
     */
    @Overwrite
    public void onResourcePackSend(ResourcePackSendS2CPacket packet) {
        ClientCommonNetworkHandler handler = MinecraftClient.getInstance().getNetworkHandler();
        if (handler != null) {
        }
    }
}