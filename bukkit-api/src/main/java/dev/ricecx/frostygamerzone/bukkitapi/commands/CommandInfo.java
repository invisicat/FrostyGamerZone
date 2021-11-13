package dev.ricecx.frostygamerzone.bukkitapi.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {
    String name();
    String permissions() default "group.default";
    String description() default "No description provided";
    CommandSenderType[] senderTypes() default CommandSenderType.PLAYER;
}
