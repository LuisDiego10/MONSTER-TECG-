package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client main Class.
 * @see SocketListen for aux class.
 */
public class Client {
    /**
     * semi globals variables
     */
    private static DataOutputStream out;
    private static DataInputStream in;
    private static String name;
    public static DefaultListModel<String> log= new DefaultListModel<>();

    /**
     * Display a start menu to enter name and port.
     * The button try to connecto to server and sen the user name.
     * @param args not used
     * @see #createAccount
     */
    public static void main(String[] args) {
        ImageIcon logo =new ImageIcon("C:\\Users\\guiso\\Downloads\\monstertecg!.png");
        JLabel etiqueta_ce=new JLabel();
        etiqueta_ce.setBounds(150,30,75,70);
        etiqueta_ce.setIcon(new ImageIcon(logo.getImage().getScaledInstance(80,70, Image.SCALE_SMOOTH)));
        ImageIcon usericon =new ImageIcon("C:\\Users\\guiso\\Downloads\\iconuser.png");
        JLabel etiqueta_u=new JLabel();
        etiqueta_u.setBounds(10,105,20,20);
        etiqueta_u.setIcon(new ImageIcon(usericon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH)));
        ImageIcon iconport =new ImageIcon("C:\\Users\\guiso\\Downloads\\iconport.png");
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
        JButton introButton=new JButton("CONNECT SERVER");
        introButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String User=intro3.getText();
                String serverport=intro5.getText();
                int port= Integer.parseInt(serverport);
                try{
                    Socket clientSocket = new Socket(InetAddress.getLocalHost(),port);
                    createAccount(clientSocket,intro3.getText());
                    chatScreen.setVisible(false);
                    chatScreen.dispose();
                    Client.display();
                } catch (Exception e) {
                    intro1.setBackground(Color.white);
                    intro1.setText("Error,try again");
                    intro1.setBounds(200,100,250,30);
                }

            }
        });
        introButton.setBounds(115,220,150,50);
        chatScreen.add(etiqueta_u);
        chatScreen.add(etiqueta_p);
        chatScreen.add(etiqueta_ce,BorderLayout.CENTER);
        chatScreen.add(intro1);
        chatScreen.add(intro2);
        chatScreen.add(intro3);
        chatScreen.add(intro4);
        chatScreen.add(intro5);
        chatScreen.add(introButton);
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
    public static void display() throws IOException {
        JFrame chatScreen=new JFrame("Client");
        JList<String> chatlog=new JList<>(log);
        JTextField inputBox= new JTextField();
        JButton button=new JButton("Send");
        chatlog.setBounds(10,10, 380,300);
        inputBox.setBounds(10,310, 300,70);
        button.setBounds(310,310, 100,70);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                try {
                    String msg=inputBox.getText();
                    out.writeUTF(name+": "+"\n"+msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        chatScreen.setSize(400,400);
        chatScreen.add(chatlog);
        chatScreen.add(inputBox);
        chatScreen.add(button);
        chatScreen.setLayout(null);
        chatScreen.setVisible(true);
        out.writeUTF(name);
        chatScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    /**
     * method that the lister for the socket.
     * @param socket client socket
     * @param user user name
     * @throws IOException when name are not under UTF or socket fail
     */
    public static void createAccount(Socket socket, String user) throws IOException {
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        DataInputStream input = new DataInputStream(socket.getInputStream());
        out=output;
        in=input;
        name=user;
        out.writeUTF(name);
        SocketListen lister= new SocketListen();
        lister.start();
    }

    /**
     * Getter.
     * @return respective object.
     */
    public static DataInputStream getIn() {
        return in;
    }
}

/**
 * Aux class for client.
 * listen the socket for msg.
 * @see Client
 */

class SocketListen extends Thread{
    /**
     * Start the lister of client socket as a new Thread.
     * recieve all the msg sent from server and send it to client.
     */
    public void run(){
        DataInputStream input=Client.getIn();
        while(true){
            try {
                String msg;
                msg=input.readUTF();
                Client.log.addElement(msg);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}