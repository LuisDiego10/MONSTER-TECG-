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
     * Semi-global variables
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
        for (int i = 0; i < 4; i++) {
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
            //Variables of events
            //Secrets Variables
            //Assistans
            boolean hostAssistans=false;
            boolean invitatedAssistans=false;
            //EyesxEyes
            boolean hostEyesxEyes=false;
            boolean invitatedEyesxEyes=false;
            //Cleaner
            //Strength
            boolean hostStrength=false;
            boolean invitatedStrength=false;
            //Sacrifice
            boolean hostSacrifice=false;
            boolean invitatedSacrifice=false;
            //Graveyard
            boolean hostGraveyard=false;
            boolean invitatedGraveyard=false;
            //TemporalTramp
            boolean hostTemporalTramp=false;
            boolean invitatedTemporalTramp=false;
            //Gas
            boolean hostGas=false;
            boolean invitatedGas=false;
            //ZombieTramp
            boolean hostZombies=false;
            boolean invitatedZombie=false;
            //Spell Variables
            //Getting turn
            boolean hostGettingTurn=true;
            boolean invitateGettingTurn=true;
            //Peek
            boolean hostAllowPeekDeck=true;
            boolean invitateAllowPeekDeck=true;
            //Attacks
            int hostMaxAttack=3;
            int invitateMaxAttack=3;
            //Freeze
            boolean hostFreeze=true;
            boolean invitatedFreeze=true;

            while (playerInvitated.turn) {
                //get action from client
                String action = "";
                try {
                    action = playerInvitated.in.readUTF();
                    while(!action.equals("turn")&&invitateGettingTurn){
                        action = playerInvitated.in.readUTF();
                    }
                    //Inicio del turno es aquí
                    if(playerInvitated.playerData.playerHand.sizeLCDE<=0){


                    }

                    if(invitatedTemporalTramp==true&&playerHost.playerData.playerHand.sizeLCDE<10) {
                        for (int i = 0; i < 4; i++) {
                            if (playerHost.playerData.playerTable[i] != null) {
                                playerHost.playerData.playerHand.insert(playerHost.playerData.playerTable[i]);
                                playerInvitated.playerData.playerTable[i]=null;
                                i=5;
                                invitatedTemporalTramp=false;
                                SendMsg();

                            }
                        }
                    }
                    if(invitatedStrength==true&&playerInvitated.playerData.life<=500) {
                        for (int i = 0; i < 4; i++) {
                            if (playerInvitated.playerData.playerTable[i] != null) {
                                playerInvitated.playerData.playerTable[i].healt+=100;
                                invitatedStrength=false;
                                SendMsg();
                            }
                        }
                    }
                    if(invitatedAssistans){
                        invitatedAssistans=false;
                        for (int i = 0; i < 4; i++) {
                            if (playerInvitated.playerData.playerTable[i] != null) {
                                playerInvitated.playerData.playerTable[i]=Factory.Master().minion[0];
                        }
                    }
                    }
                    if(invitatedZombie){
                        for (int i = 0; i < 4; i++) {
                            if (playerInvitated.playerData.playerTable[i] != null) {
                                playerInvitated.playerData.playerTable[i]=Factory.Master().minion[6];
                                invitatedZombie=false;
                                SendMsg();
                            }
                        }
                    }
                    logger.debug(action);
                    invitateGettingTurn=false;

                } catch (IOException e) {logger.error("error getting action trying again");}
                //finish turn
                if (action.equals("finish turn")) {
                    playerInvitated.turn = false;
                    playerHost.turn = true;
                    invitateGettingTurn=true;
                    invitateAllowPeekDeck=true;
                    invitateMaxAttack=3;
                    hostFreeze=true;
                    if (playerInvitated.playerData.mana <= 750){
                        playerInvitated.playerData.mana += 250;
                    }else{
                        playerInvitated.playerData.mana=1000;}
                    if(invitatedGas){
                        for(int i=0;i<4;i++){
                            if(playerHost.playerData.playerTable[i]!=null) {
                                playerHost.playerData.playerTable[i].healt -= 20;
                            }
                            if(playerInvitated.playerData.playerTable[i]!=null) {
                                playerInvitated.playerData.playerTable[i].healt -= 20;
                            }
                        }
                    }
                    try {
                        playerHost.out.writeUTF("turn");
                        SendMsg();
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
                                        SendMsg();
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
                                            playerInvitated.playerData.historial.insertLDE("Congelacion","Invitado", "Invocar");
                                            invitatedFreeze=false;
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        case "Curación":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.life += 200;
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            playerInvitated.playerData.historial.insertLDE("Curación","Invitado", "Invocar");
                                            SendMsg();
                                            break;
                                        case "PoderSupremo":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.playerHand.insert(playerInvitated.playerData.playerDeck.peek());
                                            playerInvitated.playerData.playerHand.insert(playerInvitated.playerData.playerDeck.peek());
                                            playerInvitated.playerData.playerHand.insert(playerInvitated.playerData.playerDeck.peek());
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            playerInvitated.playerData.historial.insertLDE("PoderSupremo","Invitado", "Invocar");
                                            SendMsg();
                                            break;
                                        case "AhoraEsMia":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("AhoraEsMia","Invitado", "Invocar");
                                            SendMsg();
                                            break;
                                        case "Escudo":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Escudo","Invitado", "Invocar");
                                            SendMsg();
                                            break;
                                        case "Invalidar":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Invalidar","Invitado", "Invocar");
                                            SendMsg();
                                            break;
                                        case "Anulacion":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Anulacion","Invitado", "Invocar");
                                            SendMsg();
                                            break;
                                        case "Contrarrestar":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Contrarrestar","Invitado", "Invocar");
                                            SendMsg();
                                            break;
                                        case "Duplicar":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Duplicar","Invitado", "Invocar");
                                            SendMsg();
                                            break;
                                        default:
                                            playerInvitated.playerData.mana += 150;
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            playerInvitated.playerData.historial.insertLDE("Economizador","Invitado", "Invocar");
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
                                            playerInvitated.playerData.historial.insertLDE("Refuerzos","Invitado", "Invocar");
                                            playerInvitated.playerData.playerHand.deleteNode("Refuerzos");
                                            invitatedAssistans=true;
                                            SendMsg();
                                            break;
                                        case "Ojo por ojo":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Ojo por ojo","Invitado", "Invocar");
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            invitatedEyesxEyes=true;
                                            SendMsg();
                                            break;
                                        case "Limpieza":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Limpieza","Invitado", "Invocar");
                                            SendMsg();
                                            break;
                                        case "Comercio":
                                            if (playerInvitated.playerData.playerHand.sizeLCDE>3&&playerHost.playerData.playerHand.sizeLCDE>3){
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Comercio","Invitado", "Invocar");
                                            Card playerOneCard=playerInvitated.playerData.playerHand.getStart().fact;
                                            Card playerTwoCard=playerHost.playerData.playerHand.getStart().fact;
                                            playerHost.playerData.playerHand.insert(playerOneCard);
                                            playerInvitated.playerData.playerHand.insert(playerTwoCard);
                                            playerInvitated.playerData.playerHand.deleteNode(playerOneCard.name);
                                            playerHost.playerData.playerHand.deleteNode(playerTwoCard.name);
                                            SendMsg();}
                                            break;
                                        case "Fortaleza":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Fortaleza","Invitado", "Invocar");
                                            playerInvitated.playerData.playerHand.deleteNode("Fortaleza");
                                            invitatedStrength=true;
                                            SendMsg();
                                            break;
                                        case "Sacrificio":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Sacrificio","Invitado", "Invocar");
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            invitatedSacrifice=true;
                                            SendMsg();
                                            break;
                                        case "Cementerio":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Cementerio","Invitado", "Invocar");
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            invitatedGraveyard=true;
                                            SendMsg();
                                            break;
                                        case "Trampa temporal":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Trampa temporal","Invitado", "Invocar");
                                            playerInvitated.playerData.playerHand.deleteNode("Trampa temporal");
                                            invitatedTemporalTramp=true;
                                            SendMsg();
                                            break;
                                        case "Gas":
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Gas","Invitado", "Invocar");
                                            SendMsg();
                                            break;
                                        default:
                                            playerInvitated.playerData.mana -= 300;
                                            playerInvitated.playerData.historial.insertLDE("Trampa zombie","Invitado", "Invocar");
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
                if (action.contains("attack")&&invitateMaxAttack>0&&hostFreeze) {
                    //get attacker name
                    action = action.substring(6);
                    Card attacker = null;
                    try {
                        for (int i=0;i<5;i++) {
                            //Get self hand card
                            if(playerInvitated.playerData.playerTable[i].name.equals(action)){
                                attacker=playerInvitated.playerData.playerTable[i];
                                i=5;
                                SendMsg();
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
                                    if(playerHost.playerData.playerTable[i].healt<=0){
                                        if (hostGraveyard==true&&playerHost.playerData.playerHand.sizeLCDE<10){
                                            Minion card = Factory.Master().minion[0];
                                            for (int c=0;c<=20;c++) {
                                                if(Factory.Master().minion[c].name.equals(action)){
                                                    card=Factory.Master().minion[c];
                                                }
                                            }
                                            playerHost.playerData.playerHand.insert(card);
                                            hostGraveyard=false;
                                            SendMsg();
                                        }
                                        playerHost.playerData.playerTable[i]=null;
                                        if (hostEyesxEyes==true){
                                            for (int c=0;c<5;c++) {
                                                //Get self hand card
                                                if(playerInvitated.playerData.playerTable[c].name.equals(attacker.name)) {
                                                    playerInvitated.playerData.playerTable[c]=null;
                                                    c = 5;
                                                    hostEyesxEyes=false;
                                                    SendMsg();
                                                    break;
                                                }
                                            }
                                        }
                                        if (hostSacrifice==true){
                                            if (playerHost.playerData.mana <= 700){
                                                playerHost.playerData.mana+=300;
                                                hostSacrifice=false;
                                                SendMsg();
                                            }
                                            else{
                                                playerHost.playerData.mana = 1000;
                                                hostSacrifice=false;
                                                SendMsg();
                                            }
                                        }

                                    }
                                    SendMsg();
                                    invitateMaxAttack--;
                                    break;
                                }
                            }
                            //if enemy hand empty direct attack

                            if(i==4){
                                playerHost.playerData.life-=attacker.damage;
                                playerInvitated.playerData.enemyLife=playerHost.playerData.life;
                                invitateMaxAttack--;
                                SendMsg();

                            }

                        }
                    } catch (NullPointerException e) {logger.error("Not catched attacked card, or no existing card");}
                }
                //peek a card from the deck
                if (action.equals("peek")&&invitateAllowPeekDeck){
                    playerInvitated.playerData.playerHand.insert(playerInvitated.playerData.playerDeck.peek());
                    SendMsg();
                    invitateAllowPeekDeck=false;
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
                    if(hostTemporalTramp==true&&playerInvitated.playerData.playerHand.sizeLCDE<10) {
                        for (int i = 0; i < 4; i++) {
                            if (playerInvitated.playerData.playerTable[i] != null) {
                                playerInvitated.playerData.playerHand.insert(playerHost.playerData.playerTable[i]);
                                playerHost.playerData.playerTable[i]=null;
                                i=5;
                                hostTemporalTramp=false;
                                SendMsg();

                            }
                        }
                    }

                    if(hostStrength==true&&playerHost.playerData.life<=500) {
                        for (int i = 0; i < 4; i++) {
                            if (playerHost.playerData.playerTable[i] != null) {
                                playerHost.playerData.playerTable[i].healt+=100;
                                hostStrength=false;
                                SendMsg();
                            }
                        }
                    }
                    if(hostAssistans){
                        hostAssistans=false;
                        if (playerHost.playerData.playerTable[3]==null){
                            playerHost.playerData.playerTable[3]=Factory.Master().minion[0];
                        }
                    }
                    if(invitatedZombie){
                        for (int i = 0; i < 4; i++) {
                            if (playerHost.playerData.playerTable[i] != null) {
                                playerHost.playerData.playerTable[i]=Factory.Master().minion[6];
                                invitatedZombie=false;
                                SendMsg();
                            }
                        }
                    }


                    logger.debug(action);
                    hostGettingTurn=false;
                } catch (IOException e) {logger.error("error getting action trying again");}
                //finish turn
                if (action.equals("finish turn")) {
                    playerHost.turn = false;
                    playerInvitated.turn = true;
                    hostGettingTurn=true;
                    hostAllowPeekDeck=true;
                    hostMaxAttack=3;
                    invitatedFreeze=true;
                    if (playerHost.playerData.mana <= 750){
                        playerHost.playerData.mana += 250;
                    }else{playerHost.playerData.mana=1000;}
                    if(hostGas){
                        for(int i=0;i<4;i++){
                            if(playerHost.playerData.playerTable[i]!=null) {
                                playerHost.playerData.playerTable[i].healt -= 20;
                            }
                            if(playerInvitated.playerData.playerTable[i]!=null) {
                                playerInvitated.playerData.playerTable[i].healt -= 20;
                            }
                        }
                    }
                    try {
                        playerInvitated.out.writeUTF("turn");
                        SendMsg();
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
                                        SendMsg();
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
                                            playerHost.playerData.historial.insertLDE("Congelacion", "Host", "Invocar");
                                            hostFreeze=false;
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            SendMsg();

                                            break;
                                        case "Curación":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.life += 200;
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            playerHost.playerData.historial.insertLDE("Curación", "Host", "Invocar");
                                            SendMsg();

                                            break;
                                        case "PoderSupremo":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.playerHand.insert(playerInvitated.playerData.playerDeck.peek());
                                            playerHost.playerData.playerHand.insert(playerInvitated.playerData.playerDeck.peek());
                                            playerHost.playerData.playerHand.insert(playerInvitated.playerData.playerDeck.peek());
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            playerHost.playerData.historial.insertLDE("PoderSupremo", "Host", "Invocar");
                                            SendMsg();

                                            break;
                                        case "AhoraEsMia":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("AhoraEsMia", "Host", "Invocar");
                                            SendMsg();

                                            break;
                                        case "Escudo":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Escudo", "Host", "Invocar");
                                            SendMsg();

                                            break;
                                        case "Invalidar":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Invalidar", "Host", "Invocar");
                                            SendMsg();

                                            break;
                                        case "Anulacion":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Anulacion", "Host", "Invocar");
                                            SendMsg();

                                            break;
                                        case "Contrarrestar":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Contrarrestar", "Host", "Invocar");
                                            SendMsg();

                                            break;
                                        case "Duplicar":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Duplicar", "Host", "Invocar");
                                            SendMsg();

                                            break;
                                        default:
                                            playerHost.playerData.mana += 150;
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            playerHost.playerData.historial.insertLDE("Economizador", "Host", "Invocar");
                                            SendMsg();
                                            break;
                                    }
                                }
                                //Secrets
                            } else {
                                if (playerHost.playerData.mana>=300){
                                    switch (action) {
                                        case "Refuerzos":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Refuerzos", "Host", "Invocar");
                                            playerHost.playerData.playerHand.deleteNode("Refuerzos");
                                            hostAssistans=true;
                                            SendMsg();

                                            break;
                                        case "Ojo por ojo":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Ojo por ojo", "Host", "Invocar");
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            hostEyesxEyes=true;
                                            SendMsg();
                                            break;
                                        case "Limpieza":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Limpieza", "Host", "Invocar");
                                            SendMsg();

                                            break;
                                        case "Comercio":
                                            if (playerHost.playerData.playerHand.sizeLCDE>3&&playerInvitated.playerData.playerHand.sizeLCDE>3){
                                                playerHost.playerData.mana -= 300;
                                                playerHost.playerData.historial.insertLDE("Comercio","Invitado", "Invocar");
                                                Card playerOneCard=playerHost.playerData.playerHand.getStart().fact;
                                                Card playerTwoCard=playerInvitated.playerData.playerHand.getStart().fact;
                                                playerInvitated.playerData.playerHand.insert(playerOneCard);
                                                playerHost.playerData.playerHand.insert(playerTwoCard);
                                                playerHost.playerData.playerHand.deleteNode(playerOneCard.name);
                                                playerInvitated.playerData.playerHand.deleteNode(playerTwoCard.name);
                                                SendMsg();}
                                            break;
                                        case "Fortaleza":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Fortaleza", "Host", "Invocar");
                                            playerHost.playerData.playerHand.deleteNode("Fortaleza");
                                            hostStrength=true;
                                            SendMsg();
                                            break;
                                        case "Sacrificio":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Sacrificio", "Host", "Invocar");
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            hostSacrifice=true;
                                            SendMsg();
                                            break;
                                        case "Cementerio":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Cementerio", "Host", "Invocar");
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            invitatedGraveyard=true;
                                            SendMsg();
                                            break;
                                        case "Trampa temporal":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Trampa temporal", "Host", "Invocar");
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        case "Gas":
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Gas", "Host", "Invocar");
                                            SendMsg();
                                            break;
                                        default:
                                            playerHost.playerData.mana -= 300;
                                            playerHost.playerData.historial.insertLDE("Trampa zombie", "Host", "Invocar");
                                            SendMsg();
                                            break;
                                    }
                                }
                            }
                        }
                    } catch (NullPointerException e) {logger.error("Card do not exist or not space, ignoring action" + e);}
                }
                //Attack
                if (action.contains("attack")&&hostMaxAttack>0&&invitatedFreeze) {
                    //get attacker name
                    action = action.substring(6);
                    Card attacker = null;
                    try {
                        for (int i=0;i<5;i++) {
                            //Get self hand card
                            if(playerHost.playerData.playerTable[i].name.equals(action)){
                                attacker=playerHost.playerData.playerTable[i];
                                i=5;
                                SendMsg();
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
                                    if(playerInvitated.playerData.playerTable[i].healt<=0){
                                        if (invitatedGraveyard==true&&playerInvitated.playerData.playerHand.sizeLCDE<10){
                                            Minion card=Factory.Master().minion[0];
                                            for (int c=0;c<=20;c++) {
                                                if(Factory.Master().minion[c].name.equals(action)){
                                                    card=Factory.Master().minion[c];
                                                }
                                            }
                                            playerInvitated.playerData.playerHand.insert(card);
                                            invitatedGraveyard=false;
                                            SendMsg();
                                        }
                                        playerInvitated.playerData.playerTable[i]=null;
                                        if (invitatedEyesxEyes==true){
                                            for (int c=0;c<5;c++) {
                                                //Get self hand card
                                                if(playerHost.playerData.playerTable[c].name.equals(attacker.name)) {
                                                    playerHost.playerData.playerTable[c] = null;
                                                    c = 5;
                                                    invitatedEyesxEyes=false;
                                                    SendMsg();
                                                    break;
                                                }
                                            }
                                        }
                                        if (invitatedSacrifice==true){
                                            if (playerInvitated.playerData.mana <= 700){
                                                playerInvitated.playerData.mana+=300;
                                                invitatedSacrifice=false;
                                                SendMsg();
                                            }
                                            else{
                                                playerInvitated.playerData.mana = 1000;
                                                invitatedSacrifice=false;
                                                SendMsg();
                                            }

                                        }
                                    }
                                    SendMsg();
                                    hostMaxAttack--;
                                    break;
                                }
                            }
                            //if enemy hand empty direct attack
                            if(i==4){
                                playerInvitated.playerData.life-=attacker.damage;
                                playerHost.playerData.enemyLife=playerInvitated.playerData.life;
                                hostMaxAttack--;
                                SendMsg();
                            }
                        }
                    } catch (NullPointerException e) {logger.error("Not catched attacked card, or no existing card");}
                }
                //peek a card from the deck
                if (action.equals("peek")&&hostAllowPeekDeck){
                    playerHost.playerData.playerHand.insert(playerInvitated.playerData.playerDeck.peek());
                    hostAllowPeekDeck=false;
                    SendMsg();
                }
            }
        }
        if(playerHost.playerData.life<0){
            try {
                playerInvitated.out.writeUTF("win");
            } catch (IOException e) {
                logger.error("error sendign the win order"+e);
            }
        }else{
            try {
                playerHost.out.writeUTF("win");
            } catch (IOException e) {
                logger.error("error sendign the win order"+e);
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
     * Send the data to a user.
     */
    public void SendMsg(){
        try {
            logger.debug("trying to sent deck to players");
            playerHost.getOut().writeUTF(Factory.Serializer(playerHost.playerData));
            playerInvitated.getOut().writeUTF(Factory.Serializer(playerInvitated.playerData));
            logger.debug("sended");
        } catch (IOException e) {
            logger.error("error trying to serialize the players data or sending it, info:\n"+e);
        }

    }
}