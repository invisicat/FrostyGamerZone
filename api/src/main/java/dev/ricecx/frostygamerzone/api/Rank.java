package dev.ricecx.frostygamerzone.api;

public enum Rank {
    /* Staff Rank */
    OWNER(1),
    ADMIN(2),
    MODERATOR(3),
    HELPER(4),
    TRIAL(5),

    /* Member Ranks */
    MASTER(6),
    MAGE(7),
    MERCENARY(8),
    KNIGHT(9),
    MEMBER(10),
    ;

    Rank(int priority) {
    }
}
