package dev.ricecx.frostygamerzone.bukkitapi;
/*
 * This class provides a lazy-load Location, so that World doesn't need to be initialized
 * yet when an object of this class is created, only when the Location is first accessed.
 */

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;

import java.io.Serializable;

public class LazyLocation implements Serializable, Cloneable {

    private transient static final long serialVersionUID = -1;
    private transient Location location = null;

    private String worldName;
    private double x;
    private double y;
    private double z;

    public LazyLocation(Location loc) {
        setLocation(loc, true);
    }

    public LazyLocation(Location loc, boolean block) {
        setLocation(loc, block);
    }

    public LazyLocation(final String worldName, final double x, final double y, final double z) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static LazyLocation of(Location location) {
        return of(location, false);
    }

    public static LazyLocation of(Location location, boolean block) {
        return new LazyLocation(location, block);
    }

    public static LazyLocation getLocation(String s) {
        try {
            String[] args = s.split(",");
            return new LazyLocation(args[0], Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        } catch (Exception ignored) {
        }
        return null;
    }

    // change the Location
    public final void setLocation(Location loc, boolean block) {
        this.location = loc;

        this.worldName = loc.getWorld() != null ? loc.getWorld().getName() : null;
        if (block) {
            this.x = loc.getBlockX();
            this.y = loc.getBlockY();
            this.z = loc.getBlockZ();
        } else {
            this.x = loc.getX();
            this.y = loc.getY();
            this.z = loc.getZ();
        }
    }

    // This initializes the Location
    private void initLocation() {
        // if location is already initialized, simply return
        if (location != null) {
            return;
        }

        // get World; hopefully it's initialized at this point
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return;
        }

        // store the Location for future calls, and pass it on
        location = new Location(world, x, y, z);
    }

    // This returns the actual Location
    public final Location getLocation() {
        // make sure Location is initialized before returning it
        initLocation();
        return location;
    }

    public LazyLocation add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public LazyLocation subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public final String getWorldName() {
        return worldName;
    }

    public final double getX() {
        return x;
    }

    public final int getBlockX() {
        return (int) x;
    }

    public final double getY() {
        return y;
    }

    public final double getZ() {
        return z;
    }

    public final int getBlockZ() {
        return (int) z;
    }

    public double distance(LazyLocation o) {
        return Math.sqrt(this.distanceSquared(o));
    }

    public double distanceSquared(LazyLocation o) {
        if (o == null) {
            throw new IllegalArgumentException("Cannot measure distance to a null location");
        } else if (o.getWorldName() != null && this.getWorldName() != null) {
            if (!o.getWorldName().equals(this.getWorldName())) {
                throw new IllegalArgumentException("Cannot measure distance between " + this.getWorldName() + " and " + o.getWorldName());
            } else {
                return NumberConversions.square(this.x - o.x) + NumberConversions.square(this.y - o.y) + NumberConversions.square(this.z - o.z);
            }
        } else {
            throw new IllegalArgumentException("Cannot measure distance to a null world");
        }
    }

    @Override
    public LazyLocation clone() {
        try {
            return (LazyLocation) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public ChunkPosition toChunk() {
        return ChunkPosition.of(getBlockX() >> 4, getBlockZ() >> 4, this.worldName);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (!(o instanceof LazyLocation)) {
            return false;
        }

        final LazyLocation other = (LazyLocation) o;
        return Double.compare(this.getX(), other.getX()) == 0 &&
                Double.compare(this.getY(), other.getY()) == 0 &&
                Double.compare(this.getZ(), other.getZ()) == 0 &&
                this.getWorldName().equals(other.getWorldName());
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;

        final long x = Double.doubleToLongBits(this.getX());
        final long y = Double.doubleToLongBits(this.getY());
        final long z = Double.doubleToLongBits(this.getZ());

        result = result * PRIME + (int) (x >>> 32 ^ x);
        result = result * PRIME + (int) (y >>> 32 ^ y);
        result = result * PRIME + (int) (z >>> 32 ^ z);
        result = result * PRIME + this.getWorldName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getWorldName() + "," + getX() + "," + getY() + "," + getZ();
    }


    public void clean() {
        location = null;
    }

}