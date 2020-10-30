import LCDE.Lcde;
import Pila.StackP;

import javax.swing.*;
import java.util.ArrayList;
/**
 * Class test
 * Class used to perform the tests
 * @author Diego
 * @version 1.0
 * @since 30/10/2020
 */
public class testLCDE {

    public static void main(String[] args){
        // Start with the empty list
        Lcde listaDos = new Lcde();
        // Created linked list will be 4.5.6.7.8
        listaDos.insert(4);
        listaDos.insert(5);
        listaDos.insert(6);
        listaDos.insert(7);
        listaDos.insert(8);

        System.out.print("\n List1 Before Deletion: ");
        listaDos.display();

        System.out.print("\n List2 Before Deletion: ");
        listaDos.display();


        // Delete the node which is not present in list
        listaDos.deleteNode( 61);
        System.out.print("\nList After Deletion: ");
        listaDos.display();

        // Delete the first node
        listaDos.deleteNode( 4);
        System.out.printf("\nList After Deleting %d: ", 4);
        listaDos.display();

        // Delete the last node
        listaDos.deleteNode( 8);
        System.out.printf("\nList After Deleting %d: ", 8);
        listaDos.display();

        // Delete the middle node
        listaDos.deleteNode( 6);
        System.out.printf("\nList After Deleting %d: ", 6);
        listaDos.display();

        //get a node
        System.out.printf("\n previous node value of %d",listaDos.getNode(5).prevNode.getFact());
        StackP pila =new StackP();
        pila.push("1");
        pila.push("2");
        pila.push("3");
        pila.push("4");
        pila.push("5");
        System.out.println(pila.peek());
        System.out.println(pila.empty());
        System.out.println(pila.pop());
        System.out.println(pila.peek());
        System.out.println(pila.showElements());
    }

}


