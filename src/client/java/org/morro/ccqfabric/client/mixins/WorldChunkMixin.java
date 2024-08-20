package org.morro.ccqfabric.client.mixins;


import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import org.morro.ccqfabric.client.config.ClientConfig;
import org.morro.ccqfabric.client.events.BlockEvent;
import org.morro.ccqfabric.client.features.EggDefenseNotifierFeature;
import org.morro.ccqfabric.client.features.LobbyChestFinderFeature;
import org.morro.ccqfabric.client.utils.ScoreboardUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(WorldChunk.class)
public class WorldChunkMixin {

    @Unique
    private final List<BlockPos> normalChests = List.of(new BlockPos(466, 56, -404), new BlockPos(438, 56, -404), new BlockPos(436, 48, -349));

    @Inject(method="addBlockEntity", at=@At("HEAD"))
    public void setBlockEntity(BlockEntity par1, CallbackInfo ci) {
        if(!ClientConfig.instance().generalChestFinder) return;
        if(par1 instanceof ChestBlockEntity && !normalChests.contains(par1.getPos()) && ScoreboardUtils.getScoreboardTitle() != null && ScoreboardUtils.getScoreboardTitle().contains("CubeCraft")) {
            LobbyChestFinderFeature.chestPosition = par1.getPos();
            MinecraftClient.getInstance().player.sendMessage(Text.literal("Chest found at ").formatted(Formatting.GOLD).append(Text.literal(par1.getPos().getX() + " " + par1.getPos().getY() + " " + par1.getPos().getZ()).formatted(Formatting.GREEN, Formatting.BOLD)));
        }
    }

    @Inject(method="setBlockState", at=@At("HEAD"))
    public void setBlockState(BlockPos pos, BlockState state, boolean moved, CallbackInfoReturnable<BlockState> cir) {
            if(state.getBlock() != Blocks.AIR) {
                BlockEvent.PLACE.invoker().onBlockPlace(pos, state);
            }
    }



}
