package org.morro.ccqfabric.client.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.world.ClientWorld;

public interface WorldEvent {

    Event<Load> LOAD = EventFactory.createArrayBacked(Load.class, callbacks -> world -> {
        for (Load callback : callbacks) {
            callback.onWorldLoad(world);
        }
    });

    interface Load {
        void onWorldLoad(ClientWorld world);
    }
}
