package me.ulrich.econfig.yaml;

import org.yaml.snakeyaml.DumperOptions;

public class EnumConfigurationDumperOptions extends DumperOptions {
    private char separatorChar;

    public EnumConfigurationDumperOptions() {
        setWidth(10000);
        setPrettyFlow(true);
        setDefaultFlowStyle(FlowStyle.BLOCK);
        setDefaultScalarStyle(ScalarStyle.PLAIN);
        setLineBreak(LineBreak.getPlatformLineBreak());
        setSeparatorChar('.');
    }

    public char getSeparatorChar() {
        return separatorChar;
    }

    public void setSeparatorChar(char separatorChar) {
        this.separatorChar = separatorChar;
    }
}
