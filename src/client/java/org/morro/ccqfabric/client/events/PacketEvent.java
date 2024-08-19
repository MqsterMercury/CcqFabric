package org.morro.ccqfabric.client.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.packet.Packet;

public interface PacketEvent {
    Event<Receive> RECEIVE = EventFactory.createArrayBacked(Receive.class, callbacks -> (packet) -> {
        for (Receive callback : callbacks) {
            callback.onReceivePacket(packet);
        }
    });

    Event<Send> SEND = EventFactory.createArrayBacked(Send.class, callbacks -> (packet) -> {
        for (Send callback : callbacks) {
            callback.onSendPacket(packet);
        }
    });

    interface Receive {
        void onReceivePacket(Packet<?> packet);
    }

    interface Send {
        void onSendPacket(Packet<?> packet);
    }
}
