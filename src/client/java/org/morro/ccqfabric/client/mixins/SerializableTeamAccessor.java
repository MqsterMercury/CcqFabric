package org.morro.ccqfabric.client.mixins;

import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TeamS2CPacket.SerializableTeam.class)
public interface SerializableTeamAccessor {

    @Mutable
    @Accessor("prefix")
    void setPrefix(Text prefix);

    @Mutable
    @Accessor("suffix")
    void setSuffix(Text suffix);

}
