package Factory;
import LCDE.Lcde;
import Pila.StackP;
import com.fasterxml.jackson.databind.ObjectMapper;
import Card.*;

import java.io.File;
import java.io.IOException;
import java.util.Random;


public class Factory {
    static String json= "resources/data.json";
    static File cards= new File(json);
    static ObjectMapper mapp = new ObjectMapper();
    static Cards deck;
    static {
        try {
            deck = mapp.readValue(cards,Cards.class);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static Cards Master(){
        return deck;
    }
    public static StackP RandomDeck(){
        Random randInt= new Random();
        StackP playerDeck= new StackP(20);
        for(int i=0;i<20;i++){
            if(randInt.nextInt(2)<1){
                    playerDeck.push(deck.Esbirros[randInt.nextInt(deck.Esbirros.length)]);
            }else if(randInt.nextInt(2)<2){
                playerDeck.push(deck.Hechizos[randInt.nextInt(deck.Hechizos.length)]);

            }else{
                playerDeck.push(deck.Secretos[randInt.nextInt(deck.Secretos.length)]);

            }

        }
        return playerDeck;
    }
}
