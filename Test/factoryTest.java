import Card.Cards;
import Factory.Factory;

import java.io.IOException;

public class factoryTest {


    public static void main(String[] args) throws IOException {
        Cards cardDeck = Factory.FactoryA();
        System.out.print("\n Start");
        System.out.print("\n" + cardDeck);
        for (int i = 1; i < cardDeck.Esbirros.length; i++) {
            System.out.print("\n" + cardDeck.Esbirros[i].name);
            System.out.print("\n" + cardDeck.Esbirros[i].healt+"/"+cardDeck.Esbirros[i].getDamage());

        }
    }
}
