package dev.ricecx.frostygamerzone.api;

public enum Rank {
    /* Staff Rank */
    OWNER(1, "owner", "&cOwner"),
    ADMIN(2, "admin", "&3Admin"),
    MODERATOR(3, "moderator", "&eModerator"),
    HELPER(4, "helper", "&bHelper"),
    TRIAL(5, "trial", "&aTrial"),

    /* Member Ranks */
    MASTER(6, "master", "&5Master"),
    MAGE(7, "mage", "&dMage"),
    MERCENARY(8, "mercenary", "&6Mercenary"),
    KNIGHT(9, "knight", "&9Knight"),
    MEMBER(10, "member", "&7Regular"),
    ;

    public static Rank[] CACHE = values();

    public static Rank fromName(String name) {
        Rank foundRank = null;
        for (Rank rank : CACHE) {
            if(rank.group.equalsIgnoreCase(name)) {
                foundRank = rank;
                break;
            }
        }

        return foundRank;
    }

    private final int priority;
    private final String group;
    private final String displayName;

    Rank(int priority, String group, String displayName) {
        this.priority = priority;
        this.group = group;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPriority() {
        return priority;
    }
}
