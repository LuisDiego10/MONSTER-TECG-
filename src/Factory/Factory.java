package Factory;
import LCDE.Lcde;
import com.fasterxml.jackson.databind.ObjectMapper;
import Card.*;

import java.io.File;
import java.io.IOException;


public class Factory {
    static String json= "resources/data.json";
    static File cards= new File(json);
    Lcde list= new Lcde();
    static ObjectMapper mapp = new ObjectMapper();
    Cards deck;

    {
        try {
            deck = mapp.readValue(cards,Cards.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
