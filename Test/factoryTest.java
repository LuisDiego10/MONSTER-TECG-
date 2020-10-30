import Card.Cards;
import Factory.Factory;
import Pila.StackP;

import java.io.IOException;

public class factoryTest {


    public static void main(String[] args) throws IOException {
        Cards cardDeck = Factory.Master();
        System.out.print("\n Start");
        System.out.print("\n" + cardDeck);
        System.out.print("\n todas las cartas de esbirros en el deck maestro");
        for (int i = 1; i < cardDeck.Esbirros.length; i++) {
            System.out.print("\n" + cardDeck.Esbirros[i].name);
            System.out.print("\n" + cardDeck.Esbirros[i].healt+"/"+cardDeck.Esbirros[i].getDamage());

        }
        StackP playerOneDeck= Factory.RandomDeck();
        StackP playertwoDeck= Factory.RandomDeck();
        System.out.print("\n deck jugador 1 \n");
        playerOneDeck.showElements();
        System.out.print(" deck jugador 2 \n");
        playertwoDeck.showElements();
        int igualdad=0;
        for(int i=0;i<20;i++){
            if(playerOneDeck.peek()==playertwoDeck.peek()){ igualdad++;}
        }
        System.out.printf("\n cantidad de igualdades %d",igualdad);
    }
}
