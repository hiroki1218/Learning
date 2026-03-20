public class SavingsAccount {
    String ownerName;
    int balance;
    int goal;

    SavingsAccount(String ownerName, int balance, int goal) {
        this.ownerName = ownerName;
        this.balance = balance;
        this.goal = goal;
    }

    public static void main(String[] args) {
        SavingsAccount hiroki = new SavingsAccount("Hiroki", 5000, 100000);
        System.out.println(hiroki.ownerName + ": " + hiroki.balance + "円");
    }
}