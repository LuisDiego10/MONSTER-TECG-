package Card;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Minion extends Card{
    public int healt;
    int damage;


    public Minion(@JsonProperty("name")String minionName,@JsonProperty("vida") int life, @JsonProperty("daño") int minionDamage){
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
