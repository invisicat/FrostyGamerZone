package dev.ricecx.frostygamerzone.bukkitapi.modules.nametag.protocol;

import dev.ricecx.frostygamerzone.bukkitapi.Version;
import dev.ricecx.frostygamerzone.bukkitapi.modules.nametag.NametagHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Braydon (credits: https://github.com/sgtcaze/NametagEdit)
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class PacketWrapper {
    private static Constructor<?> CHAT_COMPONENT_CONSTRUCTOR;
    private static Class<? extends Enum> CHAT_FORMAT_TYPE;

    static {
        String version = Version.NMS_VERSION;
        try {
            Class<?> typeChatComponentText;
            if(Version.getServerVersion().isNewerOrSameThan(Version.v1_16_R1)) {
                typeChatComponentText = Class.forName("net.minecraft.network.chat.ChatComponentText");
                CHAT_FORMAT_TYPE = (Class<? extends Enum<?>>) Class.forName("net.minecraft.EnumChatFormat");
            } else {
                typeChatComponentText = Class.forName("net.minecraft.server." + version + ".ChatComponentText");
                CHAT_FORMAT_TYPE = (Class<? extends Enum<?>>) Class.forName("net.minecraft.server." + version + ".EnumChatFormat");
            }
            CHAT_COMPONENT_CONSTRUCTOR = typeChatComponentText.getConstructor(String.class);
        } catch (ClassNotFoundException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    private final Object packet = PacketAccessor.createPacket();

    public PacketWrapper(String name, int param, List<String> members) {
        if (param != 3 && param != 4)
            throw new IllegalArgumentException("Method must be join or leave for player constructor");
        setupDefaults(name, param);
        setupMembers(members);
    }

    public PacketWrapper(String name, String prefix, String suffix, int param, Collection<?> players) {
        setupDefaults(name, param);

        if (param == 0 || param == 2) {
            try {
                if (param == 0)
                    ((Collection) PacketAccessor.MEMBERS.get(packet)).addAll(players);
                String color = ChatColor.getLastColors(prefix);
                String colorCode = null;

                if (!color.isEmpty()) {
                    colorCode = color.substring(color.length() - 1);
                    String chatColor = ChatColor.getByChar(colorCode).name();
                    if (chatColor.equalsIgnoreCase("MAGIC"))
                        chatColor = "OBFUSCATED";
                    Enum<?> colorEnum = Enum.valueOf(CHAT_FORMAT_TYPE, chatColor);
                    PacketAccessor.TEAM_COLOR.set(packet, colorEnum);
                }
                PacketAccessor.PREFIX.set(packet, CHAT_COMPONENT_CONSTRUCTOR.newInstance(prefix));

                if (colorCode != null)
                    suffix = ChatColor.getByChar(colorCode) + suffix;
                PacketAccessor.SUFFIX.set(packet, CHAT_COMPONENT_CONSTRUCTOR.newInstance(suffix));

                PacketAccessor.DISPLAY_NAME.set(packet, CHAT_COMPONENT_CONSTRUCTOR.newInstance(name));
                PacketAccessor.PACK_OPTION.set(packet, 1);

                if (PacketAccessor.VISIBILITY != null)
                    PacketAccessor.VISIBILITY.set(packet, "always");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Send the wrapped packet to the loaded players in the server
     */
    public void send() {
        PacketAccessor.sendPacket(Bukkit.getOnlinePlayers(), packet);
    }

    /**
     * Send the wrapped packet to the given {@link Player}
     *
     * @param player the player to send the wrapped packet to
     */
    public void send(Player player) {
        PacketAccessor.sendPacket(player, packet);
    }

    private void setupDefaults(String name, int param) {
        try {
            PacketAccessor.TEAM_NAME.set(packet, name);
            PacketAccessor.PARAM_INT.set(packet, param);
            if (NametagHandler.DISABLE_PUSH && PacketAccessor.PUSH != null)
                PacketAccessor.PUSH.set(packet, "never");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setupMembers(Collection<?> players) {
        try {
            players = players == null || players.isEmpty() ? new ArrayList<>() : players;
            ((Collection) PacketAccessor.MEMBERS.get(packet)).addAll(players);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}