public class Scope2 {
    public static void main(String[] args) {
        int x = 5;
        if (true) {
            x = 20;
        }
        System.out.println(x);
    }
}