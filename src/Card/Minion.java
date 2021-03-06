package Card;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Class of minions.
 * The class keep in the Server. This class is build only from jackson.
 * @author Isaac
 * @version 1.0
 * @since 29/10/2020
 */
@JsonTypeName("Minion")
public class Minion extends Card{
    public Minion(@JsonProperty("name")String minionName,@JsonProperty("cost")int cost,@JsonProperty("health") int life, @JsonProperty("damage") int minionDamage){
        name= minionName;
        healt= life;
        damage= minionDamage;
        setManaCost(cost);
    }
/**
 * Methods general
 *Methods Get diffetent atributtes
 */
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

