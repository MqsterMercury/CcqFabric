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

public class POFAutoVoteFeature {

    private static boolean shouldVote = true;

    private static void sendAllVotePackets() {
        if (mc.player == null || mc.getNetworkHandler() == null) return;

        mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, mc.player.getYaw(), mc.player.getPitch()));

        // Vote for Game Mode
        sendClickPacket(1, 12, 0);
        sendClickPacket(2, ClientConfig.instance().pofGVote.getSlot(), 0);
        sendClickPacket(2, 31, 0); // Back

        // Vote for Map Mode
        sendClickPacket( 3, 14, 0);
        sendClickPacket(4, ClientConfig.instance().pofMVote.getSlot(), 0);
        sendClickPacket(4, 31, 0); // Back
        sendClickPacket(5, 31, 0); // Back
    }

    public static void registerPOFAutoVote() {
        ClientTickEvents.START_CLIENT_TICK.register(mc -> {
            if(!ClientConfig.instance().pofAutoVote || !shouldVote || mc.player == null || mc.world == null || ScoreboardUtils.getScoreboardTitle() == null || !Formatting.strip(ScoreboardUtils.getScoreboardTitle()).contains("Pillars of Fortune")) return;
            sendAllVotePackets();
            shouldVote = false;
        });

        WorldEvent.LOAD.register(world -> {
            if(!ClientConfig.instance().pofAutoVote) return;
            shouldVote = true;
        });
    }

    public enum GameModeVote {
        NORMAL(10),
        SHUFFLED(12),
        BALANCED(14),
        SWAPPER(16);

        private final int slot;
        GameModeVote(int slot) {this.slot = slot;}
        public int getSlot() {return slot;}
    }

    public enum MapModeVote {
        NORMAL(11),
        RISING_LAVA(12),
        PORTALS(13),
        ABLOCKALYPSE(14),
        FRAGILE_BLOCKS(15);

        private final int slot;
        MapModeVote(int slot) {this.slot = slot;}
        public int getSlot() {return slot;}
    }
}
