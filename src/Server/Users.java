package Server;

import Card.Card;
import LCDE.Lcde;
import Pila.StackP;

import java.io.*;
import java.net.Socket;

/**
 * Aux Class for server app.
 * Save users data and start the Socket listening.
 */
public class Users extends Thread {
    /**
     * User data
     */
    public Socket userSocket;
    public DataOutputStream out;
    public DataInputStream in;
    public String userName;

    public static Userdata playerData;



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
        USer

    }


    /**
     * Start the lister of users socket as a new Thread.
     * keep working while Server is running unntil socket is delete.
     */
    @Override
    public void run() {
        while(true){
            try {
                String msg=in.readUTF();
                SendToServer(msg);
            } catch (Exception e) {

                }
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
     * method that comunicate to Server class, send the msg recieved form the lister.
     * send to server the user and the msg.
     * @see Server#RecieveMsg
     * @param msg msg as string.
     */

    public void SendToServer(String msg)  {
        try {
            Server.RecieveMsg(this,msg);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

public static class Userdata{
    public Card[] playerTable= new Card[6];
    public StackP playerDeck;
    public Lcde playerHand;
}
}
