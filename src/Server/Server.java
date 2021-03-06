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
        //Variables of events
        //Getting turn
        boolean hostGettingTurn=true;
        boolean invitateGettingTurn=true;
        //Peek
        boolean hostAllowPeekDeck=true;
        boolean invitateAllowPeekDeck=true;
        //Attacks
        int hostMaxAttack=3;
        int invitateMaxAttack=3;

        //Secrets Variables
        //Assistans
        boolean hostAssistans=false;
        boolean invitatedAssistans=false;
        //EyesxEyes
        boolean hostEyesxEyes=false;
        boolean invitatedEyesxEyes=false;
        //Cleaner
        boolean hostCleaner=false;
        boolean invitatedCleaner=false;
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
        //Freeze
        boolean hostFreeze=true;
        boolean invitatedFreeze=true;
        //Supreme power
        int hostSupremePower=0;
        int invitatedSupremePower=0;
        //Shield
        boolean hostShield=false;
        boolean invitatedShield=false;
        //DoubleAttack
        boolean hostDoubleAttack =false;
        boolean invitatedDoubleAttack=false;
        //CounterAttack
        boolean hostCounterAttack=false;
        boolean invitatedCounterAttack=false;
        while (playerHost.playerData.life > 0 | playerInvitated.playerData.life > 0) {
            SendMsg();


            while (playerInvitated.turn) {
                //get action from client
                String action = "";
                try {
                    action = playerInvitated.in.readUTF();
                    while(!action.equals("turn")&&invitateGettingTurn){
                        action = playerInvitated.in.readUTF();
                    }
                    //Inicio del turno es aquí
                    if(invitatedCleaner &&playerInvitated.playerData.playerHand.sizeLCDE<=0){
                        //Assistans
                        hostAssistans=false;
                        //EyesxEyes
                        hostEyesxEyes=false;
                        //Strength
                        hostStrength=false;
                        //Sacrifice
                        hostSacrifice=false;
                        //Graveyard
                        hostGraveyard=false;
                        //TemporalTramp
                        hostTemporalTramp=false;
                        //Gas
                        hostGas=false;
                        //ZombieTramp
                        hostZombies=false;

                        invitatedCleaner=false;

                        SendMsg();
                    }

                    if(invitatedTemporalTramp &&playerHost.playerData.playerHand.sizeLCDE<10) {
                        for (int i = 0; i < 4; i++) {
                            if (playerHost.playerData.playerTable[i] != null) {
                                playerHost.playerData.playerHand.insert(playerHost.playerData.playerTable[i]);
                                playerInvitated.playerData.playerTable[i]=null;
                                i=4;
                                invitatedTemporalTramp=false;
                                SendMsg();

                            }
                        }
                    }
                    if(invitatedStrength &&playerInvitated.playerData.life<=500) {
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
                            invitatedGas=false;
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
                        if (!Objects.equals(playerInvitated.playerData.playerHand.getStart().fact.name, "") || !Objects.equals(playerInvitated.playerData.playerHand.getNode(action).fact.name, "")) {
                            // detect with kind of card is.
                            if (playerInvitated.playerData.playerHand.getNode(action).fact.getClass().equals(Minion.class)) {
                                //found empty space in hand to invoke
                                for (int i = 0; i < 4; i++) {
                                    if(playerInvitated.playerData.mana>playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost()) {
                                        if (playerInvitated.playerData.playerTable[i] == null) {
                                            playerInvitated.playerData.playerTable[i] = playerInvitated.playerData.playerHand.getNode(action).fact;
                                            playerHost.playerData.enemyTable = playerInvitated.playerData.playerTable;
                                            playerInvitated.playerData.historial.insertLDE(playerInvitated.playerData.playerTable[i].name, "invitado", "invocar");
                                            playerHost.playerData.historial.insertLDE(playerInvitated.playerData.playerTable[i].name, "invitado", "invocar");
                                            if (invitatedSupremePower>0) {
                                                invitatedSupremePower-=1;
                                            }else {
                                                playerInvitated.playerData.mana -= playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            }
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        }
                                    }
                                }
                                //update the enemy hand
                                playerHost.playerData.enemyTable = playerInvitated.playerData.playerTable;
                                SendMsg();
                                //Spells
                            } else if (playerInvitated.playerData.playerHand.getNode(action).fact.getClass().equals(Spell.class)) {
                                if (playerInvitated.playerData.mana>playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost()){
                                    switch (action) {
                                        case "Congelacion":
                                            playerInvitated.playerData.historial.insertLDE("Congelacion","Invitado", "Invocar");
                                            playerHost.playerData.historial.insertLDE("Congelacion","Invitado", "Invocar");
                                            invitatedFreeze=false;
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        case "Curación":
                                            if (playerInvitated.playerData.life>=750){
                                                playerInvitated.playerData.life=1000;
                                            }else{
                                                playerInvitated.playerData.life+=250;
                                            }
                                            playerInvitated.playerData.historial.insertLDE("Curación","Invitado", "Invocar");
                                            playerHost.playerData.historial.insertLDE("Curación","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        case "PoderSupremo":
                                            invitatedSupremePower=3;
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            playerInvitated.playerData.historial.insertLDE("PoderSupremo","Invitado", "Invocar");
                                            playerHost.playerData.historial.insertLDE("PoderSupremo","Invitado", "Invocar");
                                            SendMsg();
                                            break;
                                        case "AhoraEsMia":
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerInvitated.playerData.historial.insertLDE("AhoraEsMia","Invitado", "Invocar");
                                            playerHost.playerData.historial.insertLDE("AhoraEsMia","Invitado", "Invocar");
                                            for (int i = 0; i < playerHost.playerData.playerTable.length; i++) {
                                                if (playerHost.playerData.playerTable[i] != null) {
                                                    for (int j = 0; j < playerInvitated.playerData.playerTable.length; j++) {
                                                        if (playerInvitated.playerData.playerTable[j] == null) {
                                                            playerInvitated.playerData.playerTable[j] =playerHost.playerData.playerTable[j];
                                                            playerHost.playerData.playerTable[i]=null;
                                                            i=25;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        case "Escudo":
                                            playerInvitated.playerData.historial.insertLDE("Escudo","Invitado", "Invocar");
                                            playerHost.playerData.historial.insertLDE("Escudo","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            invitatedShield=true;
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        case "Invalidar":
                                            playerInvitated.playerData.historial.insertLDE("Invalidar","Invitado", "Invocar");
                                            playerHost.playerData.historial.insertLDE("Invalidar","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            //Secrets Variables
                                            //Assistans
                                            hostAssistans=false;
                                            //EyesxEyes
                                            hostEyesxEyes=false;
                                            //Cleaner
                                            hostCleaner=false;
                                            //Strength
                                            hostStrength=false;
                                            //Sacrifice
                                            hostSacrifice=false;
                                            //Graveyard
                                            hostGraveyard=false;
                                            //TemporalTramp
                                            hostTemporalTramp=false;
                                            //Gas
                                            hostGas=false;
                                            //ZombieTramp
                                            hostZombies=false;
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        case "Anulacion":
                                            playerInvitated.playerData.historial.insertLDE("Anulacion","Invitado", "Invocar");
                                            playerHost.playerData.historial.insertLDE("Anulacion","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            //Spell Variables
                                            //Freeze
                                             hostFreeze=true;
                                            //Supreme power
                                             hostSupremePower=3;
                                            //Shield
                                             hostShield=false;
                                             playerInvitated.playerData.playerHand.deleteNode(action);
                                             SendMsg();
                                            break;
                                        case "Contrarrestar":
                                            playerInvitated.playerData.historial.insertLDE("Contrarrestar","Invitado", "Invocar");
                                            playerHost.playerData.historial.insertLDE("Contrarrestar","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            invitatedCounterAttack=true;
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        case "Duplicar":
                                            invitatedDoubleAttack=true;
                                            playerInvitated.playerData.historial.insertLDE("Duplicar","Invitado", "Invocar");
                                            playerHost.playerData.historial.insertLDE("Duplicar","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        default:
                                            if(playerInvitated.playerData.mana<700){
                                                playerInvitated.playerData.mana += 300;
                                            }else{
                                                playerInvitated.playerData.mana=1000;
                                            }
                                            playerInvitated.playerData.historial.insertLDE("Economizador","Invitado", "Invocar");
                                            playerHost.playerData.historial.insertLDE("Economizador","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                    }

                                }
                                //Secrets
                            } else {
                                if (playerInvitated.playerData.mana>playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost()){
                                    switch (action) {
                                        case "Refuerzos":
                                            playerInvitated.playerData.historial.insertLDE("Refuerzos","Invitado", "Invocar");
                                            invitatedAssistans=true;
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerInvitated.playerData.playerHand.deleteNode("Refuerzos");
                                            SendMsg();
                                            break;
                                        case "Ojo por ojo":
                                            playerInvitated.playerData.historial.insertLDE("Ojo por ojo","Invitado", "Invocar");
                                            invitatedEyesxEyes=true;
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        case "Limpieza":
                                            playerInvitated.playerData.historial.insertLDE("Limpieza","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            invitatedCleaner=true;
                                            SendMsg();
                                            break;
                                        case "Comercio":
                                            if (playerInvitated.playerData.playerHand.sizeLCDE>3&&playerHost.playerData.playerHand.sizeLCDE>3){
                                            playerInvitated.playerData.historial.insertLDE("Comercio","Invitado", "Invocar");
                                            Card playerOneCard=playerInvitated.playerData.playerHand.getStart().fact;
                                            Card playerTwoCard=playerHost.playerData.playerHand.getStart().fact;
                                            playerHost.playerData.playerHand.insert(playerOneCard);
                                            playerInvitated.playerData.playerHand.insert(playerTwoCard);
                                                playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                                playerInvitated.playerData.playerHand.deleteNode(playerOneCard.name);
                                                playerHost.playerData.playerHand.deleteNode(playerTwoCard.name);
                                                SendMsg();}
                                            break;
                                        case "Fortaleza":
                                            playerInvitated.playerData.historial.insertLDE("Fortaleza","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerInvitated.playerData.playerHand.deleteNode("Fortaleza");
                                            invitatedStrength=true;
                                            SendMsg();
                                            break;
                                        case "Sacrificio":
                                            playerInvitated.playerData.historial.insertLDE("Sacrificio","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            invitatedSacrifice=true;
                                            SendMsg();
                                            break;
                                        case "Cementerio":
                                            playerInvitated.playerData.historial.insertLDE("Cementerio","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerInvitated.playerData.playerHand.deleteNode(action);
                                            invitatedGraveyard=true;
                                            SendMsg();
                                            break;
                                        case "Trampa temporal":
                                            playerInvitated.playerData.historial.insertLDE("Trampa temporal","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerInvitated.playerData.playerHand.deleteNode("Trampa temporal");
                                            invitatedTemporalTramp=true;
                                            SendMsg();
                                            break;
                                        case "Gas":
                                            playerInvitated.playerData.historial.insertLDE("Gas","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            invitatedGas=true;
                                            playerInvitated.playerData.playerHand.deleteNode("Gas");
                                            SendMsg();
                                            break;
                                        default:
                                            playerInvitated.playerData.historial.insertLDE("Trampa zombie","Invitado", "Invocar");
                                            playerInvitated.playerData.mana-=playerInvitated.playerData.playerHand.getNode(action).fact.getManaCost();
                                            invitatedZombie=true;
                                            playerInvitated.playerData.playerHand.deleteNode("Trampa zombie");
                                            SendMsg();
                                            break;
                                    }
                                }
                            }
                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        logger.error("Card do not exist or not space, ignoring action" + e);
                    }
                }
                //Attack
                if (action.contains("attack")&&invitateMaxAttack>0&&hostFreeze) {
                    //get attacker name
                    action = action.substring(6);
                    Card attacker = null;
                    try {
                        for (int i = 0; i < 4; i++) {
                            //Get self hand card
                            if (playerInvitated.playerData.playerTable[i].name.equals(action)) {
                                attacker = playerInvitated.playerData.playerTable[i];
                                i = 4;
                                break;
                            }
                        }
                        if (attacker == null) {
                            break;
                        }
                        try {
                            //Get enemy card name, empty is direct attack
                            action = playerInvitated.in.readUTF();
                        } catch (IOException e) {
                            logger.error("error getting action trying again");
                        }
                        //Get enemy hand card
                        boolean attackPlayer=true;
                        for (int i = 0; i < 4; i++) {
                            if (i < playerHost.playerData.playerTable.length && playerHost.playerData.playerTable[i] != null) {
                                if (playerHost.playerData.playerTable[i].name.equals(action)) {
                                    attackPlayer=false;
                                    if (!hostShield) {

                                        if (invitatedDoubleAttack) {
                                            playerHost.playerData.playerTable[i].healt -= attacker.damage;
                                            playerHost.playerData.playerTable[i].healt -= attacker.damage;
                                            invitatedDoubleAttack = false;
                                        } else {
                                            playerHost.playerData.playerTable[i].healt -= attacker.damage;
                                        }
                                        playerInvitated.playerData.historial.insertLDE(attacker.name,"invitated", "atacar");
                                        playerHost.playerData.historial.insertLDE(attacker.name,"invitated", "atacar");
                                        if (playerHost.playerData.playerTable[i].healt <= 0) {
                                            if (hostGraveyard && playerHost.playerData.playerHand.sizeLCDE < 10) {
                                                Minion card = Factory.Master().minion[0];
                                                for (int c = 0; c <= 20; c++) {
                                                    if (Factory.Master().minion[c].name.equals(action)) {
                                                        card = Factory.Master().minion[c];
                                                    }
                                                }
                                                playerHost.playerData.playerHand.insert(card);
                                                hostGraveyard = false;
                                            }
                                            playerHost.playerData.playerTable[i] = null;
                                            if (hostEyesxEyes) {
                                                for (int c = 0; c < 4; c++) {
                                                    //Get self hand card
                                                    if (playerInvitated.playerData.playerTable[c] != null) {
                                                        if (playerInvitated.playerData.playerTable[c].name.equals(attacker.name)) {
                                                            playerInvitated.playerData.playerTable[c] = null;
                                                            c = 4;
                                                            hostEyesxEyes = false;

                                                            break;
                                                        }
                                                    }
                                                }
                                            }

                                            if (hostSacrifice == true) {
                                                if (playerHost.playerData.mana <= 700) {
                                                    playerHost.playerData.mana += 300;
                                                    hostSacrifice = false;
                                                } else {
                                                    playerHost.playerData.mana = 1000;
                                                    hostSacrifice = false;
                                                }
                                            }

                                        }
                                        if (hostCounterAttack) {
                                            for (int c = 0; c < 4; c++) {
                                                //Get self hand card
                                                if (playerInvitated.playerData.playerTable[c] != null) {
                                                    if (playerInvitated.playerData.playerTable[c].name.equals(attacker.name)) {
                                                        playerInvitated.playerData.playerTable[c].healt -= playerInvitated.playerData.playerTable[c].damage;
                                                        hostCounterAttack = false;
                                                        if(playerInvitated.playerData.playerTable[c].healt<0){
                                                            playerInvitated.playerData.playerTable[c]=null;
                                                        }
                                                        break;
                                                    }
                                                }
                                            }
                                        }

                                    } else {
                                        invitateMaxAttack--;
                                        hostShield = false;
                                    }
                                    SendMsg();
                                }
                            }

                            //if enemy hand empty direct attack

                            if (attackPlayer&&i==3) {
                                    playerHost.playerData.life -= attacker.damage;
                                    playerInvitated.playerData.enemyLife = playerHost.playerData.life;
                                    invitateMaxAttack--;
                                SendMsg();

                            }


                        }

                    } catch (NullPointerException e) {logger.error("Not catched attacked card, or no existing card");}
                }
                //peek a card from the deck
                if (action.equals("peek")&&invitateAllowPeekDeck){
                    playerInvitated.playerData.playerHand.insert(playerInvitated.playerData.playerDeck.peek());
                    playerInvitated.playerData.playerDeck.pop();
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
                    if(hostCleaner &&playerHost.playerData.playerHand.sizeLCDE<=0){
                        //Assistans
                        invitatedAssistans=false;
                        //EyesxEyes
                        invitatedEyesxEyes=false;
                        //Strength
                        invitatedStrength=false;
                        //Sacrifice
                        invitatedSacrifice=false;
                        //Graveyard
                        invitatedGraveyard=false;
                        //TemporalTramp
                        invitatedTemporalTramp=false;
                        //Gas
                        invitatedGas=false;
                        //ZombieTramp
                        invitatedZombie=false;

                        hostCleaner=false;
                        SendMsg();

                    }
                    if(hostTemporalTramp &&playerInvitated.playerData.playerHand.sizeLCDE<10) {
                        for (int i = 0; i < 4; i++) {
                            if (playerInvitated.playerData.playerTable[i] != null) {
                                playerInvitated.playerData.playerHand.insert(playerHost.playerData.playerTable[i]);
                                playerHost.playerData.playerTable[i]=null;
                                i=4;
                                hostTemporalTramp=false;
                                SendMsg();

                            }
                        }
                    }

                    if(hostStrength&&playerHost.playerData.life<=500) {
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
                    if(hostZombies){
                        for (int i = 0; i < 4; i++) {
                            if (playerHost.playerData.playerTable[i] != null) {
                                playerHost.playerData.playerTable[i]=Factory.Master().minion[6];
                                hostZombies=false;
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
                                    if(playerHost.playerData.mana>playerHost.playerData.playerHand.getNode(action).fact.getManaCost()) {
                                        if (playerHost.playerData.playerTable[i] == null) {
                                            playerHost.playerData.playerTable[i] = playerHost.playerData.playerHand.getNode(action).fact;
                                            if (hostSupremePower>0) {
                                                hostSupremePower-=1;
                                            }else {
                                                playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            }
                                            playerHost.playerData.historial.insertLDE(playerHost.playerData.playerTable[i].name, "host", "invocar");
                                            playerInvitated.playerData.historial.insertLDE(playerHost.playerData.playerTable[i].name, "host", "invocar");
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            playerInvitated.playerData.enemyTable = playerHost.playerData.playerTable;
                                            SendMsg();
                                            break;
                                        }
                                    }
                                }
                                //update the enemy hand
                                playerInvitated.playerData.enemyTable = playerHost.playerData.playerTable;
                                SendMsg();
                                //Spells
                            } else if (playerHost.playerData.playerHand.getNode(action).fact.getClass().equals(Spell.class)) {
                                if (playerHost.playerData.mana>playerHost.playerData.playerHand.getNode(action).fact.getManaCost()){
                                    switch (action) {
                                        case "Congelacion":
                                            playerHost.playerData.historial.insertLDE("Congelacion", "Host", "Invocar");
                                            playerInvitated.playerData.historial.insertLDE("Congelacion", "Host", "Invocar");
                                            hostFreeze=false;
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        case "Curación":
                                            if (playerHost.playerData.life>=750){
                                                playerHost.playerData.life=1000;
                                            }else{
                                                playerHost.playerData.life+=250;
                                            }
                                            playerHost.playerData.historial.insertLDE("Curación", "Host", "Invocar");
                                            playerInvitated.playerData.historial.insertLDE("Curación", "Host", "Invocar");
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        case "PoderSupremo":
                                            hostSupremePower=3;
                                            playerHost.playerData.historial.insertLDE("PoderSupremo", "Host", "Invocar");
                                            playerInvitated.playerData.historial.insertLDE("PoderSupremo", "Host", "Invocar");
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                        case "AhoraEsMia":
                                            playerHost.playerData.historial.insertLDE("AhoraEsMia", "Host", "Invocar");
                                            playerInvitated.playerData.historial.insertLDE("AhoraEsMia", "Host", "Invocar");
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            for (int i = 0; i < playerInvitated.playerData.playerTable.length; i++) {
                                                if (playerInvitated.playerData.playerTable[i] != null) {
                                                    for (int j = 0; j < playerHost.playerData.playerTable.length; j++) {
                                                        if (playerHost.playerData.playerTable[j] == null) {
                                                            playerHost.playerData.playerTable[j] =playerHost.playerData.playerTable[j];
                                                            playerInvitated.playerData.playerTable[i]=null;
                                                        }
                                                    }
                                                }
                                            }
                                            SendMsg();

                                            break;
                                        case "Escudo":
                                            playerHost.playerData.historial.insertLDE("Escudo","Host", "Invocar");
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            hostShield=true;
                                            playerHost.playerData.playerHand.deleteNode(action);

                                            SendMsg();
                                            break;
                                        case "Invalidar":
                                            playerHost.playerData.historial.insertLDE("Invalidar", "Host", "Invocar");
                                            playerInvitated.playerData.historial.insertLDE("Invalidar", "Host", "Invocar");
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            //Secrets Variables
                                            //Assistans
                                            invitatedAssistans=false;
                                            //EyesxEyes
                                            invitatedEyesxEyes=false;
                                            //Cleaner
                                            invitatedCleaner=false;
                                            //Strength
                                            invitatedStrength=false;
                                            //Sacrifice
                                            invitatedSacrifice=false;
                                            //Graveyard
                                            invitatedGraveyard=false;
                                            //TemporalTramp
                                            invitatedTemporalTramp=false;
                                            //Gas
                                            invitatedGas=false;
                                            //ZombieTramp
                                            invitatedZombie=false;
                                            SendMsg();

                                            break;
                                        case "Anulacion":
                                            playerHost.playerData.historial.insertLDE("Anulacion", "Host", "Invocar");
                                            playerInvitated.playerData.historial.insertLDE("Anulacion", "Host", "Invocar");
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            invitatedFreeze=true;
                                            //Supreme power
                                            invitatedSupremePower=3;
                                            //Shield
                                            invitatedShield=false;
                                            SendMsg();

                                            break;
                                        case "Contrarrestar":
                                            playerHost.playerData.historial.insertLDE("Contrarrestar", "Host", "Invocar");
                                            playerInvitated.playerData.historial.insertLDE("Contrarrestar", "Host", "Invocar");
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            hostCounterAttack=true;
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            SendMsg();

                                            break;
                                        case "Duplicar":
                                            playerHost.playerData.historial.insertLDE("Duplicar", "Host", "Invocar");
                                            playerInvitated.playerData.historial.insertLDE("Duplicar", "Host", "Invocar");
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            hostDoubleAttack=true;
                                            SendMsg();

                                            break;
                                        default:
                                            if(playerHost.playerData.mana<700){
                                                playerHost.playerData.mana += 300;
                                            }else{
                                                playerHost.playerData.mana=1000;
                                            }
                                            playerHost.playerData.historial.insertLDE("Economizador", "Host", "Invocar");
                                            playerInvitated.playerData.historial.insertLDE("Economizador", "Host", "Invocar");
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            SendMsg();
                                            break;
                                    }
                                }
                                //Secrets
                            } else {
                                if (playerHost.playerData.mana>=300){
                                    switch (action) {
                                        case "Refuerzos":
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.historial.insertLDE("Refuerzos", "Host", "Invocar");
                                            playerHost.playerData.playerHand.deleteNode("Refuerzos");
                                            hostAssistans=true;
                                            SendMsg();

                                            break;
                                        case "Ojo por ojo":
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.historial.insertLDE("Ojo por ojo", "Host", "Invocar");
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            hostEyesxEyes=true;
                                            SendMsg();
                                            break;
                                        case "Limpieza":
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.historial.insertLDE("Limpieza", "Host", "Invocar");
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            hostCleaner=true;
                                            SendMsg();
                                            break;
                                        case "Comercio":
                                            if (playerHost.playerData.playerHand.sizeLCDE>3&&playerInvitated.playerData.playerHand.sizeLCDE>3){
                                                playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
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
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.historial.insertLDE("Fortaleza", "Host", "Invocar");
                                            playerHost.playerData.playerHand.deleteNode("Fortaleza");
                                            hostStrength=true;
                                            SendMsg();
                                            break;
                                        case "Sacrificio":
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.historial.insertLDE("Sacrificio", "Host", "Invocar");
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            hostSacrifice=true;
                                            SendMsg();
                                            break;
                                        case "Cementerio":
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.historial.insertLDE("Cementerio", "Host", "Invocar");
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            invitatedGraveyard=true;
                                            SendMsg();
                                            break;
                                        case "Trampa temporal":
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.historial.insertLDE("Trampa temporal", "Host", "Invocar");
                                            playerHost.playerData.playerHand.deleteNode(action);
                                            hostTemporalTramp=true;
                                            SendMsg();
                                            break;
                                        case "Gas":
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.historial.insertLDE("Gas", "Host", "Invocar");
                                            hostGas=true;
                                            playerInvitated.playerData.playerHand.deleteNode("Gas");

                                            SendMsg();
                                            break;
                                        default:
                                            playerHost.playerData.mana-=playerHost.playerData.playerHand.getNode(action).fact.getManaCost();
                                            playerHost.playerData.historial.insertLDE("Trampa zombie", "Host", "Invocar");
                                            hostZombies=true;
                                            playerInvitated.playerData.playerHand.deleteNode("Trampa zombie");

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
                        for (int i=0;i<4;i++) {
                            //Get self hand card
                            if(playerHost.playerData.playerTable[i].name.equals(action)){
                                attacker=playerHost.playerData.playerTable[i];
                                i=4;
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
                        boolean attackPlayer=true;
                        for (int i=0;i<4;i++) {
                            if (i < playerInvitated.playerData.playerTable.length && playerInvitated.playerData.playerTable[i] != null) {
                                if (playerInvitated.playerData.playerTable[i].name.equals(action)) {
                                    attackPlayer=false;
                                    if (!invitatedShield) {
                                        if (hostDoubleAttack) {
                                            playerInvitated.playerData.playerTable[i].healt -= attacker.damage;
                                            playerInvitated.playerData.playerTable[i].healt -= attacker.damage;
                                            hostDoubleAttack=false;
                                        } else {
                                            playerInvitated.playerData.playerTable[i].healt -= attacker.damage;
                                        }
                                        playerInvitated.playerData.historial.insertLDE(attacker.name,"host", "atacar");
                                        playerHost.playerData.historial.insertLDE(attacker.name,"host", "atacar");
                                        //enemy down
                                        if (playerInvitated.playerData.playerTable[i].healt <= 0) {
                                            if (invitatedGraveyard && playerInvitated.playerData.playerHand.sizeLCDE < 10) {
                                                Minion card = Factory.Master().minion[0];
                                                for (int c = 0; c <= 20; c++) {
                                                    if (Factory.Master().minion[c].name.equals(action)) {
                                                        card = Factory.Master().minion[c];
                                                    }
                                                }
                                                playerInvitated.playerData.playerHand.insert(card);
                                                invitatedGraveyard = false;
                                            }
                                            playerInvitated.playerData.playerTable[i] = null;
                                            if (invitatedEyesxEyes) {
                                                for (int c = 0; c < 4; c++) {
                                                    //Get self hand card
                                                    if (playerHost.playerData.playerTable[c] != null) {
                                                        if (playerHost.playerData.playerTable[c].name.equals(attacker.name)) {
                                                            playerHost.playerData.playerTable[c] = null;
                                                            c = 4;
                                                            invitatedEyesxEyes = false;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }

                                            if (invitatedSacrifice) {
                                                if (playerInvitated.playerData.mana <= 700) {
                                                    playerInvitated.playerData.mana += 300;
                                                    invitatedSacrifice = false;
                                                } else {
                                                    playerInvitated.playerData.mana = 1000;
                                                    invitatedSacrifice = false;
                                                }
                                            }

                                        }
                                        if (invitatedCounterAttack) {
                                            for (int c = 0; c < 4; c++) {
                                                //Get self hand card
                                                if (playerHost.playerData.playerTable[c] != null) {
                                                    if (playerHost.playerData.playerTable[c].name.equals(attacker.name)) {
                                                        playerHost.playerData.playerTable[c].healt -= playerHost.playerData.playerTable[c].damage;
                                                        invitatedCounterAttack = false;
                                                        if(playerHost.playerData.playerTable[c].healt<0){
                                                            playerHost.playerData.playerTable[c]=null;
                                                        }
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        hostMaxAttack--;
                                        invitatedShield = false;
                                    }
                                    SendMsg();
                                }
                            }
                            //if enemy hand empty direct attack
                            if (attackPlayer&&i==3) {
                                playerInvitated.playerData.life -= attacker.damage;
                                playerHost.playerData.enemyLife = playerInvitated.playerData.life;
                                hostMaxAttack--;

                            SendMsg();
                            }
                        }
                    } catch (NullPointerException e) {logger.error("Not catched attacked card, or no existing card");}
                }
                //peek a card from the deck
                if (action.equals("peek")&&hostAllowPeekDeck){
                    playerHost.playerData.playerHand.insert(playerHost.playerData.playerDeck.peek());
                    playerHost.playerData.playerDeck.pop();
                    hostAllowPeekDeck=false;
                    SendMsg();
                }
            }
        }
        if(playerHost.playerData.life<0){
            try {
                playerInvitated.out.writeUTF("win");
                logger.error("sendign the win order");
            } catch (IOException e) {
                logger.error("error sendign the win order"+e);
            }
        }else{
            try {
                playerHost.out.writeUTF("win");
                logger.error("error sendign the win order");
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