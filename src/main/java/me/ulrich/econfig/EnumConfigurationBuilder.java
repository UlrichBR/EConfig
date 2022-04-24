
package me.ulrich.econfig;

import org.yaml.snakeyaml.DumperOptions.LineBreak;

import me.ulrich.econfig.interfaces.ConfigurationEnum;
import me.ulrich.econfig.yaml.EnumConfigurationDumperOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnumConfigurationBuilder {
    private File file;
    private List<ConfigurationEnum> configurationEnums;
    private EnumConfigurationDumperOptions dumperOptions = new EnumConfigurationDumperOptions();

    public <T extends Enum<T> & ConfigurationEnum> EnumConfigurationBuilder(File file, Class<T> configurationEnumClass) {
    	if(configurationEnumClass==null) {
    		throw new IllegalArgumentException("EnumClass is Null!");
    	}
        this.file = file;
        this.configurationEnums = new ArrayList<>(Arrays.asList(configurationEnumClass.getEnumConstants()));
    }

    EnumConfigurationBuilder(File file, List<ConfigurationEnum> configurationEnums, EnumConfigurationDumperOptions dumperOptions) {
        this.file = file;
        this.configurationEnums = configurationEnums;
        this.dumperOptions = dumperOptions;
    }


    public EnumConfigurationBuilder setWidth(int width) {
        dumperOptions.setWidth(width);
        return this;
    }


    public EnumConfigurationBuilder setIndentation(int indentation) {
        if (indentation < 2 || indentation > 9) {
            throw new IllegalArgumentException("Indentation cannot be smaller than 2 or larger than 9");
        }
        dumperOptions.setIndent(indentation);
        return this;
    }


    public EnumConfigurationBuilder setIndicatorIndentation(int indicatorIndentation) {
        dumperOptions.setIndicatorIndent(indicatorIndentation);
        return this;
    }


    public EnumConfigurationBuilder setLineBreak(LineBreak lineBreak) {
        dumperOptions.setLineBreak(lineBreak);
        return this;
    }

    public EnumConfigurationBuilder setSeparatorCharacter(char separatorCharacter) {
        dumperOptions.setSeparatorChar(separatorCharacter);
        return this;
    }


    public EnumConfigurationBuilder addConfigurationEnumeration(ConfigurationEnum... enumsToAdd) {
        for (ConfigurationEnum enumToAdd : enumsToAdd) {
            for (ConfigurationEnum configurationEnum : configurationEnums) {
                if (enumToAdd.equals(configurationEnum)) {
                    throw new IllegalArgumentException("You cannot add identical ConfigurationEnums!");
                } else if (enumToAdd.getPath().equalsIgnoreCase(configurationEnum.getPath())) {
                    throw new IllegalArgumentException("You cannot add two ConfigurationEnums with the same path!");
                } else {
                    configurationEnums.add(enumToAdd);
                }
            }
        }
        return this;
    }


    public EnumConfiguration build(String tag) {
        return new EnumConfiguration(tag, file, configurationEnums, dumperOptions);
    }
}
