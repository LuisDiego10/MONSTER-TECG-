package ListCicularDE;

public class ListCircularDE {
    //Properties
    int sizeLCDE=0;
    Node start;
    Node end;
    //Method to compare if the list is empty
    public boolean noContent(){
        return (sizeLCDE==0);
    }
    //Method insert facts start list
    public void insertFactStart(String fact){
        Node newNode= new Node(fact);
        if (noContent()){
            start=newNode;
            end=newNode;
            start.nextNode=end;
            end.nextNode=start;
            start.prevNode=end;
            end.prevNode=start;
            sizeLCDE++;
        } else {
            Node aux=start;
            newNode.nextNode=aux;
            aux.prevNode=newNode;
            start=newNode;
            sizeLCDE++;
        }
    }
    //Method insert facts end list
    public void insertFactEnd(String fact){
        Node newNode= new Node(fact);
            if (noContent()){
                start=newNode;
                end=newNode;
                start.nextNode=end;
                end.nextNode=start;
                start.prevNode=end;
                end.prevNode=start;
                sizeLCDE++;
            }else{
                Node aux=end;
                aux.nextNode=newNode;
                aux.prevNode=newNode;
                end=newNode;
                sizeLCDE++;
        }
    }
    //Method to observe size of LCDE
    public void setSizeLCDE(){
        System.out.println("The size of LCDE is: "+ sizeLCDE);
    }
    //Method to show elements of LCDE
    public void showElements(){
        if(!noContent()){
            Node aux =start;
            for (int i=0;i <sizeLCDE;i++){
                System.out.println(aux.fact+"  ");
                aux=aux.nextNode;
            }
        }else {
            System.out.println("The LCDE no content elements");
        }
    }
    public String getFact(String fact2){
        String found = "Not found";
        if(!noContent()){
            Node aux =start;
            for (int i=0;i <sizeLCDE;i++){
                if (fact2.equals(aux.fact)){
                    found=fact2;
                    break;
                }
                aux=aux.nextNode;
            }
        }
        return found;
    }
}
