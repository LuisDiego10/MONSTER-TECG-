import ListCicularDE.*;
import Pila.StackP;

import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Stack;

public class Main {
    public static void main(String[] args){
        ListCircularDE dato= new ListCircularDE();
        dato.insertFactEnd("Elemento1");
        dato.insertFactEnd("Elemento2");
        dato.insertFactEnd("Elemento3");
        dato.showElements();
        dato.getSizeLCDE();
        System.out.println(dato.getFact("Elementon"));
        System.out.println(dato.getFact("Elementodf"));

        StackP pila =new StackP();
        pila.push("HOLA");
        System.out.println(pila.peek());
        System.out.println(pila.empty());
        System.out.println(pila.pop());
        System.out.println(pila.empty());

    }
}
