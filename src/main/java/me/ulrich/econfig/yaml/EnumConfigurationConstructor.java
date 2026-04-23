package me.ulrich.econfig.yaml;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class EnumConfigurationConstructor extends SafeConstructor {

	public EnumConfigurationConstructor() {
	    super(setupOptions());
	    this.yamlConstructors.put(Tag.MAP, new ConstructCustomObject());
	}

	private static LoaderOptions setupOptions() {
	    LoaderOptions options = new LoaderOptions();
	    // Permitir apenas tipos primitivos e estruturas básicas do YAML
	    options.setTagInspector(tag -> 
	        tag.equals(Tag.MAP) || 
	        tag.equals(Tag.STR) || 
	        tag.equals(Tag.INT) || 
	        tag.equals(Tag.FLOAT) || // Para valores como 10.5
	        tag.equals(Tag.BOOL) ||  // Para true/false
	        tag.equals(Tag.SEQ)      // Para listas []
	    ); 
	    return options;
	}

    private class ConstructCustomObject extends ConstructYamlMap {
        @Override
        public Object construct(Node node) {
            if (node.isTwoStepsConstruction()) {
                throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
            }
            return super.construct(node);
        }

        @Override
        public void construct2ndStep(Node node, Object object) {
            throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
        }
    }
}
