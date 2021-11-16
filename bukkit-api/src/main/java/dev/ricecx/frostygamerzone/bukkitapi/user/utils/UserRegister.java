package dev.ricecx.frostygamerzone.bukkitapi.user.utils;

import dev.ricecx.frostygamerzone.bukkitapi.user.core.DataUser;

import java.util.UUID;

/**
 * Let any class register a user to be called
 * @param <T> the user class to register
 */
public interface UserRegister<T extends DataUser> {

    T registerUser(String name, UUID uuid);
}
