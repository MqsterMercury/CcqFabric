package org.morro.ccqfabric.client.utils;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class RankUtils {

    public enum Rank {
        STONE("\ue1ab", Text.empty()
                .append(Text.literal("!").formatted(Formatting.DARK_GRAY, Formatting.OBFUSCATED))
                .append(Text.literal("Stone").formatted(Formatting.DARK_GRAY))
                .append(Text.literal("!").formatted(Formatting.DARK_GRAY, Formatting.OBFUSCATED)), Formatting.DARK_GRAY),

        IRON("\ue1ac", Text.empty()
                .append(Text.literal("!").formatted(Formatting.GRAY, Formatting.OBFUSCATED))
                .append(Text.literal("Iron").formatted(Formatting.GRAY))
                .append(Text.literal("!").formatted(Formatting.GRAY, Formatting.OBFUSCATED)), Formatting.GRAY),

        DIAMOND("\ue1ad", Text.empty()
                .append(Text.literal("!").formatted(Formatting.AQUA, Formatting.OBFUSCATED))
                .append(Text.literal("Diamond").formatted(Formatting.AQUA))
                .append(Text.literal("!").formatted(Formatting.AQUA, Formatting.OBFUSCATED)), Formatting.AQUA),

        OBSIDIAN("\ue1ae", Text.empty()
                .append(Text.literal("!").formatted(Formatting.DARK_PURPLE, Formatting.OBFUSCATED))
                .append(Text.literal("Obsidian").formatted(Formatting.DARK_PURPLE))
                .append(Text.literal("!").formatted(Formatting.DARK_PURPLE, Formatting.OBFUSCATED)), Formatting.DARK_PURPLE);

        public final String rankChar;
        public final Text replacement;
        public final Formatting color;

        public String getName() {
            return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
        }

        Rank(String rankChar, Text replacement, Formatting color) {
            this.rankChar = rankChar;
            this.replacement = replacement;
            this.color = color;
        }
    }
}
