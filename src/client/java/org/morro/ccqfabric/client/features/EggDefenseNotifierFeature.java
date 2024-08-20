package org.morro.ccqfabric.client.features;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.morro.ccqfabric.client.config.ClientConfig;
import org.morro.ccqfabric.client.events.BlockEvent;
import org.morro.ccqfabric.client.events.WorldEvent;
import org.morro.ccqfabric.client.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class EggDefenseNotifierFeature {

    public static List<Entity> blacklistedEntities = new ArrayList<>();

    public static void registerEggDefenseNotifier() {
        BlockEvent.PLACE.register((pos, state) -> {
          //  if(!ClientConfig.instance().eggDefenseNotifier) return;

            for(Entity e : MinecraftClient.getInstance().world.getEntities()) {
                    if(blacklistedEntities.contains(e) || !(e instanceof DisplayEntity.TextDisplayEntity)) continue;
                    DisplayEntity.TextDisplayEntity entity = (DisplayEntity.TextDisplayEntity) e;
                    if(!TextUtils.getDisplayedText(entity).getString().contains("'s Egg")) continue;

                    if(getBlocksAroundPosition(entity.getBlockPos().add(0, -1, 0)).stream().allMatch(blockState -> blockState.getBlock() == Blocks.ANCIENT_DEBRIS)) {
                        MinecraftClient.getInstance().player.sendMessage(TextUtils.getDisplayedText(entity).copy().append(" has been covered in Ancient Debris").formatted(Formatting.BOLD, Formatting.DARK_AQUA));
                        blacklistedEntities.add(e);
                    }
            }
        });

        WorldEvent.LOAD.register(world -> {
            if(!blacklistedEntities.isEmpty())
                blacklistedEntities.clear();
        });
    }

    public static List<BlockState> getBlocksAroundPosition(BlockPos pos) {
        List<BlockState> blocks = new ArrayList<>();
        World world = MinecraftClient.getInstance().world;

        blocks.add(world.getBlockState(pos.offset(Direction.NORTH)));
        blocks.add(world.getBlockState(pos.offset(Direction.SOUTH)));
        blocks.add(world.getBlockState(pos.offset(Direction.WEST)));
        blocks.add(world.getBlockState(pos.offset(Direction.EAST)));
        blocks.add(world.getBlockState(pos.offset(Direction.UP)));

        return blocks;
    }
}
