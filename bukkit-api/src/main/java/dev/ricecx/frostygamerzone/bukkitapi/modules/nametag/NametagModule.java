package dev.ricecx.frostygamerzone.bukkitapi.modules.nametag;


import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.module.ModuleInfo;
import dev.ricecx.frostygamerzone.bukkitapi.modules.nametag.protocol.PacketWrapper;
import dev.ricecx.frostygamerzone.bukkitapi.modules.nametag.team.FakeTeam;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

@ModuleInfo
public class NametagModule extends Module {
    private static final HashMap<String, FakeTeam> TEAMS = new HashMap<>();
    private static final HashMap<String, FakeTeam> CACHED_FAKE_TEAMS = new HashMap<>();

    public NametagModule() {
        CorePlugin.getInstance().registerListeners(new NametagHandler());
    }

    public void setNametag(Player player, String prefix, String suffix, int priority) {
        if (prefix == null)
            prefix = "§r";
        prefix = Utils.color(prefix);
        if (suffix == null)
            suffix = "§r";
        suffix = Utils.color(suffix);
        FakeTeam previous = getFakeTeam(player);
        if (previous != null && previous.isSimilar(prefix, suffix))
            return;
        reset(player);
        FakeTeam team = getFakeTeam(prefix, suffix);
        if (team != null) {
            team.addMember(player);
        } else {
            team = new FakeTeam(prefix, suffix, priority);
            team.addMember(player);
            TEAMS.put(team.getName(), team);
            addTeamPackets(team);
        }
        addPlayerToTeamPackets(team, player.getName());
        cache(player.getName(), team);
    }

    protected void sendTeams(Player player) {
        for (FakeTeam fakeTeam : TEAMS.values())
            new PacketWrapper(fakeTeam.getName(), fakeTeam.getPrefix(), fakeTeam.getSuffix(), 0, fakeTeam.getMembers()).send(player);
    }

    protected void reset(Player player) {
        reset(player.getName());
    }

    protected void reset(String playerName) {
        reset(playerName, CACHED_FAKE_TEAMS.remove(playerName));
    }

    public void cleanup() {
        for (FakeTeam fakeTeam : TEAMS.values()) {
            removePlayerFromTeamPackets(fakeTeam, fakeTeam.getMembers());
            removeTeamPackets(fakeTeam);
        }
        TEAMS.clear();
        CACHED_FAKE_TEAMS.clear();
    }

    private void reset(String player, FakeTeam fakeTeam) {
        if (fakeTeam != null && fakeTeam.getMembers().remove(player)) {
            boolean delete;
            Player removing = Bukkit.getPlayerExact(player);
            if (removing != null) {
                delete = removePlayerFromTeamPackets(fakeTeam, removing.getName());
            } else {
                OfflinePlayer toRemoveOffline = Bukkit.getOfflinePlayer(player);
                delete = removePlayerFromTeamPackets(fakeTeam, toRemoveOffline.getName());
            }
            if (delete) {
                removeTeamPackets(fakeTeam);
                TEAMS.remove(fakeTeam.getName());
            }
        }
    }

    private void cache(String playerName, FakeTeam fakeTeam) {
        CACHED_FAKE_TEAMS.put(playerName, fakeTeam);
    }

    private FakeTeam getFakeTeam(Player player) {
        return getFakeTeam(player.getName());
    }

    private FakeTeam getFakeTeam(String playerName) {
        return CACHED_FAKE_TEAMS.get(playerName);
    }

    private FakeTeam getFakeTeam(String prefix, String suffix) {
        return TEAMS.values().stream().filter(fakeTeam -> fakeTeam.isSimilar(prefix, suffix)).findFirst().orElse(null);
    }

    private void addTeamPackets(FakeTeam fakeTeam) {
        new PacketWrapper(fakeTeam.getName(), fakeTeam.getPrefix(), fakeTeam.getSuffix(), 0, fakeTeam.getMembers()).send();
    }

    private void addPlayerToTeamPackets(FakeTeam fakeTeam, String player) {
        new PacketWrapper(fakeTeam.getName(), 3, Collections.singletonList(player)).send();
    }

    private boolean removePlayerFromTeamPackets(FakeTeam fakeTeam, String... players) {
        return removePlayerFromTeamPackets(fakeTeam, Arrays.asList(players));
    }

    private boolean removePlayerFromTeamPackets(FakeTeam fakeTeam, List<String> players) {
        new PacketWrapper(fakeTeam.getName(), 4, players).send();
        fakeTeam.getMembers().removeAll(players);
        return fakeTeam.getMembers().isEmpty();
    }

    private void removeTeamPackets(FakeTeam fakeTeam) {
        new PacketWrapper(fakeTeam.getName(), fakeTeam.getPrefix(), fakeTeam.getSuffix(), 1, new ArrayList<>()).send();
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        cleanup();
    }
}