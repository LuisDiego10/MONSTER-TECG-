package Card;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Secret extends Card{
    String condition;
    String efect;
    public Secret(@JsonProperty("nombre")String secretName, @JsonProperty("condicion")String secretCondition, @JsonProperty("efecto")String secretEfect){
        name= secretName;
        condition= secretCondition;
        efect= secretEfect;
    }

    public String getCondition() {
        return condition;
    }

    public String getEfect() {
        return efect;
    }
}
