package dev.ricecx.frostygamerzone.api.joinmessages;

public enum JoinMessages {
    DEFAULT("%s &7flew into the lobby!")
    ;

    private final String joinMessage;
    JoinMessages(String joinMessage) {
        this.joinMessage = joinMessage;
    }


    public final String getMessage(String name) {
        return joinMessage.replace("%s", name);
    }
}
