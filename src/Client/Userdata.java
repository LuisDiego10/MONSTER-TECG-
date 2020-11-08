package Client;

import Card.Card;
import LCDE.Lcde;
import LDE.Lde;
import Pila.StackP;
import Server.Server;

/**
 * Client´s copy of the aux class to storage the user data.
 * Is overwritten each turn or action.
 * @see Server for use.
 * @author Isaac
 * @version 1.0
 * @since 2/11/2020
 */
public class Userdata{
    public int life=1000;
    public int enemyLife=1000;
    public int mana=1000;
    public Lde historial=new Lde();
    public Card[] playerTable= new Card[4];
    public Card[] enemyTable= new Card[4];
    public StackP playerDeck;
    public Lcde playerHand;
}
