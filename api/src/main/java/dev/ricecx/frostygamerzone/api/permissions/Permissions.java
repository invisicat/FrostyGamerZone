package dev.ricecx.frostygamerzone.api.permissions;

public enum Permissions {
    ADMIN_BLOCK_BREAK("fn.blockbreak")
    ;

    private final String permission;
    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
