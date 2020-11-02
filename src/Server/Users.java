package Server;

import Card.Card;
import LCDE.Lcde;
import Pila.StackP;

import java.io.*;
import java.net.Socket;

/**
 * Aux Class for server app.
 * Save users data.
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

    public Userdata playerData;



    /**
     * Builder.
     * register the user data.
     * @param socket user socket.
     */
    public Users(Socket socket) {
        userSocket=socket;
        userName=in.readUTF();
        DataOutputStream output = new DataOutputStream(userSocket.getOutputStream());
        DataInputStream input = new DataInputStream(userSocket.getInputStream());


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

    public Userdata getPlayerData() {
        return playerData;
    }

    /**
     * method that comunicate to Server class, send the msg recieved form the lister.
     * send to server the user and the msg.
     * @see Server#
     * @param msg msg as string.
     */

    public void SendToServer(String msg)  {
        try {
            Server.(this,msg);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

public class Userdata{
    public int life=1000;
    public Card[] playerTable= new Card[6];
    public StackP playerDeck;
    public Lcde playerHand;
}
}
