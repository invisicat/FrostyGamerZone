package dev.ricecx.frostygamerzone.bukkitapi.module;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ModuleConfiguration {

    public ModuleConfiguration() {
    }

    public static FileConfigurationPair getModuleFileConfiguration(String moduleName) {

        File file = null;
        FileConfiguration fileConfiguration = null;
        try {
            file = createFile(moduleName + ".yml");

            if (file == null) throw new IOException();

            fileConfiguration = YamlConfiguration.loadConfiguration(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new FileConfigurationPair(file, fileConfiguration);
    }

    private static File createFile(String name) throws IOException {
        File file = new File(CorePlugin.getInstance().getDataFolder(), "modules/" + name);
        LoggingUtils.debug(file.getPath());
        if (!file.exists())
            if (!file.createNewFile())
                return null;

        return file;
    }


    static class FileConfigurationPair {
        private final File file;
        private final FileConfiguration fileConfiguration;

        public FileConfigurationPair(File file, FileConfiguration fileConfiguration) {
            this.file = file;
            this.fileConfiguration = fileConfiguration;
        }

        public File getFile() {
            return file;
        }

        public FileConfiguration getFileConfiguration() {
            return fileConfiguration;
        }

        public void saveFile() {
            try {
                fileConfiguration.options().copyDefaults(true);
                fileConfiguration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
