package org.morro.ccqfabric.client.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import org.morro.ccqfabric.client.config.ClientConfig;
import org.morro.ccqfabric.client.events.WorldEvent;
import org.morro.ccqfabric.client.utils.ScoreboardUtils;

import static org.morro.ccqfabric.client.CcqfabricClient.mc;
import static org.morro.ccqfabric.client.utils.PacketUtils.sendClickPacket;

public class BedwarsAutoVoteFeature {

    private static boolean shouldVote = true;

    private static void sendAllVotePackets() {
        if (mc.player == null || mc.getNetworkHandler() == null) return;

        mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, mc.player.getYaw(), mc.player.getPitch()));

        // Vote for Modifier
        sendClickPacket(1, ClientConfig.instance().bedwarsMVote.getSlot(), 0);
        sendClickPacket(1, 31, 0); // Back
    }

    public static void registerBedwarsAutoVote() {
        ClientTickEvents.START_CLIENT_TICK.register(mc -> {
            if(!ClientConfig.instance().bedwarsAutoVote || !shouldVote || mc.player == null || mc.world == null || ScoreboardUtils.getScoreboardTitle() == null || !Formatting.strip(ScoreboardUtils.getScoreboardTitle()).contains("BedWars")) return;
            sendAllVotePackets();
            shouldVote = false;
        });

        WorldEvent.LOAD.register(world -> {
            if(!ClientConfig.instance().bedwarsAutoVote) return;
            shouldVote = true;
        });
    }

    public enum ModifierVote {
        NORMAL(12),
        FASTER_GENERATORS(14);

        private final int slot;
        ModifierVote(int slot) {this.slot = slot;}
        public int getSlot() {return slot;}
    }
}
