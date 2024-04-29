public class User {
    private String username;
    private String password;
    private double balance;

    public User(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void deposit(double amount) {
        if(amount <= 0 ){
            System.out.println("Invalid amount.");
        }
        else if(amount % 100 != 0){
            System.out.println("Amount must be a multiple of 100.");
        }
        else{
            balance += amount;
            System.out.println("Deposit successful. New balance: " + balance);
        }
    }

    public void withdraw(double amount) {
        if(amount <= 0 || amount > balance){
            System.out.println("Insufficient funds or invalid amount.");
        }
        else if(amount % 100 != 0){
            System.out.println("Amount must be a multiple of 100.");
        }
        else{
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: " + balance);
        }
    }

    public void sendMoney(User recipient, double amount) {
        if(amount <= 0 || amount > balance){
            System.out.println("Insufficient funds or invalid amount.");
        }
        else if(amount % 100 != 0){
            System.out.println("Amount must be a multiple of 100.");
        }
        else{
            balance -= amount;
            recipient.deposit(amount);
            System.out.println("Money sent successfully.");
        }
    }
}
