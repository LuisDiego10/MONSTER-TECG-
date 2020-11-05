package Card;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
/**
 * class of Secret.
 * The class keep in the Server. This class is build only from jackson.
 * @author Isaac
 * @version 1.0
 * @since 29/10/2020
 */
@JsonTypeName("Secret")
public class Secret extends Card{
    String effect;
    public Secret(@JsonProperty("name")String secretName,@JsonProperty("cost")int cost, @JsonProperty("effect")String secretEffect){
        name= secretName;
        effect= secretEffect;
        setManaCost(cost);

    }


    public String getEfect() {
        return effect;
    }
}
