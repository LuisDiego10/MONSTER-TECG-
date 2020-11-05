package Card;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Mother class of the card.
 * The class sended to clients. This class is build only from jackson.
 * @author Isaac
 * @version 1.0
 * @since 29/10/2020
 */
public class Card{
    @JsonProperty("name")
    public String name;
    private int manaCost=300;
    public int healt;
    public int damage;
    @JsonProperty("efect")
    public String effect;
    public Card(){

    }

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
}

