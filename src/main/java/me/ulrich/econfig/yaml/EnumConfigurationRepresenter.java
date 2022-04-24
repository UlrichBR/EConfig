package me.ulrich.econfig.yaml;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Representer;

import me.ulrich.econfig.yaml.file.interfaces.ConfigurationSection;

public class EnumConfigurationRepresenter extends Representer {
    public EnumConfigurationRepresenter() {
        this.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection());
    }

    private class RepresentConfigurationSection extends RepresentMap {
        @Override
        public Node representData(Object data) {
            return super.representData(((ConfigurationSection) data).getValues(false));
        }
    }
}
