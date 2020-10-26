package ListCicularDE;

public class Main {
    public static void main(String[] args){
        ListCircularDE dato=new ListCircularDE();
        dato.insertFactEnd("Elemento1");
        dato.insertFactEnd("Elemento2");
        dato.insertFactEnd("Elemento3");
        dato.showElements();
        dato.setSizeLCDE();
        System.out.println(dato.getFact("Elemento1"));
        System.out.println(dato.getFact("Elementodf"));
    }
}
