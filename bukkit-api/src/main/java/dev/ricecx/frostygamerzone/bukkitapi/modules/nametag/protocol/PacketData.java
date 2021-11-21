package dev.ricecx.frostygamerzone.bukkitapi.modules.nametag.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Braydon (credits: https://github.com/sgtcaze/NametagEdit)
 * @implNote This class holds the field names for the @link PacketPlayOutScoreboardTeam packet for each
 *           Minecraft version
 */
@AllArgsConstructor @Getter
public enum PacketData {
    v1_7("e", "c", "d", "a", "f", "g", "b", "NA", "NA", "NA"),
    v1_8("g", "c", "d", "a", "h", "i", "b", "NA", "NA", "e"),
    v1_9("h", "c", "d", "a", "i", "j", "b", "NA", "f", "e"),
    v1_10("h", "c", "d", "a", "i", "j", "b", "NA", "f", "e"),
    v1_11("h", "c", "d", "a", "i", "j", "b", "NA", "f", "e"),
    v1_12("h", "c", "d", "a", "i", "j", "b", "NA", "f", "e"),
    v1_13("h", "c", "d", "a", "i", "j", "b", "g", "f", "e"),
    v1_14("h", "c", "d", "a", "i", "j", "b", "g", "f", "e"),
    v1_15("h", "c", "d", "a", "i", "j", "b", "g", "f", "e"),
    v1_16("h", "c", "d", "a", "i", "j", "b", "g", "f", "e");

    private final String members, prefix, suffix, teamName, paramInt, packOption, displayName, color, push, visibility;
}