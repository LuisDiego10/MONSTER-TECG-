package LCDE;
/**
 * Class Node
 * Declare the attributes for Node and constructor
 * @author Diego
 * @version 1.0
 * @since 30/10/2020
 */
public class Node{
    public int fact;
    public Node nextNode;
    public Node prevNode;
    public Node(int value){
        fact= value;
    }
    public Node(){

    }
    public int getFact(){
        return fact;
    }
}
