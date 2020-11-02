package Factory;
import LCDE.Lcde;
import Pila.StackP;
import com.fasterxml.jackson.databind.ObjectMapper;
import Card.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.File;
import java.io.IOException;
import java.util.Random;


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

    public static Cards Master(){
        return deck;
    }
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



}
