package Server;

import Factory.Factory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.*;
import java.io.*;



/**
 * Client.Main Class of Server app.
 * @see Users for aux class
 */
public class Server extends Thread {
    /**
     * semi-global variables
     */
    public ServerSocket publicSocket;//* socket
    private static Logger logger=LogManager.getLogger();
    private Users playerHost;
    private Users playerInvitated;
    public Factory abstractFactory =new Factory();


    /**
     * Create the server´s socket whit the indicated port
     * @param port port of the
     */
    public Server(int port){
        try {
            publicSocket= new ServerSocket(port,50,InetAddress.getLocalHost());
        } catch (IOException e) {
            logger.error("The port entered could not be accessed");

        }
    }


    /**
     * Start the server and wait for action.
     */
    public void run() {
        playerHost=addUser();
        playerInvitated=addUser();
        playerHost.playerData.playerDeck= Factory.RandomDeck();
        playerInvitated.playerData.playerDeck= Factory.RandomDeck();
        for (int i=0;i<5;i++){
            playerInvitated.playerData.playerHand.insert(playerInvitated.playerData.playerDeck.peek());
            playerHost.playerData.playerHand.insert(playerHost.playerData.playerDeck.peek());
        }
        // llamada de serializacion
        //
        while(playerHost.playerData.life>0 | playerInvitated.playerData.life>0){
            while (playerInvitated.turn){
                String action = "";
                try {
                    action=playerInvitated.in.readUTF();
                } catch (IOException e) {
                    logger.error("error getting action trying again");

                }
                if(action.equals("finish turn")){playerInvitated.turn=false;playerHost.turn=true; }
            }
            while (playerHost.turn){
                String action = "";
                try {
                    action=playerInvitated.in.readUTF();
                } catch (IOException e) {
                   logger.error("error getting action trying again");

                }
                if(action.equals("finish turn")){playerHost.turn=false;playerInvitated.turn=true; }
            }
        }
    }


    /**
     * Method that register user data and start they socket listener.
     * get and create user :socket, input class, output class and Socket listener.
     * Use a User CLass to save the data as instances, and data input and data output streams to communication.
     * @see Users
     * @return User if new user enter o null if error.
     */
    public Users addUser() {
        try {
            Socket clientSocket = publicSocket.accept();
            return new Users(clientSocket);
        } catch (IOException e) {
            logger.error("Could not connect"+e);
        }
        return null;
    }

    public static Logger getLogger() {
        return logger;
    }

    /**
     * Send the msg received to a user.
     * if the user Socket dont accept msg try to delete it
     * @param user User to send msg.
     * @param msg msg as string.
     * @throws IOException if fail closing the socket.
     */
    public void SendMsg( Users user, String msg){
        DataOutputStream out = user.getOut();
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            logger.error("Inactive port. It will be deleted");
            try {
                user.getUserSocket().close();
            } catch (IOException ex) {
                logger.error("unexpected errror closing port");
            }
        }
    }
}
