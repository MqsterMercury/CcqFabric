package org.morro.ccqfabric.client.utils;

import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

import static org.morro.ccqfabric.client.CcqfabricClient.mc;

public class PacketUtils {

    public static void sendClickPacket(int syncId, int slotId, int button) {
        ClickSlotC2SPacket packet = new ClickSlotC2SPacket(
                syncId,
                0,
                slotId,
                button,
                SlotActionType.PICKUP,
                ItemStack.EMPTY,
                Int2ObjectMaps.emptyMap()
        );
        mc.getNetworkHandler().sendPacket(packet);
    }


}
