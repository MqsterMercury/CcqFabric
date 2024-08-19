package org.morro.ccqfabric.client.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import org.morro.ccqfabric.client.config.ClientConfig;
import org.morro.ccqfabric.client.events.WorldEvent;
import org.morro.ccqfabric.client.utils.ScoreboardUtils;

import static org.morro.ccqfabric.client.CcqfabricClient.mc;
import static org.morro.ccqfabric.client.utils.PacketUtils.sendClickPacket;

public class SkywarsAutoVoteFeature {

    private static boolean shouldVote = true;

    private static void sendAllVotePackets() {
        if (mc.player == null || mc.getNetworkHandler() == null) return;

        mc.player.getInventory().selectedSlot = 1;
        mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(1));
        mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, mc.player.getYaw(), mc.player.getPitch()));

        // Vote for Chest
        sendClickPacket(1, 15, 0);
        sendClickPacket(2, ClientConfig.instance().skyCVote.getSlot(), 0);
        sendClickPacket(2, 31, 0); // Back

        // Vote for Projectiles
        sendClickPacket( 3, 13, 0);
        sendClickPacket(4, ClientConfig.instance().skyPVote.getSlot(), 0);
        sendClickPacket(4, 31, 0); // Back

        // Vote for Time
        sendClickPacket(5, 11, 0);
        sendClickPacket(6, ClientConfig.instance().skyTVote.getSlot(), 0);
        sendClickPacket(6, 31, 0); // Back
        sendClickPacket(7, 31, 0); // Back
    }

    public static void registerSkywarsAutoVote() {
        ClientTickEvents.START_CLIENT_TICK.register(mc -> {
            if(!ClientConfig.instance().skyAutoVote || !shouldVote || mc.player == null || mc.world == null || ScoreboardUtils.getScoreboardTitle() == null || !Formatting.strip(ScoreboardUtils.getScoreboardTitle()).contains("Solo SkyWars")) return;
            sendAllVotePackets();
            shouldVote = false;
        });

        WorldEvent.LOAD.register(world -> {
            if(!ClientConfig.instance().skyAutoVote) return;
            shouldVote = true;
        });
    }

    public enum SkywarsTimeVote {
        DAY(11),
        SUNSET(13),
        NIGHT(15);

        private final int slot;
        SkywarsTimeVote(int slot) {this.slot = slot;}
        public int getSlot() { return slot;}
    }

    public enum ProjectileVote {
        NO_THROWABLES(11),
        NORMAL_PROJECTILES(13),
        SOFT_BLOCKS(15);

        private final int slot;
        ProjectileVote(int slot) {this.slot = slot;}
        public int getSlot() {return slot;}
    }

    public enum ChestVote {
        BASIC(11),
        NORMAL(13),
        OVERPOWERED(15);

        private final int slot;
        ChestVote(int slot) {this.slot = slot;}
        public int getSlot() {return slot;}
    }
}
