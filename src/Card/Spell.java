package Card;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Spell")
public class Spell extends Card{
    String effect;
    public Spell(@JsonProperty("name")String spellName,@JsonProperty("cost")int cost,@JsonProperty("effect") String spellEffect){
        name= spellName;
        effect= spellEffect;
        setManaCost(cost);
    }

    public String getEfect() {
        return effect;
    }
}
