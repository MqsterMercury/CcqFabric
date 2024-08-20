package org.morro.ccqfabric.client.features;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import org.morro.ccqfabric.client.config.ClientConfig;
import org.morro.ccqfabric.client.events.WorldEvent;
import org.morro.ccqfabric.client.utils.RenderUtils;

import java.awt.*;

public class LobbyChestFinderFeature {
    public static BlockPos chestPosition = null;

    public static void registerLobbyChestFinder() {
        WorldRenderEvents.LAST.register(world -> {
            if(!ClientConfig.instance().generalChestFinder) return;

            if(chestPosition != null) {
                RenderUtils.renderOutline(world, chestPosition, Color.BLUE);
            }
        });

    }
}
