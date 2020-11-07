package Server;

import Card.Card;
import Card.Minion;
import Card.Spell;
import Factory.Factory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;


/**
 * Class of Server app.
 * Do all action with the Cards.
 * @see Users for aux class
 */
public class Server extends Thread {
    /**
     * semi-global variables
     */
    public ServerSocket publicSocket;//* socket
    private static Logger logger=LogManager.getLogger("Server");
    private Users playerHost;
    private Users playerInvitated;
    public Factory abstractFactory =new Factory();


    /**
     * Create the server´s socket whit the indicated port.
     * @param port port of the server.
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
     * Send to player the deck info.
     */
    public void run() {
        logger.debug("try ing to connect with host and player");
        playerHost = addUser();
        logger.debug("host connected");
        playerInvitated = addUser();
        logger.debug("player connected");
        logger.debug("generating player and host deck");
        playerHost.playerData.playerDeck = Factory.RandomDeck();
        playerInvitated.playerData.playerDeck = Factory.RandomDeck();
        for (int i = 0; i < 5; i++) {
            playerInvitated.playerData.playerHand.insert(playerInvitated.playerData.playerDeck.peek());
            playerInvitated.playerData.playerDeck.pop();
            playerHost.playerData.playerHand.insert(playerHost.playerData.playerDeck.peek());
            playerHost.playerData.playerDeck.pop();
        }
        logger.debug("decks generateds");
        SendMsg();
        playerInvitated.turn=true;
        SendMsg();
        try {
            playerInvitated.out.writeUTF("turn");
        } catch (IOException e) {
            logger.error("error trying to set first turn for invitated"+e);
        }
        while (playerHost.playerData.life > 0 | playerInvitated.playerData.life > 0) {
            SendMsg();
            boolean hostGettingTurn=true;
            boolean invitateGettingTurn=true;
            while (playerInvitated.turn) {
                //get action from client
                String action = "";
                try {
                    action = playerInvitated.in.readUTF();
                    while(!action.equals("turn")&&invitateGettingTurn){
                        action = playerInvitated.in.readUTF();
                    }
                    logger.debug(action);
                    invitateGettingTurn=false;
                } catch (IOException e) {logger.error("error getting action trying again");}
                //finish turn
                if (action.equals("finish turn")) {
                    playerInvitated.turn = false;
                    playerHost.turn = true;
                    invitateGettingTurn=true;
                    if (playerInvitated.playerData.mana <= 750){
                        playerInvitated.playerData.mana += 250;
                    }else{
                        playerInvitated.playerData.mana=1000;}
                    try {playerHost.out.writeUTF("turn");
                    } catch (IOException e) {
                        logger.error("error allowing turn to host, possibly close socket"+e);}
                }
                //Invoke
                if (action.contains("invoke")) {
                    //get card name
                    action = action.substring(6);
                    try {
                        if (!Objects.equals(playerInvitated.playerData.playerHand.getNode(action).fact.name, "")) {
                            // detect with kind of card is.
                            if (playerInvitated.playerData.playerHand.getNode(action).fact.getClass().equals(Minion.class)) {
                                //found empty space in hand to invoke
                                for (int i = 0; i < 4; i++) {
                                    if (playerInvitated.playerData.playerTable[i] == null) {
                                        playerInvitated.playerData.playerTable[i] = playerInvitated.playerData.playerHand.getNode(action).fact;
                                        playerInvitated.playerData.playerHand.deleteNode(action);
                                        playerHost.playerData.enemyTable = playerInvitated.playerData.playerTable;
                                        playerInvitated.playerData.historial.insertLDE(playerInvitated.playerData.playerTable[i].name,"invitado","invocar");
                                        break;}
                                }
                                //update the enemy hand
                                playerHost.playerData.enemyTable = playerInvitated.playerData.playerTable;
                                SendMsg();
                                //Spells
                            } else if (playerInvitated.playerData.playerHand.getNode(action).fact.getClass().equals(Spell.class)) {
                                if (playerInvitated.playerData.mana>=300){
                                    switch (action) {
                                        case "Congelacion":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("congelacion","invitado","invocar");
                                            SendMsg();
                                            break;
                                        case "Curación":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.life += 200;
                                            SendMsg();
                                            break;
                                        case "PoderSupremo":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "AhoraEsMia":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Escudo":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Invalidar":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Anulacion":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Contrarrestar":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Duplicar":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        default:
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                    }

                                }
                                //Secrets
                            } else {
                                if (playerInvitated.playerData.mana>=300){
                                    switch (action) {
                                        case "Refuerzos":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Ojo por ojo":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Limpieza":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Comercio":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Fortaleza":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Sacrificio":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Cementerio":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Trampa temporal":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Gas":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        default:
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                    }
                                }
                            }
                        }

                    } catch (NullPointerException e) {
                        logger.error("Card do not exist or not space, ignoring action" + e);
                    }
                }
                //Attack
                if (action.contains("attack")) {
                    //get attacker name
                    action = action.substring(6);
                    Card attacker = null;
                    try {
                        for (int i=0;i<5;i++) {
                            //Get self hand card
                            if(playerInvitated.playerData.playerTable[i].name.equals(action)){
                                attacker=playerInvitated.playerData.playerTable[i];
                                i=5;
                                break;
                            }
                        }
                        if(attacker==null){break;}
                        try {
                            //Get enemy card name, empty is direct attack
                            action = playerInvitated.in.readUTF();
                        } catch (IOException e) {logger.error("error getting action trying again");}
                        //Get enemy hand card
                        for (int i=0;i<5;i++) {
                            if(i<playerHost.playerData.playerTable.length && playerHost.playerData.playerTable[i]!=null){
                                if(playerHost.playerData.playerTable[i].name.equals(action)){
                                    playerHost.playerData.playerTable[i].healt-=attacker.damage;
                                    if(playerHost.playerData.playerTable[i].healt<=0){playerHost.playerData.playerTable[i]=null; }
                                    SendMsg();
                                    break;
                                }
                            }
                            //if enemy hand empty direct attack
                            if(i==4){playerHost.playerData.life-=attacker.damage;}
                        }
                    } catch (NullPointerException e) {logger.error("Not catched attacked card, or no existing card");}
                }
                //peek a card from the deck
                if (action.equals("peek")){
                    playerInvitated.playerData.playerHand.insert(playerInvitated.playerData.playerDeck.peek());
                    SendMsg();
                }
            }
            while (playerHost.turn) {
                //get action from client
                String action = "";
                try {
                    action = playerHost.in.readUTF();
                    while(!action.equals("turn")&&hostGettingTurn){
                        action = playerHost.in.readUTF();
                    }
                    logger.debug(action);
                    hostGettingTurn=false;
                } catch (IOException e) {logger.error("error getting action trying again");}
                //finish turn
                if (action.equals("finish turn")) {
                    playerHost.turn = false;
                    playerInvitated.turn = true;
                    hostGettingTurn=true;
                    if (playerHost.playerData.mana <= 750){
                        playerHost.playerData.mana += 250;
                    }else{playerHost.playerData.mana=1000;}
                    try {
                        playerInvitated.out.writeUTF("turn");
                    } catch (IOException e) {logger.error("error allowing turn to host, possibly close socket"+e); }}
                //invoke
                if (action.contains("invoke")) {
                    //get card name
                    action = action.substring(6);
                    try {
                        if (!Objects.equals(playerHost.playerData.playerHand.getNode(action).fact.name, "")) {
                            // detect what kind of card is.
                            if (playerHost.playerData.playerHand.getNode(action).fact.getClass().equals(Minion.class)) {
                                //found empty space in hand to invoke
                                for (int i = 0; i < 4; i++) {
                                    if (playerHost.playerData.playerTable[i] == null) {
                                        playerHost.playerData.playerTable[i] = playerHost.playerData.playerHand.getNode(action).fact;
                                        playerHost.playerData.playerHand.deleteNode(action);
                                        playerInvitated.playerData.enemyTable = playerHost.playerData.playerTable;
                                        playerHost.playerData.historial.insertLDE(playerHost.playerData.playerTable[i].name,"invitado","invocar");
                                        break;
                                    }
                                }
                                //update the enemy hand
                                playerInvitated.playerData.enemyTable = playerHost.playerData.playerTable;
                                SendMsg();
                                //Spells
                            } else if (playerHost.playerData.playerHand.getNode(action).fact.getClass().equals(Spell.class)) {
                                if (playerHost.playerData.mana>=300){
                                    switch (action) {
                                        case "Congelacion":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("congelacion","invitado","invocar");
                                            SendMsg();
                                            break;
                                        case "Curación":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.life += 200;
                                            SendMsg();
                                            break;
                                        case "PoderSupremo":
                                            playerHost.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "AhoraEsMia":
                                            playerHost.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Escudo":
                                            playerHost.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Invalidar":
                                            playerHost.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Anulacion":
                                            playerHost.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Contrarrestar":
                                            playerHost.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Duplicar":
                                            playerHost.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        default:
                                            playerHost.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                    }

                                }
                                //Secrets
                            } else {
                                if (playerHost.playerData.mana>=300){
                                    switch (action) {
                                        case "Refuerzos":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Ojo por ojo":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Limpieza":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Comercio":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Fortaleza":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Sacrificio":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Cementerio":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Trampa temporal":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        case "Gas":
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                        default:
                                            playerInvitated.playerData.mana -= 300;
                                            SendMsg();
                                            break;
                                    }
                                }
                            }
                        }
                    } catch (NullPointerException e) {logger.error("Card do not exist or not space, ignoring action" + e);}
                }
                //Attack
                if (action.contains("attack")) {
                    //get attacker name
                    action = action.substring(6);
                    Card attacker = null;
                    try {
                        for (int i=0;i<5;i++) {
                            //Get self hand card
                            if(playerHost.playerData.playerTable[i].name.equals(action)){
                                attacker=playerHost.playerData.playerTable[i];
                                i=5;
                                break;
                            }
                        }
                        if(attacker==null){break;}
                        try {
                            //Get enemy card name, empty is direct attack
                            action = playerHost.in.readUTF();
                        } catch (IOException e) {logger.error("error getting action trying again");}
                        //Get enemy hand card
                        for (int i=0;i<5;i++) {
                            if(i<playerInvitated.playerData.playerTable.length && playerInvitated.playerData.playerTable[i]!=null){
                                if(playerInvitated.playerData.playerTable[i].name.equals(action)){
                                    playerInvitated.playerData.playerTable[i].healt-=attacker.damage;
                                    if(playerInvitated.playerData.playerTable[i].healt<=0){playerInvitated.playerData.playerTable[i]=null;}
                                    SendMsg();
                                    break;
                                }
                            }
                            //if enemy hand empty direct attack
                            if(i==4){playerInvitated.playerData.life-=attacker.damage;}
                        }
                    } catch (NullPointerException e) {logger.error("Not catched attacked card, or no existing card");}
                }
                //peek a card from the deck
                if (action.equals("peek")){
                    playerHost.playerData.playerHand.insert(playerInvitated.playerData.playerDeck.peek());
                    SendMsg();
                }
            }
        }
    }
    /**
     * Method that register user data and start they socket listener.
     * get and create user :socket, input class, output class and Socket listener.
     * Use a User CLass to save the data as instances, and data input and data output streams to communication.
     * @see Users
     * @return User, or null if error.
     */
    public Users addUser() {
        try {
            Socket clientSocket = publicSocket.accept();
            return new Users(clientSocket);
        } catch (IOException e) {
            logger.error("Could not connect"+e);
        }catch (NullPointerException e) {
            logger.error("Port occupied"+e);
        }
        return null;
    }
    /**
     * Logger getter
     * @return logger
     * @author Isaac
     * @version 1.0
     * @since 4/10/2020
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Send the msg received to a user.
     * if the user Socket dont accept msg try to delete it
     * @throws IOException if fail closing the socket.
     */
    public void SendMsg(){
        try {
            logger.debug("try ing to sent deck");
            playerHost.getOut().writeUTF(Factory.Serializer(playerHost.playerData));
            logger.debug("host sended");
            playerInvitated.getOut().writeUTF(Factory.Serializer(playerInvitated.playerData));
            logger.debug("player sended");
        } catch (IOException e) {
            logger.error("error trying to serialize the players data or sending it, info:\n"+e);
        }

    }
}
