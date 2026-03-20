interface Account {
  void deposit(int amount);
  int getBalance();
  void showStatus();
}

class SavingsAccount implements Account {

  private String ownerName;
  private int balance;
  private int goal;

  SavingsAccount(String ownerName, int balance, int goal) {
    this.ownerName = ownerName;
    this.balance = balance;
    this.goal = goal;
  }

  public void deposit(int amount) {
    if (amount > 0) this.balance += amount;
  }

  public int getBalance() {
    return this.balance;
  }

  public void showStatus() {
    System.out.println(this.ownerName + " | 残高：" + this.balance + "円");
  }
}

class FixedDeposit implements Account {

  private String ownerName;
  private int balance;

  FixedDeposit(String ownerName, int balance) {
    this.ownerName = ownerName;
    this.balance = balance;
  }

  public void deposit(int amount) {
    System.out.println("定期預金のため追加入金不可");
  }

  public int getBalance() {
    return this.balance;
  }

  public void showStatus() {
    System.out.println(this.ownerName + " | 定期預金：" + this.balance + "円");
  }
}

public class AccountDemo {
  public static void printAll(Account[] accounts) {
    for (Account account : accounts) {
      account.showStatus();
    }
  }
  public static void main(String[] args) {
    Account[] accounts =  {
      new SavingsAccount("Hiroki", 5000, 100000),
      new FixedDeposit("Hiroki", 500000)
    };

    printAll(accounts);
  }
}