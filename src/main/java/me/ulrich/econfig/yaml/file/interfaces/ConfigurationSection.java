package me.ulrich.econfig.yaml.file.interfaces;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Represents a section of a {@link Configuration}
 */
public interface ConfigurationSection {
    Set<String> getKeys(boolean deep);

    LinkedHashMap<String, Object> getValues(boolean deep);

    boolean contains(String path);

    boolean isSet(String path);

    String getCurrentPath();

    String getName();

    Configuration getRoot();

    ConfigurationSection getParent();

    Object get(String path);

    void set(String path, Object value);

    ConfigurationSection createSection(String path);

    ConfigurationSection createSection(String path, LinkedHashMap<String, Object> map);

    ConfigurationSection getConfigurationSection(String path);

    boolean isConfigurationSection(String path);
}
