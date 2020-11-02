package Card;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Secret extends Card{
    String efect;
    public Secret(@JsonProperty("name")String secretName,@JsonProperty("Coste")int cost, @JsonProperty("Efecto")String secretEfect){
        name= secretName;
        efect= secretEfect;
        setManaCost(cost);

    }


    public String getEfect() {
        return efect;
    }
}
