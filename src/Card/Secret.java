package Card;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

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
