package org.morro.ccqfabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import org.lwjgl.glfw.GLFW;
import org.morro.ccqfabric.client.config.ClientConfig;
import org.morro.ccqfabric.client.features.*;
import org.morro.ccqfabric.client.utils.RenderUtils;
import java.awt.*;

public class CcqfabricClient implements ClientModInitializer {

    public static MinecraftClient mc = MinecraftClient.getInstance();
    @Override
    public void onInitializeClient() {
        ClientConfig.load();
        registerKeybinding();
        EggwarsAutoVoteFeature.registerEggwarsAutoVote();
        SkywarsAutoVoteFeature.registerSkywarsAutoVote();
        POFAutoVoteFeature.registerPOFAutoVote();
        BedwarsAutoVoteFeature.registerBedwarsAutoVote();
        LuckyIslandsAutoVoteFeature.registerLuckyIslandsVote();
        LobbyChestFinderFeature.registerLobbyChestFinder();
        //EggDefenseNotifierFeature.registerEggDefenseNotifier();
        ClientLifecycleEvents.CLIENT_STOPPING.register(mc -> {
            ClientConfig.save();
        });
    }

    private void registerKeybinding() {
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ccqfabric.openconfig",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.ccqfabric.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {
                openConfigScreen();

            }
        });
    }

    private void openConfigScreen() {
        MinecraftClient.getInstance().execute(() -> {
            Screen screen = ClientConfig.createScreen(MinecraftClient.getInstance().currentScreen);
            MinecraftClient.getInstance().setScreen(screen);
        });
    }

}
