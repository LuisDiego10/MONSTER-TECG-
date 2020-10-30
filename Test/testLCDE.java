import Card.Card;
import Factory.Factory;
import LCDE.Lcde;
import Pila.StackP;

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
        for (Card card:Factory.RandomDeck().getArr()){
            listaDos.insert(card);
        }

        listaDos.deleteNode("PoderSupremo");
        System.out.print("\n List1 Before Deletion: PoderSupremo" );
        listaDos.display();


        // Delete the node which is not present in list
        listaDos.deleteNode("alcitran");
        System.out.print("\nList After Deletion alcitran: ");
        listaDos.display();

        // Delete the first node
        listaDos.deleteNode(listaDos.getStart().fact.name);
        System.out.print("\nList After Deleting the start: ");
        listaDos.display();

        // Delete the last node
        listaDos.deleteNode(listaDos.getEnd().fact.name);
        System.out.printf("\nList After Deleting %d: ", 8);
        listaDos.display();


        //get a node

        System.out.printf("\nfound node: PoderSupremo or Duplicar");
        listaDos.getNode("PoderSupremo");
        listaDos.getNode("Duplicar");


        StackP pila = Factory.RandomDeck();
        pila.peek();
        pila.peek();
        pila.peek();
        pila.peek();
        pila.push(new Card());
        pila.push(new Card());
        pila.push(new Card());
        pila.push(new Card());
        pila.push(new Card());
        System.out.println(pila.showElements());
        System.out.println(pila.peek());
        System.out.println(pila.empty());
        System.out.println(pila.pop());
        System.out.println(pila.peek());
        System.out.println(pila.showElements());
    }

}


