package org.morro.ccqfabric.client.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import org.morro.ccqfabric.client.config.ClientConfig;
import org.morro.ccqfabric.client.events.WorldEvent;
import org.morro.ccqfabric.client.utils.ScoreboardUtils;
import static org.morro.ccqfabric.client.utils.PacketUtils.sendClickPacket;
import static org.morro.ccqfabric.client.CcqfabricClient.mc;

public class EggwarsAutoVoteFeature {

    private static boolean shouldVote = false;

    private static void sendAllVotePackets() {
        if (mc.player == null || mc.getNetworkHandler() == null) return;

        mc.player.getInventory().selectedSlot = 2;
        mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(2));
        mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, mc.player.getYaw(), mc.player.getPitch()));


        // Vote for Health
        sendClickPacket(1, 15, 0);
        sendClickPacket(2, ClientConfig.instance().eggHVote.getSlot(), 0);
        sendClickPacket(2, 31, 0); // Back

        // Vote for Items
        sendClickPacket( 3, 13, 0);
        sendClickPacket(4, ClientConfig.instance().eggIVote.getSlot(), 0);
        sendClickPacket(4, 31, 0); // Back

        // Vote for Perks
        sendClickPacket(5, 11, 0);
        sendClickPacket(6, ClientConfig.instance().eggPVote.getSlot(), 0);
        sendClickPacket(6, 31, 0); // Back
        sendClickPacket(7, 31, 0); // Back
    }


    public static void registerEggwarsAutoVote() {
        ClientTickEvents.START_CLIENT_TICK.register(mc -> {
            if(!ClientConfig.instance().eggAutoVote || !shouldVote || mc.player == null || mc.world == null || ScoreboardUtils.getScoreboardTitle() == null || !Formatting.strip(ScoreboardUtils.getScoreboardTitle()).contains("Team EggWars")) return;
            sendAllVotePackets();
            shouldVote = false;
        });

        WorldEvent.LOAD.register( world -> {
            if(!ClientConfig.instance().eggAutoVote) return;
            shouldVote = true;
        });

    }


    public enum ItemVote {
        OVERPOWERED( 15),
        NORMAL(13),
        HARDCORE( 11);

        private final int slot;
        ItemVote(int slot) {this.slot = slot;}
        public int getSlot() {return slot;}
    }

    public enum HealthVote {
        HALF(11),
        NORMAL(13),
        DOUBLE(15);

        private final int slot;
        HealthVote(int slot) {this.slot = slot;}
        public int getSlot() {return slot;}
    }

    public enum PerkVote {
        NO_FREE_PERK(12),
        FREE_PERK(14);

        private final int slot;
        PerkVote(int slot) {this.slot = slot;}
        public int getSlot() {return slot;}
    }
}
