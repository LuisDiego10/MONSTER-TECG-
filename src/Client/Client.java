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
     * @param args not used
     * @see #createAccount
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
     * show the chat screen when to chat.
     * Send the written msg to server.
     * @throws IOException if msg is not under UTF.
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

    static class canvasUser extends JPanel{
        JFrame v=new JFrame();
        public static JLabel label_u=new JLabel();

        public canvasUser(){
            ImageIcon bgTable =new ImageIcon("resources/images/DECK.png");
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
     * method that the lister for the socket.
     * @param socket client socket
     * @throws IOException when name are not under UTF or socket fail
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



