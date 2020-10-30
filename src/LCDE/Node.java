package LCDE;

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
