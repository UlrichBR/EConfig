package me.ulrich.econfig.yaml;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Representer;

import me.ulrich.econfig.yaml.file.interfaces.ConfigurationSection;

public class EnumConfigurationRepresenter extends Representer {

    public EnumConfigurationRepresenter() {
        // Chame o construtor da classe Representer que aceita DumperOptions
        super(new DumperOptions());
        
        // Adicione o representer personalizado para ConfigurationSection
        this.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection());
    }

    private class RepresentConfigurationSection extends RepresentMap {
        @Override
        public Node representData(Object data) {
            // Converta ConfigurationSection para um mapa e represente seus dados
            return super.representData(((ConfigurationSection) data).getValues(false));
        }
    }
}
