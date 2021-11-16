package dev.ricecx.frostygamerzone.bukkitapi;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * An immutable and serializable chuck location object
 */
@SuppressWarnings("all")
public final class ChunkPosition {
    private final int x;
    private final int z;
    private final String world;

    public ChunkPosition(int x, int z, String world) {
        this.x = x;
        this.z = z;
        this.world = world;
    }

    public static ChunkPosition deserialize(JsonElement element) {
        Preconditions.checkArgument(element.isJsonObject());
        JsonObject object = element.getAsJsonObject();

        Preconditions.checkArgument(object.has("x"));
        Preconditions.checkArgument(object.has("z"));
        Preconditions.checkArgument(object.has("world"));

        int x = object.get("x").getAsInt();
        int z = object.get("z").getAsInt();
        String world = object.get("world").getAsString();

        return of(x, z, world);
    }

    public static ChunkPosition of(int x, int z, String world) {
        Objects.requireNonNull(world, "world");
        return new ChunkPosition(x, z, world);
    }

    public static ChunkPosition of(int x, int z, World world) {
        Objects.requireNonNull(world, "world");
        return of(x, z, world.getName());
    }

    public static ChunkPosition of(Chunk location) {
        Objects.requireNonNull(location, "location");
        return of(location.getX(), location.getZ(), location.getWorld().getName());
    }

    public static ChunkPosition of(LazyLocation location) {
        return location.toChunk();
    }

    public static ChunkPosition of(Location location) {
        return of(location.getBlockX() >> 4, location.getBlockZ() >> 4, location.getWorld().getName());
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    public String getWorld() {
        return this.world;
    }

    public synchronized Chunk toChunk() {
        return Bukkit.getWorld(this.world).getChunkAt(this.x, this.z);
    }

    public ChunkPosition getRelative(BlockFace face) {
        Preconditions.checkArgument(face != BlockFace.UP && face != BlockFace.DOWN, "invalid face");
        return ChunkPosition.of(this.x + face.getModX(), this.z + face.getModZ(), this.world);
    }

    public ChunkPosition getRelative(BlockFace face, int distance) {
        Preconditions.checkArgument(face != BlockFace.UP && face != BlockFace.DOWN, "invalid face");
        return ChunkPosition.of(this.x + face.getModX() * distance, this.z + face.getModZ() * distance, this.world);
    }

    public Set<ChunkPosition> getNearbyChunksAndSelf() {
        BlockFace[] faces = {
                BlockFace.NORTH,
                BlockFace.NORTH_EAST,
                BlockFace.EAST,
                BlockFace.SOUTH_EAST,
                BlockFace.SOUTH,
                BlockFace.SOUTH_WEST,
                BlockFace.WEST,
                BlockFace.NORTH_WEST,
        };


        Set<ChunkPosition> chunksAround = new HashSet<>();
        for (BlockFace face : faces) {
            chunksAround.add(getRelative(face));
        }

        chunksAround.add(this);

        return chunksAround;
    }

    public ChunkPosition add(int x, int z) {
        return ChunkPosition.of(this.x + x, this.z + z, this.world);
    }

    public ChunkPosition subtract(int x, int z) {
        return add(-x, -z);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ChunkPosition)) return false;
        final ChunkPosition other = (ChunkPosition) o;
        return this.getX() == other.getX() && this.getZ() == other.getZ() && this.getWorld().equals(other.getWorld());
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getX();
        result = result * PRIME + this.getZ();
        result = result * PRIME + this.getWorld().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ChunkPosition(x=" + this.getX() + ", z=" + this.getZ() + ", world=" + this.getWorld() + ")";
    }

    public String toFileName() {
        return getWorld() + "," + getX() + "," + getZ();
    }


}
