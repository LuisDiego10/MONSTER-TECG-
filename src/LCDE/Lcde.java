package LCDE;
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
     * Constructor class StackP
     * @param node
     * @author Diego
     * @version 1.0
     * @since 30/10/2020
     */
    public Lcde(int node){
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
    public Lcde(int[] nodos){
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
    public void insert(int value){
        if (start == null) {
            Node newNode = new Node();
            newNode.fact = value;
            newNode.nextNode = newNode.prevNode = newNode;
            start = newNode;
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
     * @param valueFind
     * @return start node
     * @version 1.0
     * @since 30/10/2020
     */
    public Node deleteNode(int valueFind) {
        if (start == null)
            return null;
        Node currentNode = start, prev1 = null;
        while (currentNode.fact != valueFind) {
            if (currentNode.nextNode == start) {
                System.out.printf("\nList doesn't have node with value = %d", valueFind);
                return start;
            }
            prev1 = currentNode;
            currentNode = currentNode.nextNode;
        }
        if (currentNode.nextNode == start && prev1 == null) {
            (start) = null;
            return start;
        }
        if (currentNode == start) {
            prev1 = (start).prevNode;
            start = (start).nextNode;
            prev1.nextNode = start;
            (start).prevNode = prev1;
        } else if (currentNode.nextNode == start) {
            prev1.nextNode = start;
            (start).prevNode = prev1;
        } else {
            Node aux = currentNode.nextNode;
            prev1.nextNode = aux;
            aux.prevNode = prev1;
        }
        return start;
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
            System.out.printf("%d ", aux.fact);
            aux = aux.nextNode;
        }
        System.out.printf("%d ", aux.fact);
    }
    /**
     * Class getNode
     * Class get a specific node
     * @author Diego
     * @param factkey
     * @version 1.0
     * @since 30/10/2020
     */
    public Node getNode(int factkey){
        if (start == null){
            System.out.print("list is empty");

            return null;}
        Node currentNode = start, prev1 = null;
        while (currentNode.fact != factkey) {
            if (currentNode.nextNode == start) {
                System.out.printf("\nList doesn't have node with value = %d", factkey);
                return start;
            }
            prev1 = currentNode;
            currentNode = currentNode.nextNode;
        }
        if (currentNode.nextNode == start && prev1 == null) {
            (start) = null;
            return start;
        }
        return currentNode;
    }
}