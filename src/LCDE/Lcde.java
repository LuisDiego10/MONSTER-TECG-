package LCDE;
import Card.Card;

/**
 * Principal class Lcde
 * Declare the attributes and contains the methods for the circular doubly linked list(LCDE)
 * @author Diego
 * @version 1.0
 * @since 30/10/2020
 */

public class Lcde {
    public int sizeLCDE=0;
    Node start= null;
    Node end= null;

    public Lcde(){
    }

    /**
     * Constructor Lcde class
     * @param node, object that contain the data to store.
     * @author Diego
     * @version 1.0
     * @since 30/10/2020
     */


    public Lcde(Card node){
        Node newNode= new Node(node);
        start= newNode;
        end= newNode;
        sizeLCDE++;
    }

    /**
     * Class Lcde
     * Class for target the nodes
     * @author Diego
     * @version 1.0
     * @since 30/10/2020
     */

    public Lcde(Card[] nodos){
        start = new Node(nodos[0]);
        end = start;
        start.nextNode=start;
        start.prevNode=start;
        sizeLCDE++;
        for(int i=1;i<nodos.length;i++){
            insert(nodos[i]);
        }
    }

    /**
     * Class insert
     * Class for insert nodes
     * @author Diego
     * @param value
     * @version 1.0
     * @since 30/10/2020
     */

    public void insert(Card value){
        if (start == null) {
            Node newNode = new Node();
            newNode.fact = value;
            newNode.nextNode = newNode.prevNode = newNode;
            start = newNode;
            sizeLCDE++;
            return;
        }
        Node lastNode = (start).prevNode;
        Node newNode = new Node();
        newNode.fact = value;
        newNode.nextNode = start;
        (start).prevNode = newNode;
        newNode.prevNode = lastNode;
        lastNode.nextNode = newNode;
        sizeLCDE++;
    }

    /**
     * Class deleteNode
     * Class for delete nodes
     * @author Diego
     * @param valueFind, key of the data stored in the node, in this case is the card name
     * @version 1.0
     * @since 30/10/2020
     */

    public void deleteNode(String valueFind) {
        if (start == null)
            return;
        Node currentNode = start, prev1 = null;

        while (!currentNode.fact.name.equals(valueFind)) {
            if (currentNode.nextNode == start) {
                System.out.printf("\nList doesn't have node with value = %s", valueFind);
                return;
            }
            prev1 = currentNode;
            currentNode = currentNode.nextNode;
        }
        if (currentNode.nextNode == start && prev1 == null) {
            sizeLCDE--;
            return;
        }
        if (currentNode == start) {
            prev1 = (start).prevNode;
            start = (start).nextNode;
            prev1.nextNode = start;
            (start).prevNode = prev1;
            end = prev1;
        } else if (currentNode.nextNode == start) {
            prev1.nextNode = start;
            (start).prevNode = prev1;

        } else {
            Node aux = currentNode.nextNode;
            prev1.nextNode = aux;
            aux.prevNode = prev1;
        }
        sizeLCDE--;
        if(sizeLCDE<0){sizeLCDE=0;}
    }
    /**
     * Class display
     * Class for show elements in LCDE
     * @author Diego
     * @version 1.0
     * @since 30/10/2020
     */
    public void display() {
        Node aux = start;
        while (aux.nextNode != start) {
            System.out.printf("%s ", aux.fact.getName());
            aux = aux.nextNode;
        }
        System.out.printf("%s \n ", aux.fact.getName());
    }

    /**
     * Class getNode
     * Class get a specific node
     * @author Diego
     * @param factkey, key of the data stored in node, in this case is the card name
     * @version 1.0
     * @since 30/10/2020
     */
    public Node getNode(String factkey){
        if (start == null){
            System.out.print("list is empty");
            return null;}
        Node currentNode = start, prev1 = null;
        while (!currentNode.fact.getName().equals(factkey)) {
            if (currentNode.nextNode == start) {
                System.out.printf("\nList doesn't have node with value = %s", factkey);
                return start;
            }
            prev1 = currentNode;
            currentNode = currentNode.nextNode;
        }
        if (currentNode.nextNode == start && prev1 == null) {
            return start;
        }
        return currentNode;
    }
    /**
     * Test method
     * Get Start Node
     * @version 1.0
     * @since 30/10/2020
     */
    public Node getStart() {
        return start;
    }
    /**
     * Test method
     * Get End Node
     * @version 1.0
     * @since 30/10/2020
     */
    public Node getEnd() {
        return end;
    }
}