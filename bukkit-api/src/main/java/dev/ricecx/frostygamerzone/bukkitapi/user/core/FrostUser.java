package dev.ricecx.frostygamerzone.bukkitapi.user.core;

import dev.ricecx.frostygamerzone.api.Rank;

public interface FrostUser extends DataUser {
    Rank getRank();

    int getCoins();
    void setCoins(int newCoins);

    int getLevel();
    void setLevel(int newLevel);
}
