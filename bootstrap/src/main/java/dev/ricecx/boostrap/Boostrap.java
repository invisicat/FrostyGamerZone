package dev.ricecx.boostrap;

import me.lucko.jarrelocator.JarRelocator;
import me.lucko.jarrelocator.Relocation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Boostrap {
    public static void loadCore(File dataFolder, String path) {

        File input = new File("bukkit-api.jar");
        File output = new File(dataFolder, "relocated-bukkit-api.jar");

        List<Relocation> rules = new ArrayList<>();
        rules.add(new Relocation("dev.ricecx.frostygamerzone.bukkitapi", "dev.ricecx.frostygamerzone.bukkit-api"));

        JarRelocator relocator = new JarRelocator(input, output, rules);

        try {
            relocator.run();
        } catch (IOException e) {
            throw new RuntimeException("Unable to relocate", e);
        }

    }
}
