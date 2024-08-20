package org.morro.ccqfabric.client.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public interface BlockEvent {

    Event<Place> PLACE = EventFactory.createArrayBacked(Place.class, callbacks -> (pos, state) -> {
        for(Place callback : callbacks) {
            callback.onBlockPlace(pos, state);
        }
    });

    interface Place {
        void onBlockPlace(BlockPos pos, BlockState state);
    }
}
