package Card;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Spell extends Card{
    String efect;
    public Spell(@JsonProperty("name")String spellName,@JsonProperty("cost")int cost,@JsonProperty("effect") String spellEfect){
        name= spellName;
        efect= spellEfect;
        setManaCost(cost);
    }

    public String getEfect() {
        return efect;
    }
}
