package LDE;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Principal class Lde
 * Declare the attributes and contains the methods for the doubly linked list(LDE)
 * @author Diego
 * @version 1.0
 * @since 30/10/2020
 */
public class Lde {
    public int sizeLDE=0;
    @JsonProperty("startLDE")
    public NodeLDE start= null;
    public NodeLDE lastNode= null;

    /**
     * Constructor class Lde
     * @author Diego
     * @version 1.0
     * @since 30/10/2020
     */
    public Lde(){

    }
    /**
     * Class insertLDE
     * Class for insert nodes
     * @author Diego
     * @param action
     * @param card
     * @param player
     * @version 1.0
     * @since 30/10/2020
     */

    public void insertLDE(String card,String player, String action){
        if (start == null) {
            NodeLDE newNode = new NodeLDE();
            newNode.fact = card;
            newNode.player = player;
            newNode.action = action;
            newNode.nextNodeLDE =null;
            newNode.prevNodeLDE = null;
            start = newNode;
            lastNode=newNode;
            sizeLDE++;

            return;
        }
        NodeLDE newNode = new NodeLDE();
        newNode.fact = card;
        newNode.player = player;
        newNode.action = action;
        newNode.prevNodeLDE = lastNode;
        lastNode.nextNodeLDE = newNode;
        lastNode=newNode ;
        sizeLDE++;
    }
    /**
     * Class display
     * Class for show elements in LDE
     * @author Diego
     * @version 1.0
     * @since 30/10/2020
     */
    public void displayLDE() {
        NodeLDE aux = start;
        while (aux.nextNodeLDE != start) {
            System.out.print(aux.fact);
            System.out.print(aux.player);
            System.out.print(aux.action);
            aux = aux.nextNodeLDE;
        }
        System.out.printf("%s \n ", aux.fact);
    }
    /**
     * Class getNodeLDE
     * Class get a specific node
     * @author Diego
     * @param factkey, key of the data stored in node, in this case is the card name
     * @version 1.0
     * @since 30/10/2020
     */
    public NodeLDE getNodeLDE(String factkey){
        if (start == null){
            System.out.print("list is empty");

            return null;}
        NodeLDE currentNode = start, prev1 = null;
        while (!currentNode.fact.equals(factkey)) {
            if (currentNode.nextNodeLDE == start) {
                System.out.printf("\nList doesn't have node with value = %s", factkey);
                return start;
            }
            prev1 = currentNode;
            currentNode = currentNode.nextNodeLDE;
        }
        if (currentNode.nextNodeLDE == start && prev1 == null) {
            (start) = null;
            return start;
        }
        return currentNode;
    }

    /**
     * Test method
     * Method for getStard Node LDE
     * @version 0.0
     * @since 30/10/2020
     */
    public NodeLDE getStartLDE() {
        return start;
    }
}