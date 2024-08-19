package org.morro.ccqfabric.client.config;


import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.morro.ccqfabric.client.features.BedwarsAutoVoteFeature.ModifierVote;
import org.morro.ccqfabric.client.features.EggwarsAutoVoteFeature.HealthVote;
import org.morro.ccqfabric.client.features.EggwarsAutoVoteFeature.ItemVote;
import org.morro.ccqfabric.client.features.EggwarsAutoVoteFeature.PerkVote;
import org.morro.ccqfabric.client.features.SkywarsAutoVoteFeature.SkywarsTimeVote;
import org.morro.ccqfabric.client.features.SkywarsAutoVoteFeature.ProjectileVote;
import org.morro.ccqfabric.client.features.SkywarsAutoVoteFeature.ChestVote;
import org.morro.ccqfabric.client.features.POFAutoVoteFeature.GameModeVote;
import org.morro.ccqfabric.client.features.POFAutoVoteFeature.MapModeVote;
import org.morro.ccqfabric.client.features.LuckyIslandsAutoVoteFeature.GameOptionVote;
import org.morro.ccqfabric.client.features.LuckyIslandsAutoVoteFeature.LITimeVote;


import java.nio.file.Path;

public class ClientConfig {
    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("ccqfabric.json") ;

    private static final ConfigClassHandler<ClientConfig> HANDLER = ConfigClassHandler.createBuilder(ClientConfig.class)
            .id(Identifier.of("ccqfabric", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(CONFIG_PATH)
                    .setJson5(true)
                    .build())
            .build();

    /*
    EGGWARS
     */
    @SerialEntry
    @AutoGen(category = "Eggwars")
    @Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
    public boolean eggAutoVote = true;

    @SerialEntry
    @AutoGen(category = "Eggwars")
    @EnumCycler()
    public ItemVote eggIVote = ItemVote.OVERPOWERED;

    @SerialEntry
    @AutoGen(category = "Eggwars")
    @EnumCycler()
    public HealthVote eggHVote = HealthVote.HALF;

    @SerialEntry
    @AutoGen(category = "Eggwars")
    @EnumCycler()
    public PerkVote eggPVote = PerkVote.NO_FREE_PERK;

    /*
    SKYWARS
     */

    @SerialEntry
    @AutoGen(category = "Skywars")
    @Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
    public boolean skyAutoVote = true;

    @SerialEntry
    @AutoGen(category = "Skywars")
    @EnumCycler()
    public ChestVote skyCVote = ChestVote.NORMAL;

    @SerialEntry
    @AutoGen(category = "Skywars")
    @EnumCycler()
    public ProjectileVote skyPVote = ProjectileVote.NORMAL_PROJECTILES;

    @SerialEntry
    @AutoGen(category = "Skywars")
    @EnumCycler()
    public SkywarsTimeVote skyTVote = SkywarsTimeVote.SUNSET;

    /*
    Pillars Of Fortune
     */

    @SerialEntry
    @AutoGen(category = "POF")
    @Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
    public boolean pofAutoVote = true;

    @SerialEntry
    @AutoGen(category = "POF")
    @EnumCycler()
    public GameModeVote pofGVote = GameModeVote.NORMAL;

    @SerialEntry
    @AutoGen(category = "POF")
    @EnumCycler()
    public MapModeVote pofMVote = MapModeVote.NORMAL;

    /* BedWars */

    @SerialEntry
    @AutoGen(category = "Bedwars")
    @Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
    public boolean bedwarsAutoVote = true;

    @SerialEntry
    @AutoGen(category = "Bedwars")
    @EnumCycler()
    public ModifierVote bedwarsMVote = ModifierVote.NORMAL;

    /* Lucky Islands */

    @SerialEntry
    @AutoGen(category = "LuckyIslands")
    @Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
    public boolean liAutoVote = true;

    @SerialEntry
    @AutoGen(category = "LuckyIslands")
    @EnumCycler()
    public GameOptionVote liGVote = GameOptionVote.NORMAL;

    @SerialEntry
    @AutoGen(category = "LuckyIslands")
    @EnumCycler()
    public LITimeVote liTVote = LITimeVote.SUNSET;

    /* ----------- */

    public static void load() {
        HANDLER.load();
    }

    public static void save() {
        HANDLER.save();
    }

    public static ClientConfig instance() {
        return HANDLER.instance();
    }

    public static Screen createScreen(@Nullable Screen parent) {
        return HANDLER.generateGui().generateScreen(parent);
    }

}
