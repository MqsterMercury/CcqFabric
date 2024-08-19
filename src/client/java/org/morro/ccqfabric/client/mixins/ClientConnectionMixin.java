package org.morro.ccqfabric.client.mixins;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import org.morro.ccqfabric.client.events.PacketEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(method = "channelRead0", at = @At("HEAD"))
    private void onChannelRead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
        PacketEvent.RECEIVE.invoker().onReceivePacket(packet);
    }

    @Inject(method = "sendImmediately", at = @At("HEAD"))
    private void onSendPacket(Packet<?> packet, PacketCallbacks callbacks, boolean flush, CallbackInfo ci) {
        PacketEvent.SEND.invoker().onSendPacket(packet);
    }
}
