package org.morro.ccqfabric.client.utils;

import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Formatting;
import static org.morro.ccqfabric.client.CcqfabricClient.mc;

public class InventoryUtils {

    public static String getInventoryName() {
        if (mc.currentScreen instanceof GenericContainerScreen) {
            return purify(mc.currentScreen.getTitle().getString());
        }
        return null;
    }

    public static Slot getSlotOfItemInContainer(String item, Boolean equals) {
        if (mc.player == null) return null;

        for (Slot slot : mc.player.currentScreenHandler.slots) {
            if (slot.hasStack()) {
                String itemName = Formatting.strip(slot.getStack().getName().getString());
                if (equals) {
                    if (itemName.equalsIgnoreCase(item)) {
                        return slot;
                    }
                } else {
                    if (item != null && itemName.contains(item)) {
                        return slot;
                    }
                }
            }
        }
        return null;
    }

    public static int findItemInHotbar(String name) {
        for (int i = 0; i < 9; i++) {
            ItemStack curStack = mc.player.getInventory().getStack(i);
            if (!curStack.isEmpty()) {
                String itemName = curStack.getName().getString();
                if (itemName.toLowerCase().contains(name.toLowerCase())) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void clickSlot(int slotNum) {
        if(mc.currentScreen == null || mc.interactionManager == null || mc.player == null) return;
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slotNum,0, SlotActionType.PICKUP, mc.player);
    }

    private static String purify(String input) {
        if (input == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetterOrDigit(c) || c == ' ' || c == ':' || c == '.') {
                result.append(c);
            }
        }
        return result.toString();
    }

}
