package Pila;

/**
 * Principal StackP
 * Declare the attributes and contains the methods for the stack
 * @author Diego
 * @version 1.0
 * @since 30/10/2020
 */
public class StackP {
    String arr[];
    int maxSize;
    int top;
    /**
     * Constructor class StackP
     * @author Diego
     * @version 1.0
     * @since 30/10/2020
     */
    public StackP(){
        maxSize=16;
        arr=new String[maxSize];
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
        if (top==0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * Push method
     * Add an card to the stack
     * @author Diego
     * @return boolean
     * @version 1.0
     * @since 30/10/2020
     */
    public void push(String card){
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
    public String peek(){
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
    public String pop(){
        String temp = null;
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
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    return message;
    }
}