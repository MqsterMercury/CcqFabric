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

public class LuckyIslandsAutoVoteFeature {

    private static boolean shouldVote = true;

    private static void sendAllVotePackets() {
        if (mc.player == null || mc.getNetworkHandler() == null) return;

        mc.player.getInventory().selectedSlot = 1;
        mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(1));
        mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, mc.player.getYaw(), mc.player.getPitch()));

        // Vote for Game Options
        sendClickPacket(1, 12, 0);
        sendClickPacket(2, ClientConfig.instance().liGVote.getSlot(), 0);
        sendClickPacket(2, 31, 0); // Back

        // Vote for Map Mode
        sendClickPacket( 3, 14, 0);
        sendClickPacket(4, ClientConfig.instance().liTVote.getSlot(), 0);
        sendClickPacket(4, 31, 0); // Back
        sendClickPacket(5, 31, 0); // Back
    }

    public static void registerLuckyIslandsVote() {
        ClientTickEvents.START_CLIENT_TICK.register(mc -> {
            if(!ClientConfig.instance().liAutoVote || !shouldVote || mc.player == null || mc.world == null || ScoreboardUtils.getScoreboardTitle() == null || !Formatting.strip(ScoreboardUtils.getScoreboardTitle()).contains("Lucky Islands")) return;
            sendAllVotePackets();
            shouldVote = false;
        });

        WorldEvent.LOAD.register(world -> {
            if(!ClientConfig.instance().liAutoVote) return;
            shouldVote = true;
        });

    }

    public enum GameOptionVote {
        BLESSED(10),
        NORMAL(12),
        OVERPOWERED(14),
        CRAZY(16);

        private final int slot;
        GameOptionVote(int slot) {this.slot = slot;}
        public int getSlot() {return slot;}
    }

    public enum LITimeVote {
        DAY(11),
        SUNSET(13),
        NIGHT(15);

        private final int slot;
        LITimeVote(int slot) {this.slot = slot;}
        public int getSlot() {return slot;}
    }
}
