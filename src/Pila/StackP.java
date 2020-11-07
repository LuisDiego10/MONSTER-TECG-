package Pila;

import Card.Card;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Principal StackP
 * Declare the attributes and contains the methods for the stack
 * @author Diego
 * @version 1.0
 * @since 30/10/2020
 */
public class StackP {
    @JsonProperty("maxSize")
    int maxSize;
    @JsonProperty("top")
    int top;
    @JsonProperty("arr")
    Card[] arr;
    /**
     * Constructor class StackP
     * @author Diego
     * @version 1.0
     * @since 30/10/2020
     */
    public StackP(int size){
        maxSize=size;
        top=0;
        arr=new Card[maxSize];

    }

    /**
     * Constructor class StackP
     * @author Isaac
     * @version 1.0
     * @since 3/11/2020
     */
    public StackP(@JsonProperty("maxSize")int size, @JsonProperty("top")int topValue, @JsonProperty("arr")Card[] deck){
        maxSize=size;
        top=topValue;
        arr=deck;

    }

    /**
     * Boolean method
     * Return if class is empty
     * @author Diego
     * @return boolean
     * @version 1.0
     * @since 30/10/2020
     */
    public boolean empty(){
        return (top == 0);
    }
    /**
     * Push method
     * Add an card to the stack
     * @author Diego
     * @return boolean
     * @version 1.0
     * @since 30/10/2020
     */
    public void push(Card card){
        if (top<maxSize){
            arr[top]=card;
            top++;
        }
    }
    /**
     * Peek method
     * Return last card
     * @author Diego
     * @return last card
     * @version 1.0
     * @since 30/10/2020
     */
    public Card peek(){
        if (top>0){
            return arr[top-1];
        }else {
            System.out.println("The stack don't containt cards");
            return null;
        }
    }
    /**
     * Pop method
     * Return and eliminate last card to the stack
     * @author Diego
     * @return temp
     * @version 1.0
     * @since 30/10/2020
     */
    public Card pop(){
        Card temp = null;
        if (top>0){
            temp=arr[top-1];
            arr[top-1]=null;
            top--;
        }
        return temp;
    }
    /**
     * ShowElements method
     * Show all elements stack
     * @author Diego
     * @return message
     * @version 1.0
     * @since 30/10/2020
     */
    public String showElements() {
        String message="These are the elements that the stack contains";
        if(arr.length!=0){
            for (int i = 0; i < arr.length; i++) {
                System.out.println(arr[i]);
            }}
    return message;
    }

    /**
     * Test method
     * Method for get Array Card
     * @version 1.0
     * @since 30/10/2020
     */
    public Card[] getArr() {
        return arr;
    }
}