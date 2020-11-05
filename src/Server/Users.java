package Server;

import Card.Card;
import LCDE.Lcde;
import Pila.StackP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Aux Class for server app.
 * Save users data.
 * @see Server for use.
 * @author Isaac
 * @version 1.0
 * @since 29/10/2020
 */
public class Users {
    /**
     * User data
     */
    public Socket userSocket;
    public DataOutputStream out;
    public DataInputStream in;
    public String userName;
    public boolean turn;
    public Userdata playerData= new Userdata();

    /**
     * Class builder.
     * Create the input and output stream of the user.
     * @param socket user socket.
     */
    public Users(Socket socket) {
        userSocket=socket;
        try {
            in = new DataInputStream(userSocket.getInputStream());
            out = new DataOutputStream(userSocket.getOutputStream());
        } catch (IOException e) {
            Server.getLogger().error("Error while creating the User class in the server"+e);
        }catch (NullPointerException e) {
            Server.getLogger().error("Null, try creating a server"+e);
        }



    }


    /**
     * Getter.
     * @return respective object
     */
    public DataInputStream getIn() {
        return in;
    }
    /**
     * Getter.
     * @return respective object
     */
    public DataOutputStream getOut() {
        return out;
    }
    /**
     * Getter.
     * @return respective object
     */
    public Socket getUserSocket() {
        return userSocket;
    }
    /**
     * Getter.
     * @return respective object
     */
    public String getUserName() {
        return userName;
    }
    /**
     * Getter.
     * @return respective object
     */
    public Userdata getPlayerData() {
        return playerData;
    }

    /**
     * Aux class to save the user data.
     * @see Server for use.
     * @author Isaac
     * @version 1.0
     * @since 4/11/2020
     */
public class Userdata{
    public int life=1000;
    public Card[] playerTable= new Card[4];
    public Card[] enemyTable= new Card[4];
    public StackP playerDeck;
    public Lcde playerHand = new Lcde();
}
}
