package Card;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {
    @JsonProperty("nombre")
    public String name;
    private int manaCost=300;

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

