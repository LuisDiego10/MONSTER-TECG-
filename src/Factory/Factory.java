package Factory;
import Card.Cards;
import Pila.StackP;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Class that construct decks, serialize and deserialize from Json.
 * @author Isaac
 * @version 1.0
 * @since 29/10/2020
 */
public class Factory {
    static Logger logger=LogManager.getLogger("Abstract Factory");
    static String json= "resources/data.json";
    static File cards= new File(json);
    static ObjectMapper mapp = new ObjectMapper();
    static Cards deck;
    static {
        try {
            deck = mapp.readValue(cards,Cards.class);
        } catch (IOException e) {
            logger.error("error while loading, reading or creating with jackson "+e);
        }
    }
    /**
     * Getter of master deck.
     * @author Isaac
     * @version 1.0
     * @since 29/10/2020
     */
    public static Cards Master(){
        return deck;
    }
    /**
     * Create a random deck.
     * @author Isaac
     * @version 1.0
     * @since 30/10/2020
     */
    public static StackP RandomDeck(){
        System.out.print(cards);
        Random randInt= new Random();
        StackP playerDeck= new StackP(20);
        for(int i=0;i<20;i++){
            if(randInt.nextInt(2)<1){
                    playerDeck.push(deck.minion[randInt.nextInt(deck.minion.length)]);
            }else if(randInt.nextInt(2)<2){
                playerDeck.push(deck.spell[randInt.nextInt(deck.spell.length)]);

            }else{
                playerDeck.push(deck.secrets[randInt.nextInt(deck.secrets.length)]);

            }

        }
        return playerDeck;
    }
    /**
     * Serialize a object using the factory mapper.
     * @author Gonzalo
     * @version 1.0
     * @since 2/11/2020
     */
    public static String Serializer(Object Serial) throws JsonProcessingException {
        return mapp.writeValueAsString(Serial);
    }


}
