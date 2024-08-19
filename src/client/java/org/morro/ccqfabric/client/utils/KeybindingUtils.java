package org.morro.ccqfabric.client.utils;

import org.morro.ccqfabric.client.mixins.MinecraftAccessor;

import static org.morro.ccqfabric.client.CcqfabricClient.mc;

public class KeybindingUtils {
    public static void rightClick() {
        ((MinecraftAccessor) mc).invokeRightClickMouse();
    }
}
