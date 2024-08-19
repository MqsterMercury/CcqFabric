package org.morro.ccqfabric.client.utils;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;

import static org.morro.ccqfabric.client.CcqfabricClient.mc;

public class ScoreboardUtils {

    public static String getScoreboardTitle() {
        if (mc.world == null) return null;

        Scoreboard scoreboard = mc.world.getScoreboard();
        for (ScoreboardObjective objective : scoreboard.getObjectives()) {
            if (scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR) == objective) {
                return objective.getDisplayName().getString();
            }
        }

        return null;
    }

    public static class SidebarEntry {
        public final Text name;
        public final Text score;
        public final int scoreWidth;

        public SidebarEntry(Text name, Text score, int scoreWidth) {
            this.name = name;
            this.score = score;
            this.scoreWidth = scoreWidth;
        }
    }

}