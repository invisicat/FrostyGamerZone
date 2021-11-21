package dev.ricecx.frostygamerzone.bukkitapi.modules.nametag.protocol;

import dev.ricecx.frostygamerzone.bukkitapi.Version;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Braydon (credits: https://github.com/sgtcaze/NametagEdit)
 */
public class PacketAccessor {
    public static Field MEMBERS;
    public static Field PREFIX;
    public static Field SUFFIX;
    public static Field TEAM_NAME;
    public static Field PARAM_INT;
    public static Field PACK_OPTION;
    public static Field DISPLAY_NAME;
    public static Field TEAM_COLOR;
    public static Field PUSH;
    public static Field VISIBILITY;

    private static Method getHandle;
    private static Method sendPacket;
    private static Field playerConnection;

    private static Class<?> packetClass;

    static {
        try {
            String version = Version.NMS_VERSION;
            Class<?> typeCraftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            getHandle = typeCraftPlayer.getMethod("getHandle");

            packetClass = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam");
            Class<?> typeNMSPlayer = Class.forName("net.minecraft.server.level.EntityPlayer");
            Class<?> typePlayerConnection = Class.forName("net.minecraft.server.network.PlayerConnection");
            playerConnection = typeNMSPlayer.getField("b");
            sendPacket = typePlayerConnection.getMethod("sendPacket", Class.forName("net.minecraft.network.protocol.Packet"));

            PacketData currentVersion = Arrays.stream(PacketData.values())
                    .filter(packetData -> version.contains(packetData.name()))
                    .findFirst().orElse(null);
            if (currentVersion != null) {
                MEMBERS = getField(currentVersion.getMembers());
                PREFIX = getField(currentVersion.getPrefix());
                SUFFIX = getField(currentVersion.getSuffix());
                TEAM_NAME = getField(currentVersion.getTeamName());
                PARAM_INT = getField(currentVersion.getParamInt());
                PACK_OPTION = getField(currentVersion.getPackOption());
                DISPLAY_NAME = getField(currentVersion.getDisplayName());

                // If the server is running a native version, we wanna support team colors
                TEAM_COLOR = getField(currentVersion.getColor());

                // If the version is a pushed version
                if (Integer.parseInt(version.split("_")[1]) >= 9)
                    PUSH = getField(currentVersion.getPush());

                // If the version is a visibility version
                if (Integer.parseInt(version.split("_")[1]) >= 8)
                    VISIBILITY = getField(currentVersion.getVisibility());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Create a new instance of {@link PacketPlayOutScoreboardTeam}
     *
     * @return the new instance
     */
    public static Object createPacket() {
        try {
            return packetClass.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Send the given packet to the {@link Collection<Player>}
     *
     * @param players the players to send the packet to
     * @param packet  the packet to send
     */
    public static void sendPacket(Collection<? extends Player> players, Object packet) {
        for (Player player : players)
            sendPacket(player, packet);
    }

    /**
     * Send a packet to the given {@link Player}
     *
     * @param player the player to send the packet to
     * @param packet the packet to send
     */
    public static void sendPacket(Player player, Object packet) {
        try {
            Object nmsPlayer = getHandle.invoke(player);
            Object connection = playerConnection.get(nmsPlayer);
            sendPacket.invoke(connection, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get a field in the packet by the given name
     *
     * @param name the name of the field
     * @return the field
     * @throws Exception the exception
     */
    private static Field getField(String name) throws Exception {
        Field field = packetClass.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }
}