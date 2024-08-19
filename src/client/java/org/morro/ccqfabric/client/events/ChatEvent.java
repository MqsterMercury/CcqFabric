package org.morro.ccqfabric.client.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.text.Text;

public interface ChatEvent {

    Event<Receive> RECEIVE = EventFactory.createArrayBacked(Receive.class, callbacks -> (message) -> {
        for (Receive callback : callbacks) {
            callback.onChatMessage(message);
        }
    });

    interface Receive {
        void onChatMessage(Text message);
    }

}
