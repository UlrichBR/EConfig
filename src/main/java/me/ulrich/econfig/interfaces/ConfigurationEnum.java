package me.ulrich.econfig.interfaces;

public interface ConfigurationEnum {
    String getPath();

    Object getDefaultValue();

    String[] getComments();

}
