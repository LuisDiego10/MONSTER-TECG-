package Server;

import Card.Card;
import LCDE.Lcde;
import Pila.StackP;

public class Userdata{
    public int life=1000;
    public Card[] playerTable= new Card[6];
    public StackP playerDeck;
    public Lcde playerHand;
}
