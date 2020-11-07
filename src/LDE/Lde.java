package LDE;
public class Lde {
    public int sizeLDE=0;
    NodeLDE start= null;
    /**
     * Constructor class StackP
     * @author Diego
     * @version 1.0
     * @since 30/10/2020
     */
    public Lde(){
        NodeLDE newNode= new NodeLDE();
        start= newNode;
        sizeLDE++;
    }
    /**
     * Class insert
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
            newNode.nextNodeLDE = newNode.prevNodeLDE = newNode;
            start = newNode;
            return;
        }
        NodeLDE lastNode = (start).prevNodeLDE;
        NodeLDE newNode = new NodeLDE();
        newNode.fact = card;
        newNode.player = player;
        newNode.action = action;
        newNode.nextNodeLDE = start;
        (start).prevNodeLDE = newNode;
        newNode.prevNodeLDE = lastNode;
        lastNode.nextNodeLDE = newNode;
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
            aux = aux.nextNodeLDE;
        }
        System.out.printf("%s \n ", aux.fact);
    }
    /**
     * Class getNode
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
     * deleted before release
     * @version 0.0
     * @since 30/10/2020
     */
    public NodeLDE getStartLDE() {
        return start;
    }
}