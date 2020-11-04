package Server;

import Card.Card;
import LCDE.Lcde;
import Pila.StackP;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
    public Userdata playerData= new Userdata();

    /**
     * Builder.
     * register the user data.
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

    public Userdata getPlayerData() {
        return playerData;
    }

public class Userdata{
    public int life=1000;
    public Card[] playerTable= new Card[6];
    public StackP playerDeck;
    public Lcde playerHand = new Lcde();
}
}
