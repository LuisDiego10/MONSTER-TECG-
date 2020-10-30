package ListCicularDE;

public class Node {
    String fact;
    Node nextNode;
    Node prevNode;
    public Node(String fact){
        this.fact=fact;
        nextNode =null;
        prevNode =null;
    }

}
