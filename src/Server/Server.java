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
    private Logger logger=LogManager.getLogger();
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
     * Start the server
     */
    public void run() {
        playerHost=addUser();
        playerInvitated=addUser();
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
            Users user = new Users(clientSocket);
            user.start();
            return user;
        } catch (IOException e) {
            logger.error("Could not connect"+e);
        }
        return null;
    }

    /**
     * Comunication between Users listenig and Servers.
     * When recieve a msg from a Users listening send this to log and to another users.
     * @param user user who send the msg.
     * @param msg msg as string.
     * @throws IOException for msg thats is not a string or a closed  user´s Socket with open listening.
     */

    /**
     * Send the msg received to a user.
     * if the user Socket dont accept msg try to delete it
     * @param user User to send msg.
     * @param msg msg as string.
     * @throws IOException if fail closing the socket.
     */
    public static void SendMsg( Users user, String msg){
        DataOutputStream out = user.getOut();
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            logger.error("Inactive port. It will be deleted");
            user.getUserSocket().close();
        }
    }
}
