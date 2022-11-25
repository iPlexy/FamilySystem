package de.iplexy.family.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.iplexy.family.groups.Family;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.*;

import static de.iplexy.family.FamilySystem.getPlugin;

public class FamilySerialzier {
    public static void serialize(Family family) {
        File file = new File(getPlugin().getDataFolder() + "/families/", family.getUid() + ".json");
        try {
            if (!file.exists()) file.createNewFile();
            Writer writer = new FileWriter(file);
            Gson gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(ConfigurationSerializable.class, new ConfigurationSerializableAdapter())
                    .create();
            gson.toJson(family, writer);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void deserialize(File file){
        try {
            if (!file.exists()) file.createNewFile();
            Reader reader = new FileReader(file);
            Gson gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(ConfigurationSerializable.class, new ConfigurationSerializableAdapter())
                    .create();
            Family family = gson.fromJson(reader, Family.class);
            family.cacheFamily();
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
