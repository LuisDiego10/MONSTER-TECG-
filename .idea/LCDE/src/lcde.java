class lcde{
    static class Node{
        int fact;
        Node nextNode;
        Node prevNode;
    };
    static Node insert(Node start, int value) {
        if (start == null) {
            Node newNode = new Node();
            newNode.fact = value;
            newNode.nextNode = newNode.prevNode = newNode;
            start = newNode;
            return start;
        }
        Node lastNode=(start).prevNode;
        Node newNode = new Node();
        newNode.fact=value;
        newNode.nextNode=start;
        (start).prevNode=newNode;
        newNode.prevNode=lastNode;
        lastNode.nextNode=newNode;
        return start;
    }
    static Node deleteNode(Node start,int valueFind){
        if (start==null)
            return null;
        Node currentNode=start , prev1=null;
        while (currentNode.fact!=valueFind){
            if (currentNode.nextNode==start){
                System.out.printf("\nList doesn't have node with value = %d", valueFind);
                return start;
            }
            prev1=currentNode;
            currentNode=currentNode.nextNode;
        }
        if (currentNode.nextNode==start&&prev1==null){
            (start)=null;
            return start;
        }
        if (currentNode==start){
            prev1=(start).prevNode;
            start=(start).nextNode;
            prev1.nextNode=start;
            (start).prevNode=prev1;
        }
        else if (currentNode.nextNode==start){
            prev1.nextNode=start;
            (start).prevNode=prev1;
        }
        else{
            Node aux=currentNode.nextNode;
            prev1.nextNode=aux;
            aux.prevNode=prev1;
        }
        return start;
    }
    static void display(Node start)
        {
            Node aux = start;
            while (aux.nextNode != start) {
                System.out.printf("%d ", aux.fact);
                aux = aux.nextNode;
            }
            System.out.printf("%d ", aux.fact);
        }
        public static void main(String args[])
        {
            // Start with the empty list
            Node start = null;

            // Created linked list will be 4.5.6.7.8
            start = insert(start, 4);
            start = insert(start, 5);
            start = insert(start, 61);
            start = insert(start, 7);
            start = insert(start, 8);

            System.out.printf("List Before Deletion: ");
            display(start);

            // Delete the node which is not present in list
            start = deleteNode(start, 61);
            System.out.printf("\nList After Deletion: ");
            display(start);

            // Delete the first node
            start = deleteNode(start, 4);
            System.out.printf("\nList After Deleting %d: ", 4);
            display(start);

            // Delete the last node
            start = deleteNode(start, 8);
            System.out.printf("\nList After Deleting %d: ", 8);
            display(start);

            // Delete the middle node
            start = deleteNode(start, 6);
            System.out.printf("\nList After Deleting %d: ", 6);
            display(start);
        }
    }