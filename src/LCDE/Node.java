package LCDE;
import Card.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * Class Node
 * Declare the attributes for Node and constructor
 * @author Diego
 * @version 1.0
 * @since 30/10/2020
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Node{
    public Card fact;
    public Node nextNode;
    public Node prevNode;

    public Node(Card value){
        fact= value;
    }
    public Node(){

    }
    public Card getFact(){
        return fact;
    }

}
