package me.ulrich.econfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.ulrich.econfig.interfaces.ConfigurationEnum;
import me.ulrich.econfig.yaml.EnumConfigurationDumperOptions;
import me.ulrich.econfig.yaml.file.YAMLConfiguration;
import me.ulrich.econfig.yaml.file.interfaces.ConfigurationSection;

public class EnumConfiguration {
    private YAMLConfiguration configuration;
    private YAMLConfiguration oldConfiguration;
    private List<ConfigurationEnum> configurationEnums;
    private String pluginTag;

    EnumConfiguration(String tag, File file, List<ConfigurationEnum> configurationEnums, EnumConfigurationDumperOptions dumperOptions) {
        try {
            if (!file.getParentFile().mkdirs() && !file.getParentFile().isDirectory()) {
                throw new IOException("Parent folder was a file, not directory");
            }
            if (!file.exists() && !file.createNewFile()) {
                throw new IOException("File couldn't be created");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    
        pluginTag = tag;
        oldConfiguration = new YAMLConfiguration(file, dumperOptions);
        try {
            oldConfiguration.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.configurationEnums = configurationEnums;
        configuration = new YAMLConfiguration(file, dumperOptions);
        buildConfig();
    }

    private void buildConfig() {

        for (ConfigurationEnum configurationEnum : configurationEnums) {
            if (configurationEnum.getComments().length > 0) {
                configuration.setComments(configurationEnum.getPath(), configurationEnum.getComments());
            }
            if (oldConfiguration.get(configurationEnum.getPath()) != null) {
                configuration.set(configurationEnum.getPath(), oldConfiguration.get(configurationEnum.getPath()));
            } else {
                configuration.set(configurationEnum.getPath(), configurationEnum.getDefaultValue());
            }
        }

        try {
            configuration.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(ConfigurationEnum configurationEnum, Object value) {
        configuration.set(configurationEnum.getPath(), value);
        try {
            configuration.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 
    public YAMLConfiguration getYaml() {

        return configuration;
    }
    public Object get(ConfigurationEnum configurationEnum) {
        return configuration.get(configurationEnum.getPath());
    }

   
    
    public String getString(ConfigurationEnum configurationEnum) {
    	String value = get(configurationEnum).toString();
    	
    	value = fingArgs(value, null);
    	
        return value;
    }
    
    public String getString(ConfigurationEnum configurationEnum, List<String> args) {

    	String value = get(configurationEnum).toString();
    	
    	value = fingArgs(value, args);
    	
        return value;
    }


    public String fingArgs(String str, List<String> args) {
		
        String value = str;

        
        value = value.replace("%tag%", getPluginTag());
        
        if (args == null) {
            return value;
        } else {
            if (args.size() == 0)
                return value;

            Iterator<String> argsIterator = args.iterator();
            while(argsIterator.hasNext()) {
            	
            	String arg = argsIterator.next();
            	if(arg==null || arg.isEmpty()) {
            		continue;
            	}
            	String[] splited = arg.split(";");
            	value = value.replace(splited[0], splited[1]);
            }
            
        }

        return value;
    }
    
    public boolean isString(ConfigurationEnum configurationEnum) {
        Object val = get(configurationEnum);
        return (val instanceof String);
    }

    public int getInteger(ConfigurationEnum configurationEnum) {
        return (Integer) get(configurationEnum);
    }

    public boolean isInteger(ConfigurationEnum configurationEnum) {
        Object val = get(configurationEnum);
        return (val instanceof Integer);
    }

    public boolean getBoolean(ConfigurationEnum configurationEnum) {
        Object val = get(configurationEnum);
        return (Boolean) val;
    }

    public boolean isBoolean(ConfigurationEnum configurationEnum) {
        Object val = get(configurationEnum);
        return (val instanceof Boolean);
    }

    public double getDouble(ConfigurationEnum configurationEnum) {
        Object val = get(configurationEnum);
        return (Double) val;
    }

    public boolean isDouble(ConfigurationEnum configurationEnum) {
        Object val = get(configurationEnum);
        return (val instanceof Double);
    }

    public long getLong(ConfigurationEnum configurationEnum) {
        Object val = get(configurationEnum);
        return (Long) val;
    }

    public boolean isLong(ConfigurationEnum configurationEnum) {
        Object val = get(configurationEnum);
        return (val instanceof Long);
    }


    public ConfigurationSection getConfigurationSection(ConfigurationEnum configurationEnum) {
        Object val = get(configurationEnum);
        return (ConfigurationSection) val;
    }

    public boolean isConfigurationSection(ConfigurationEnum configurationEnum) {
        Object val = get(configurationEnum);
        return (val instanceof ConfigurationSection);
    }

    public List<?> getList(ConfigurationEnum configurationEnum) {
        Object val = get(configurationEnum);
        return (List<?>) val;
    }

    public boolean isList(ConfigurationEnum configurationEnum) {
        Object val = get(configurationEnum);
        return (val instanceof List);
    }

    public List<String> getStringList(ConfigurationEnum configurationEnum) {
        List<?> list = getList(configurationEnum);

        if (list == null) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();

        for (Object object : list) {
            if ((object instanceof String)) {
            	
            	String value = String.valueOf(object);
            	
            	value = fingArgs(value, null);
            	
                result.add(value);
            }
        }

        return result;
    }
    
    public List<String> getStringList(ConfigurationEnum configurationEnum, List<String> args) {
        List<?> list = getList(configurationEnum);

        if (list == null) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();

        for (Object object : list) {
            if ((object instanceof String)) {
            	
            	String value = String.valueOf(object);
            	
            	value = fingArgs(value, args);
            	

            
                result.add(value);
            }
        }

        return result;
    }
    

    public List<Integer> getIntegerList(ConfigurationEnum configurationEnum) {
        List<?> list = getList(configurationEnum);

        if (list == null) {
            return new ArrayList<>();
        }

        List<Integer> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Integer) {
                result.add((Integer) object);
            } else if (object instanceof String) {
                try {
                    result.add(Integer.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((int) (Character) object);
            } else if (object instanceof Number) {
                result.add(((Number) object).intValue());
            }
        }

        return result;
    }

    public List<Boolean> getBooleanList(ConfigurationEnum configurationEnum) {
        List<?> list = getList(configurationEnum);

        if (list == null) {
            return new ArrayList<>();
        }

        List<Boolean> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Boolean) {
                result.add((Boolean) object);
            } else if (object instanceof String) {
                if (Boolean.TRUE.toString().equals(object)) {
                    result.add(true);
                } else if (Boolean.FALSE.toString().equals(object)) {
                    result.add(false);
                }
            }
        }

        return result;
    }

    public List<Double> getDoubleList(ConfigurationEnum configurationEnum) {
        List<?> list = getList(configurationEnum);

        if (list == null) {
            return new ArrayList<>();
        }

        List<Double> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Double) {
                result.add((Double) object);
            } else if (object instanceof String) {
                try {
                    result.add(Double.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((double) (Character) object);
            } else if (object instanceof Number) {
                result.add(((Number) object).doubleValue());
            }
        }

        return result;
    }

    public List<Float> getFloatList(ConfigurationEnum configurationEnum) {
        List<?> list = getList(configurationEnum);

        if (list == null) {
            return new ArrayList<>();
        }

        List<Float> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Float) {
                result.add((Float) object);
            } else if (object instanceof String) {
                try {
                    result.add(Float.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((float) (Character) object);
            } else if (object instanceof Number) {
                result.add(((Number) object).floatValue());
            }
        }

        return result;
    }

    public List<Long> getLongList(ConfigurationEnum configurationEnum) {
        List<?> list = getList(configurationEnum);

        if (list == null) {
            return new ArrayList<>();
        }

        List<Long> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Long) {
                result.add((Long) object);
            } else if (object instanceof String) {
                try {
                    result.add(Long.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((long) (Character) object);
            } else if (object instanceof Number) {
                result.add(((Number) object).longValue());
            }
        }

        return result;
    }

    public List<Byte> getByteList(ConfigurationEnum configurationEnum) {
        List<?> list = getList(configurationEnum);

        if (list == null) {
            return new ArrayList<>();
        }

        List<Byte> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Byte) {
                result.add((Byte) object);
            } else if (object instanceof String) {
                try {
                    result.add(Byte.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((byte) ((Character) object).charValue());
            } else if (object instanceof Number) {
                result.add(((Number) object).byteValue());
            }
        }

        return result;
    }

    public List<Character> getCharacterList(ConfigurationEnum configurationEnum) {
        List<?> list = getList(configurationEnum);

        if (list == null) {
            return new ArrayList<>();
        }

        List<Character> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Character) {
                result.add((Character) object);
            } else if (object instanceof String) {
                String str = (String) object;

                if (str.length() == 1) {
                    result.add(str.charAt(0));
                }
            } else if (object instanceof Number) {
                result.add((char) ((Number) object).intValue());
            }
        }

        return result;
    }

    public List<Short> getShortList(ConfigurationEnum configurationEnum) {
        List<?> list = getList(configurationEnum);

        if (list == null) {
            return new ArrayList<>();
        }

        List<Short> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Short) {
                result.add((Short) object);
            } else if (object instanceof String) {
                try {
                    result.add(Short.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((short) ((Character) object).charValue());
            } else if (object instanceof Number) {
                result.add(((Number) object).shortValue());
            }
        }

        return result;
    }

    public List<Map<?, ?>> getMapList(ConfigurationEnum configurationEnum) {
        List<?> list = getList(configurationEnum);
        List<Map<?, ?>> result = new ArrayList<>();

        if (list == null) {
            return result;
        }

        for (Object object : list) {
            if (object instanceof Map) {
                result.add((Map<?, ?>) object);
            }
        }

        return result;
    }

    /**
     * Adds ConfigurationEnumerations from other sources.
     *
     * @param enumsToAdd {@link ConfigurationEnum} to add
     */
    public void addConfigurationEnumeration(ConfigurationEnum[] enumsToAdd) {
        List<ConfigurationEnum> configurationEnums = new ArrayList<>();
        for (ConfigurationEnum enumToAdd : enumsToAdd) {
            for (ConfigurationEnum configurationEnum : this.configurationEnums) {
                if (enumToAdd.equals(configurationEnum)) {
                    throw new IllegalArgumentException("You cannot add identical ConfigurationEnums!");
                } else if (enumToAdd.getPath().equalsIgnoreCase(configurationEnum.getPath())) {
                    throw new IllegalArgumentException("You cannot add two ConfigurationEnums with the same path!");
                } else {
                    configurationEnums.add(enumToAdd);
                }
            }
        }
        this.configurationEnums.addAll(configurationEnums);
        buildConfig();
    }

	public String getPluginTag() {
		return pluginTag;
	}
}
