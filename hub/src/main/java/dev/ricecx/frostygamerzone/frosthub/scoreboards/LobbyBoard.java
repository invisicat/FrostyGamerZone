package dev.ricecx.frostygamerzone.frosthub.scoreboards;

import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.bukkitapi.scoreboard.FrostBoard;

import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.bukkitapi.user.core.FrostUser;
import dev.ricecx.frostygamerzone.frosthub.Frosthub;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LobbyBoard extends FrostBoard {

    private final Player player;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");


    public LobbyBoard(Player player) {
        super(player, "&bFrosty&9Network");
        this.player = player;
    }

    @Override
    public void updateBoard() {
        String prefix = Frosthub.getInstance().getCoreAPI().getLuckPermsAPI().getPrefix(player.getUniqueId());
        player.setPlayerListName(Utils.color(prefix + "&r" +  player.getDisplayName()));

        FrostUser user = Users.getUser(player, FrostUser.class);
        if(user == null) { player.kickPlayer("Your data isn't loaded properly."); return; }
        String[] lines = new String[]{
                "        &7" + dtf.format(LocalDateTime.now()),
                " ",
                "&eLevel:&f " + user.getLevel() + " ☆",
                " ",
                "&3Rank:&f " + Utils.color(prefix),
                " ",
                "&bCoins:&e " + user.getCoins() + " ⛃",
                " ",
                "&bOnline:&f " + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(),
                " ",
                "&bplay.frostynetwork.net"
        };
        updateLines(lines);
    }
}
