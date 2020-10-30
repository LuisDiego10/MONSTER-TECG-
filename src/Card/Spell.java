package Card;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Spell extends Card{
    String efect;
    public Spell(@JsonProperty("nombre")String spellName,@JsonProperty("Coste")int cost,@JsonProperty("Efecto") String spellEfect){
        name= spellName;
        efect= spellEfect;
        setManaCost(cost);
    }

    public String getEfect() {
        return efect;
    }
}
