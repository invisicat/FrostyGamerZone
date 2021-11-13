package dev.ricecx.frostygamerzone.bukkitapi.module;

import org.bukkit.Material;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface PlayerConfig {
    Material icon() default Material.DIAMOND_SWORD;
}
