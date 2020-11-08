package Card;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
/**
 * class of Spell.
 * This class keep in the Server. This class is build only from jackson. Is same that Secret class.
 * @author Isaac
 * @version 1.0
 * @since 29/10/2020
 */
@JsonTypeName("Spell")
public class Spell extends Card{
    public Spell(@JsonProperty("name")String spellName,@JsonProperty("cost")int cost,@JsonProperty("effect") String spellEffect){
        name= spellName;
        effect= spellEffect;
        setManaCost(cost);
    }

    public String getEfect() {
        return effect;
    }
}
