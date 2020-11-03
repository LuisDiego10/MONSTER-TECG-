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
            userName=in.readUTF();
        } catch (IOException e) {
            Server.getLogger().error("Error while creating the User class in the server"+e);
        }catch (NullPointerException e) {
            Server.getLogger().error("Null, try creating a server"+e);
        }

    }
    public static class userWindow extends JFrame{
        public userWindow(){
            this.setTitle("PLAYER TABLE");
            setBounds(0,0,1030,750);
            canvasUser canvasU= new canvasUser();
            add(canvasU);
            setVisible(true);
        }
    }
    static class canvasUser extends JPanel{
        public canvasUser(){
            JFrame v=new JFrame();
            ImageIcon bgTable =new ImageIcon("resources/images/DECK.png");
            JLabel label_u=new JLabel();
            label_u.setBounds(0,0,1030,750);
            label_u.setIcon(new ImageIcon(bgTable.getImage().getScaledInstance(1000,700, Image.SCALE_SMOOTH)));
            add(label_u);
            JLabel label_m=new JLabel("100");
            label_m.setBounds(835,647,20,20);
            add(label_m);
            JLabel label_m2=new JLabel("100");
            label_m2.setBounds(835,55,20,20);
            add(label_m2);
            label_u.add(label_m);
            label_u.add(label_m2);
            JButton btn_SkipTurn=new JButton();
            ImageIcon skipTurn =new ImageIcon("resources/images/skip.png");
            btn_SkipTurn.setIcon(new ImageIcon(skipTurn.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
            btn_SkipTurn.setBounds(920,630,40,40);
            label_u.add(btn_SkipTurn);
            JButton btn_Historial=new JButton();
            ImageIcon historial =new ImageIcon("resources/images/historial.png");
            btn_Historial.setIcon(new ImageIcon(historial.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
            btn_Historial.setBounds(920,550,40,40);
            label_u.add(btn_Historial);
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
    public Lcde playerHand;

}


}
