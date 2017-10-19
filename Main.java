public class Main {
    public static void main(String args[]){
        Message m1 = new Message("John", "Hi");
        Message m2 = new Message("MArry", "Hello");
        Message m3 = new Message("John", "Bye");
        Message m4 = new Message("Marry", "Awww...");

        System.out.println(m1.toString());
        System.out.println(m2);
        System.out.println(m3);
        System.out.println(m4);
        
    }
}

}
