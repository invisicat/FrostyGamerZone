package dev.ricecx.frostygamerzone.bukkitapi.scoreboard;

import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import org.bukkit.entity.Player;

public abstract class FrostBoard extends FrostFastBoard {

    public FrostBoard(Player player, String objectiveName) {
        super(player);
        updateTitle(Utils.color(objectiveName));
    }

    public abstract void updateBoard();
}
