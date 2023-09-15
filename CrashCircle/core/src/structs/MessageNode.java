package structs;

public class MessageNode {
    public String stringMessage;
    public int intMessage;
    public float floatMessage;
    public MessageNode next;
    public boolean has_next;

    public MessageNode(){
        this.has_next=false;
    }
    public MessageNode(MessageNode next){
        this.next=next;
        this.has_next=true;
    }

    public MessageNode(String stringMessage){
        this.stringMessage=stringMessage;
        this.has_next=false;
    }
    public MessageNode(String stringMessage,MessageNode next){
        this.stringMessage=stringMessage;
        this.next=next;
        this.has_next=true;
    }
    public MessageNode(String stringMessage,int intMessage){
        this.stringMessage=stringMessage;
        this.intMessage=intMessage;
        this.has_next=false;
    }
    public MessageNode(String stringMessage,int intMessage,MessageNode next){
        this.stringMessage=stringMessage;
        this.intMessage=intMessage;
        this.next=next;
        this.has_next=true;
    }

    public void put(MessageNode next){
        this.has_next=true;
        this.next=next;
    }
    public void print(){
        System.out.print("\t||\tstring: "+stringMessage+" ;\tint: "+intMessage);
        if (has_next){
            this.next.print();
        }
    }

}
