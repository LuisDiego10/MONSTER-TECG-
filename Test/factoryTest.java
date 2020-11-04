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
        for (int i = 1; i < cardDeck.minion.length; i++) {
            System.out.print("\n" + cardDeck.minion[i].name);
            System.out.print("\n" + cardDeck.minion[i].healt+"/"+cardDeck.minion[i].getDamage());

        }        System.out.print("\n todas las cartas de spell en el deck maestro");
        for (int i = 1; i < cardDeck.spell.length; i++) {
            System.out.print("\n" + cardDeck.spell[i].name);
            System.out.print("\n" + cardDeck.spell[i].getEfect());

        }        System.out.print("\n todas las cartas de secreto en el deck maestro");
        for (int i = 1; i < cardDeck.secrets.length; i++) {
            System.out.print("\n" + cardDeck.secrets[i].name);
            System.out.print("\n" + cardDeck.secrets[i].getEfect());

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
