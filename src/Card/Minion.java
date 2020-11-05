package Card;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * class of minions.
 * The class keep in the Server. This class is build only from jackson.
 * @author Isaac
 * @version 1.0
 * @since 29/10/2020
 */
@JsonTypeName("Minion")
public class Minion extends Card{

    public int healt;
    int damage;


    public Minion(@JsonProperty("name")String minionName,@JsonProperty("health") int life, @JsonProperty("damage") int minionDamage){
        name= minionName;
        healt= life;
        damage= minionDamage;
    }

    public int getHealt() {
        return healt;
    }

    public void setHealt(int healt) {
        this.healt = healt;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

}

