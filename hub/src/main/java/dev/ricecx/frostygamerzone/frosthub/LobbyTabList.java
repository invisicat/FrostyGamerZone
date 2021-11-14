package dev.ricecx.frostygamerzone.frosthub;

import dev.ricecx.frostygamerzone.bukkitapi.Styles;
import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.bukkitapi.tab.Tablist;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class LobbyTabList extends Tablist {
    @Override
    public void sendTabList(Player player) {
        String title = Utils.color(" \n"+ Styles.PREFIX + "\n ");
        String subTitle = Utils.color(" \n&6" + "Hub" + " &e" + player.getPing() + "ms\n" +
                "&e" + Bukkit.getOnlinePlayers().size() + " &fplayers &7(" + Bukkit.getOnlinePlayers().size() + " in this server)\n" +
                Styles.IP + "\n ");


        IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}");
        IChatBaseComponent tabSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subTitle + "\"}");

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabTitle, tabSubTitle);

        try {
            Tablist.subTitle.set(packet, tabSubTitle);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ((CraftPlayer) player).getHandle().b.sendPacket(packet);
        }
    }
}
