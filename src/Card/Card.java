package Card;

import com.fasterxml.jackson.annotation.JsonProperty;


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






    public void Action(){

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

