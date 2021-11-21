package dev.ricecx.frostygamerzone.bukkitapi.modules.nametag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Nametag {
    private final String prefix, suffix;
    private final int priority;
}