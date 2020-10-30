package Pila;

import Card.Card;

/**
 * Principal StackP
 * Declare the attributes and contains the methods for the stack
 * @author Diego
 * @version 1.0
 * @since 30/10/2020
 */
public class StackP {
    Card[] arr;
    int maxSize;
    int top;
    /**
     * Constructor class StackP
     * @author Diego
     * @version 1.0
     * @since 30/10/2020
     */
    public StackP(int size){
        maxSize=size;
        arr=new Card[maxSize];
        top=0;

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
     *  ShowElements method
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
     *  Test method
     * eliminated before realease
     * @version 0.0
     * @since 30/10/2020
     */
    public Card[] getArr() {
        return arr;
    }
}