package Client;

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

import static java.awt.Font.PLAIN;

public class Client {
    /**
     * semi globals variables
     */
    private static DataOutputStream out;
    private static DataInputStream in;
    private static String name;
    private static Logger logger = null;
    private static Userdata userData;
    private static Server server;


    /**
     * Display a start menu to enter name and port.
     * The button try to connect to server and sen the user name.
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
                logger.debug("Client logger");
                logger.info("Client logger");
                logger.info("Client logger");
                String serverport=intro5.getText();
                int port=996;;
                if(!intro3.getText().equals("")) {
                    try {
                        if (!serverport.equals("")) {
                            port = Integer.parseInt(serverport);
                        }
                        Socket clientSocket = new Socket(InetAddress.getLocalHost(), port);

                        createAccount(clientSocket);
                        chatScreen.setVisible(false);
                        chatScreen.dispose();
                        userWindow window_u = new Client.userWindow();

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
                }else{}
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
                    Socket clientSocket = new Socket(InetAddress.getLocalHost(),port);
                    createAccount(clientSocket);
                    userWindow window_u = new Client.userWindow();

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
        public userWindow(){
            this.setTitle("PLAYER TABLE");
            setBounds(0,0,1030,750);
            canvasUser canvasU= new canvasUser();
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
    static class canvasUser extends JPanel{
        JFrame v=new JFrame();
        public static JLabel label_u=new JLabel();

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
            JButton btn_Historial=new JButton();
            ImageIcon historial =new ImageIcon("resources/images/historial.png");
            btn_Historial.setIcon(new ImageIcon(historial.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
            btn_Historial.setBounds(920,550,40,40);
            label_u.add(btn_Historial);
            //CREATE ALL BUTTONS
            JButton deck_btn=new JButton("Information");
            deck_btn.setBounds(25,250,105,185);
            label_u.add(deck_btn);
            JButton card_1=new JButton("<html>Information2<br>jdsfklsdfklsjdfkljsdlfjdllsadmf<html>");
            card_1.setBounds(128,540,80,140);
            label_u.add(card_1);
            JButton card_2=new JButton("<html>Information2<br>Hola<html>");
            card_2.setBounds(215,540,80,140);
            label_u.add(card_2);
            JButton card_3=new JButton("<html>Information2<br>Hola<html>");
            card_3.setBounds(302,540,80,140);
            label_u.add(card_3);
            JButton card_4=new JButton("<html>Information2<br>Hola<html>");
            card_4.setBounds(397,540,80,140);
            label_u.add(card_4);
            JButton card_5=new JButton("<html>Information2<br>Hola<html>");
            card_5.setBounds(710,30,80,140);
            label_u.add(card_5);
            JButton card_6=new JButton("<html>Information2<br>Hola<html>");
            card_6.setBounds(805,30,80,140);
            label_u.add(card_6);
            JButton card_7=new JButton("<html>Information2<br>Hola<html>");
            card_7.setBounds(900,30,80,140);
            label_u.add(card_7);
            JButton card_8=new JButton("<html>Information2<br>Hola<html>");
            card_8.setBounds(710,190,80,140);
            label_u.add(card_8);
            JButton card_9=new JButton("<html>Information2<br>Hola<html>");
            card_9.setBounds(805,190,80,140);
            label_u.add(card_9);
            JButton card_10=new JButton("<html>Information2<br>Hola<html>");
            card_10.setBounds(900,190,80,140);
            label_u.add(card_10);
            JButton card_11=new JButton("<html>Information2<br>Hola<html>");
            card_11.setBounds(710,343,80,140);
            label_u.add(card_11);
            JButton card_12=new JButton("<html>Information2<br>Hola<html>");
            card_12.setBounds(805,343,80,140);
            label_u.add(card_12);
            JButton card_13=new JButton("<html>Information2<br>Hola<html>");
            card_13.setBounds(900,343,80,140);
            label_u.add(card_13);
            JButton card_14=new JButton("<html>Information2<br>Hola<html>");
            card_14.setBounds(805,500,80,140);
            label_u.add(card_14);

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
//        canvasUser.label_u;
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
                logger.error("socket receive"+msg);
                Client.updatePlayerData(mapp.readValue(msg,Userdata.class));

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



