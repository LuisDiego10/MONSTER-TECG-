package Client;

import Card.Card;
import LCDE.Node;
import Server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Principal Class Client
 * Containts methods to connect client to server
 * @author Isaac
 * @since 2/11/2020
 */
public class Client {
    /**
     * Semi globals variables
     */
    private static DataOutputStream out;
    private static DataInputStream in;
    private static String name;
    private static Logger logger = null;
    private static Userdata userData;
    private static Server server;
    public static userWindow window_u=null;



    /**
     * Display a start menu to enter name and port.
     * The button try to connect to server and send the user name.
     * @author Diego and Isaac
     * @param args not used
     * @see #createAccount
     * @version 2.0
     */

    public static void main(String[] args) {
        ImageIcon logo =new ImageIcon("resources/images/monstertecg!.png");
        JLabel etiqueta_ce=new JLabel();
        etiqueta_ce.setBounds(150,30,75,70);
        etiqueta_ce.setIcon(new ImageIcon(logo.getImage().getScaledInstance(80,70, Image.SCALE_SMOOTH)));
        ImageIcon usericon =new ImageIcon("resources/images/iconuser.png");
        JLabel etiqueta_u=new JLabel();
        etiqueta_u.setBounds(10,105,20,20);
        etiqueta_u.setIcon(new ImageIcon(usericon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH)));
        ImageIcon iconport =new ImageIcon("resources/images/iconport.png");
        JLabel etiqueta_p=new JLabel();
        etiqueta_p.setBounds(10,165,20,20);
        etiqueta_p.setIcon(new ImageIcon(iconport.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH)));
        JFrame chatScreen=new JFrame("Client");
        JLabel intro1=new JLabel("Welcome to the MONSTERTEC! Login");
        intro1.setBounds(75,5,250,30);
        JLabel intro2=new JLabel("Please enter your username");
        intro2.setBounds(33,100,200,30);
        JTextField intro3=new JTextField();
        intro3.setBounds(195,110,150,23);
        JLabel intro4=new JLabel("Please enter the server port");
        intro4.setBounds(33,160,200,30);
        JTextField intro5=new JTextField();
        intro5.setBounds(195,164,150,23);
        JButton connectButton=new JButton("CONNECT SERVER");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logger = LogManager.getLogger("PLAYER");
                System.out.print(logger.getLevel());
                logger.info("Client logger");
                String serverport=intro5.getText();
                int port=996;;
                if(!intro3.getText().equals("")) {
                    try {
                        if (!serverport.equals("")) {
                            port = Integer.parseInt(serverport);
                        }
                        window_u = new Client.userWindow();
                        logger.debug("Starting player GUI");
                        Socket clientSocket = new Socket(InetAddress.getLocalHost(), port);
                        createAccount(clientSocket);
                        logger.debug("Starting player socket");
                        chatScreen.setVisible(false);
                        chatScreen.dispose();

                    } catch (UnknownHostException e) {
                        logger.error("not supported port:" + serverport);
                        intro1.setBackground(Color.white);
                        intro1.setText("Error,try again");
                        intro1.setBounds(200, 100, 250, 30);
                    } catch (IOException e) {
                        intro1.setBackground(Color.white);
                        intro1.setText("Error,try again");
                        intro1.setBounds(200, 100, 250, 30);
                        logger.error("Unknown error");
                        logger.error("error info:\n" + e.getMessage());

                    }
                }
            }
        });
        connectButton.setBounds(25,220,150,50);
        JButton hostButton=new JButton("HOST SERVER");
        hostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logger = LogManager.getLogger("Host");
                String serverport=intro5.getText();
                int port=996;
                try{
                    if (!serverport.equals("")){port= Integer.parseInt(serverport);}
                    chatScreen.setVisible(false);
                    chatScreen.dispose();
                    server= new Server(port);
                    server.start();
                    logger.debug("Starting server");
                    window_u = new Client.userWindow();
                    logger.debug("Starting player GUI");
                    Socket clientSocket = new Socket(InetAddress.getLocalHost(),port);
                    createAccount(clientSocket);
                    logger.debug("Starting player socket");


                } catch (UnknownHostException e) {
                    logger.error("not supported port:"+serverport);
                    intro1.setBackground(Color.white);
                    intro1.setText("Error,try again");
                    intro1.setBounds(200,100,250,30);
                } catch (IOException e) {
                    intro1.setBackground(Color.white);
                    intro1.setText("Error,try again");
                    intro1.setBounds(200,100,250,30);
                    logger.error("Unknown error or no port/ name");
                    logger.error("error info:\n"+ e.getMessage());

                }

            }
        });
        hostButton.setBounds(225,220,150,50);
        chatScreen.add(etiqueta_u);
        chatScreen.add(etiqueta_p);
        chatScreen.add(etiqueta_ce,BorderLayout.CENTER);
        chatScreen.add(intro1);
        chatScreen.add(intro2);
        chatScreen.add(intro3);
        chatScreen.add(intro4);
        chatScreen.add(intro5);
        chatScreen.add(connectButton);
        chatScreen.add(hostButton);
        chatScreen.setSize(390,380);
        chatScreen.setLayout(null);
        chatScreen.setVisible(true);
        chatScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    /**
     * userWindow class
     * Show the game screen
     * @throws IOException if msg is not under UTF.
     * @author Diego
     * @version 2.0
     * @since 2/11/2020
     */

    public static class userWindow extends JFrame{
        public canvasUser canvasU;
        public userWindow(){
            this.setTitle("PLAYER TABLE");
            setBounds(0,0,1030,750);
            canvasU= new canvasUser();
            add(canvasU);
            setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
    /**
     * canvasUser class
     * Shows the elements of the game window
     * @author Diego and Isaac
     * @version 2.1
     * @since 2/11/2020
     */
    static class canvasUser extends JPanel {
        JFrame v = new JFrame();
        public static JLabel label_u = new JLabel();
        public JButton card_1;
        public JButton card_2;
        public JButton card_3;
        public JButton card_4;
        public JButton card_5;
        public JButton card_6;
        public JButton card_7;
        public JButton card_8;
        public JButton card_9;
        public JButton card_10;
        public JButton card_11;
        public JButton card_12;
        public JButton card_13;
        public JButton card_14;
        public JButton card_15;
        public JButton card_16;
        public JButton card_17;
        public JButton card_18;

        public canvasUser(){
            ImageIcon bgTable =new ImageIcon("resources/images/DECK 2.0.png");
            label_u.setBounds(0,0,1030,750);
            label_u.setIcon(new ImageIcon(bgTable.getImage().getScaledInstance(1000,700, Image.SCALE_SMOOTH)));
            add(label_u);
            JLabel label_m=new JLabel("200");
            label_m.setBounds(626,570,40,40);
            label_m.setForeground(Color.white);
            label_m.setFont(new Font ("Arial", Font.PLAIN,16));
            add(label_m);
            JLabel label_m2=new JLabel("200");
            label_m2.setBounds(626,115,40,40);
            label_m2.setFont(new Font ("Arial", Font.PLAIN,16));
            label_m2.setForeground(Color.white);
            add(label_m2);
            label_u.add(label_m);
            label_u.add(label_m2);
            JButton btn_SkipTurn=new JButton();
            ImageIcon skipTurn =new ImageIcon("resources/images/skip.png");
            btn_SkipTurn.setIcon(new ImageIcon(skipTurn.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
            btn_SkipTurn.setBounds(920,630,40,40);
            label_u.add(btn_SkipTurn);
            btn_SkipTurn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        out.writeUTF("finish turn");
                    } catch (IOException ex) {
                        logger.error("error finishing turn");
                    }
                }
            });
            JButton btn_Historial=new JButton();
            ImageIcon historial =new ImageIcon("resources/images/historial.png");
            btn_Historial.setIcon(new ImageIcon(historial.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
            btn_Historial.setBounds(920,550,40,40);
            label_u.add(btn_Historial);
            //CREATE ALL BUTTONS
            JButton deck_btn=new JButton("Deck");
            deck_btn.setBounds(25,250,105,185);
            label_u.add(deck_btn);
            deck_btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!userData.playerDeck.empty()){
                        try {
                            out.writeUTF("peek");
                        } catch (IOException ex) {
                            logger.error("error sending msg to server with action:"+e+"\n;"+ex);
                        }
                    }
                }
            });
            card_1=new JButton("<html>card<html>");
            card_1.setBounds(128,540,80,140);
            label_u.add(card_1);
            card_1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                        try {
                            if (userData.enemyTable[0]==null){
                                out.writeUTF("");
                            }else {
                                out.writeUTF("attack" + userData.playerTable[0].name);
                            }
                        } catch (IOException ex) {
                            logger.error("error sending attack oder to server with action:"+e+"\n;"+ex);
                        }

                }
            });
            card_2=new JButton("<html>card<html>");
            card_2.setBounds(215,540,80,140);
            label_u.add(card_2);
            card_2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                        try {
                            if (userData.enemyTable[1]==null){
                                out.writeUTF("");
                            }else {
                                out.writeUTF("attack" + userData.playerTable[1].name);
                            }
                        } catch (IOException ex) {
                            logger.error("error sending attack oder to server with action:"+e+"\n;"+ex);
                        }

                }
            });
            card_3=new JButton("<html>card<html>");
            card_3.setBounds(302,540,80,140);
            label_u.add(card_3);
            card_3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                        try {
                            if (userData.enemyTable[2]==null){
                                out.writeUTF("");
                            }else {
                                out.writeUTF("attack" + userData.playerTable[2].name);
                            }
                        } catch (IOException ex) {
                            logger.error("error sending attack oder to server with action:"+e+"\n;"+ex);
                        }

                }
            });
            card_4=new JButton("<html>card<html>");
            card_4.setBounds(397,540,80,140);
            label_u.add(card_4);
            card_4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                        try {
                            if (userData.enemyTable[2]==null){
                                out.writeUTF("");
                            }else {
                                out.writeUTF("attack" + userData.playerTable[2].name);
                            }
                        } catch (IOException ex) {
                            logger.error("error sending attack oder to server with action:"+e+"\n;"+ex);
                        }

                }
            });
            card_5=new JButton("<html>Hand<html>");
            card_5.setBounds(710,30,80,140);
            label_u.add(card_5);
            card_5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        out.writeUTF("invoke"+Client.getUserData().playerHand.getStart().fact.name);

                    } catch (IOException ex) {
                        logger.error("error while client invoke card"+ex);
                    }
                }
            });
            card_6=new JButton("<html>Hand<html>");
            card_6.setBounds(805,30,80,140);
            label_u.add(card_6);
            card_6.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        out.writeUTF("invoke"+Client.getUserData().playerHand.getStart().nextNode.fact.name);

                    } catch (IOException ex) {
                        logger.error("error while client invoke card"+ex);
                    }
                }
            });
            card_7=new JButton("<html>Hand<html>");
            card_7.setBounds(900,30,80,140);
            label_u.add(card_7);
            card_7.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        out.writeUTF("invoke"+Client.getUserData().playerHand.getStart().nextNode.nextNode.fact.name);

                    } catch (IOException ex) {
                        logger.error("error while client invoke card"+ex);
                    }
                }
            });
            card_8=new JButton("<html>Hand<html>");
            card_8.setBounds(710,190,80,140);
            label_u.add(card_8);
            card_8.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        out.writeUTF("invoke"+Client.getUserData().playerHand.getStart().nextNode
                                .nextNode.nextNode.fact.name);

                    } catch (IOException ex) {
                        logger.error("error while client invoke card"+ex);
                    }
                }
            });
            card_9=new JButton("<html>Hand<html>");
            card_9.setBounds(805,190,80,140);
            label_u.add(card_9);
            card_9.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        out.writeUTF("invoke"+Client.getUserData().playerHand.getStart().nextNode.nextNode
                                .nextNode.nextNode.fact.name);

                    } catch (IOException ex) {
                        logger.error("error while client invoke card"+ex);
                    }
                }
            });
            card_10=new JButton("<html>Hand<html>");
            card_10.setBounds(900,190,80,140);
            label_u.add(card_10);
            card_10.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        out.writeUTF("invoke"+Client.getUserData().playerHand.getStart().nextNode.nextNode.nextNode
                                .nextNode.nextNode.fact.name);

                    } catch (IOException ex) {
                        logger.error("error while client invoke card"+ex);
                    }
                }
            });
            card_11=new JButton("<html>Hand<html>");
            card_11.setBounds(710,343,80,140);
            label_u.add(card_11);
            card_11.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        out.writeUTF("invoke"+Client.getUserData().playerHand.getStart().nextNode.nextNode.nextNode
                                .nextNode.nextNode.nextNode.fact.name);

                    } catch (IOException ex) {
                        logger.error("error while client invoke card"+ex);
                    }
                }
            });
            card_12=new JButton("<html>Hand<html>");
            card_12.setBounds(805,343,80,140);
            label_u.add(card_12);
            card_12.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        out.writeUTF("invoke"+Client.getUserData().playerHand.getStart().nextNode.nextNode.nextNode
                                .nextNode.nextNode.nextNode.nextNode.fact.name);

                    } catch (IOException ex) {
                        logger.error("error while client invoke card"+ex);
                    }
                }
            });
            card_13=new JButton("<html>Hand<html>");
            card_13.setBounds(900,343,80,140);
            label_u.add(card_13);
            card_13.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        out.writeUTF("invoke"+Client.getUserData().playerHand.getStart().nextNode.nextNode.nextNode
                                .nextNode.nextNode.nextNode.nextNode.nextNode.fact.name);

                    } catch (IOException ex) {
                        logger.error("error while client invoke card"+ex);
                    }
                }
            });
            card_14=new JButton("<html>Hand<html>");
            card_14.setBounds(805,500,80,140);
            label_u.add(card_14);
            card_14.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        out.writeUTF("invoke"+Client.getUserData().playerHand.getStart().nextNode.nextNode.nextNode
                                .nextNode.nextNode.nextNode.nextNode.nextNode.nextNode.fact.name);

                    } catch (IOException ex) {
                        logger.error("error while client invoke card"+ex);
                    }
                }
            });
            card_15=new JButton("<html>Card<html>");
            card_15.setBounds(128,15,80,140);
            label_u.add(card_15);
            card_15.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                        try {
                            if (userData.enemyTable[0]==null){
                                out.writeUTF("");
                            }else {
                                out.writeUTF(userData.enemyTable[0].name);
                            }
                        } catch (IOException ex) {
                            logger.error("error sending attack objective to server with action:"+e+"\n;"+ex);
                        }

                }
            });
            card_16=new JButton("<html>Card<html>");
            card_16.setBounds(215,15,80,140);
            label_u.add(card_16);
            card_16.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                        try {
                            if (userData.enemyTable[1]==null){
                                out.writeUTF("");
                            }else {
                                out.writeUTF(userData.enemyTable[1].name);
                            }
                        } catch (IOException ex) {
                            logger.error("error sending attack objective to server with action:"+e+"\n;"+ex);
                        }

                }
            });
            card_17=new JButton("<html>Card<html>");
            card_17.setBounds(302,15,80,140);
            label_u.add(card_17);
            card_17.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                        try {
                            if (userData.enemyTable[2]==null){
                                out.writeUTF("");
                            }else {
                                out.writeUTF(userData.enemyTable[2].name);
                            }
                        } catch (IOException ex) {
                            logger.error("error sending attack objective to server with action:"+e+"\n;"+ex);
                        }

                }
            });
            card_18=new JButton("<html>Card<html>");
            card_18.setBounds(397,15,80,140);
            label_u.add(card_18);
            card_18.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                        try {
                            if (userData.enemyTable[3]==null){
                                out.writeUTF("");
                            }else {
                                out.writeUTF(userData.enemyTable[3].name);
                            }
                        } catch (IOException ex) {
                            logger.error("error sending attack objective to server with action:"+e+"\n;"+ex);
                        }

                }
            });

        }
        public void updateCardDisplay(){
            Card card;
            JButton[] buttons= new JButton[]{card_5, card_6, card_7, card_8, card_9, card_10, card_11, card_12, card_13, card_14};
            int a=0;
            Userdata data=Client.getUserData();
            while(a<data.playerHand.sizeLCDE) {
                Node node = data.playerHand.getStart();
                for(int i=0;i<a;i++){
                    node = node.nextNode;}
                card=node.fact;
                buttons[a].setText(getCardText(card));
                a++;
            }
            card=data.playerTable[0];
            if(card != null){
            card_1.setText(getCardText(card));}
            card=data.playerTable[1];
            if(card != null){
            card_2.setText(getCardText(card));}
            card=data.playerTable[2];
            if(card != null){
            card_3.setText(getCardText(card));}
            card=data.playerTable[3];
            if(card != null){
            card_4.setText(getCardText(card));}
            card=data.enemyTable[0];
            if(card != null){
            card_15.setText(getCardText(card));}
            card=data.enemyTable[1];
            if(card != null){
            card_16.setText(getCardText(card));}
            card=data.enemyTable[2];
            if(card != null){
            card_17.setText(getCardText(card));}
            card=data.enemyTable[3];
            if(card != null){
            card_18.setText(getCardText(card));}


        }
        public String getCardText(Card card){
            StringBuilder text= new StringBuilder();
            text.append("<html>").append(card.name).append("<br>");
            if (card.healt!=-1){
                text.append("healt: ").append(card.healt).append("<br>");
            }
            if (card.damage!=-1){
                text.append("damage: ").append(card.damage).append("<br>");
            }
            if (!card.effect.equals("")){
                text.append(card.effect).append("<br>");
            }
            text.append("cost: ").append(card.getManaCost()).append("<br>");
            text.append("<html>");
            return text.toString();
        }
    }

    /**
     * method that the lister for the socket.
     * @param socket client socket
     * @throws IOException when name are not under UTF or socket fail
     * @author Isaac
     * @version 2.0
     * @since 02/11/2020
     */
    public static void createAccount(Socket socket) throws IOException {
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        DataInputStream input = new DataInputStream(socket.getInputStream());
        out=output;
        in=input;
        SocketListen lister= new SocketListen();
        lister.start();
    }


    public static void updatePlayerData(Userdata data){
        userData=data;
        if (window_u!=null){
        window_u.canvasU.updateCardDisplay();}
    }

    public static Userdata getUserData() {
        return userData;
    }

    /**
     * Getter.
     * @return respective object.
     */
    public static DataInputStream getIn() {
        return in;
    }

    /**
     * Getter
     * @return respective String
     */
    public static String getName(){
        return name;
    }

    /**
     * Getter.
     * @return respective object.
     */
    public static Logger getLogger() {
        return logger;
    }

    public static void startTurn(){
        try {
            out.writeUTF("turn");
        } catch (IOException e) {
            logger.error("error indicating the start of the turn to server");
        }
    }
}

/**
 * Aux class for client.
 * listen the socket for msg.
 * @see Client
 * @author Isaac
 * @version 2.0
 * @since 02/11/2020
 */

class SocketListen extends Thread{
    /**
     * Socket logger
     */
    private static Logger logger;
    static ObjectMapper mapp = new ObjectMapper();


    /**
     * Start the lister of client socket as a new Thread.
     * recieve all the msg sent from server and send it to client.
     */
    public void run(){
        logger= Client.getLogger() ;
        DataInputStream input=Client.getIn();
        while(true){
            try {
                String msg;
                msg=input.readUTF();
                logger.debug("socket receive"+msg);
                if (msg.equals("turn")){
                    Client.startTurn();
                }else{
                    Client.updatePlayerData(mapp.readValue(msg,Userdata.class));
                }
            } catch (SocketException e) {
                logger.error("socket error , exception: \n"+ e);
                logger.debug("socket close");
                break;
            } catch (IOException e) {
                logger.error("input error from socket, exception: \n"+ e);
                logger.debug("socket close");
                break;
            }
        }
    }
}



