import java.io.*;
import java.util.*;

public class BankApp {
    private static final String FILE_NAME = "users.txt";
    private static Scanner scanner = new Scanner(System.in);
    private static List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        loadUsersFromFile();
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome to the Bank App!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        saveUsersToFile();
        System.out.println("Thank you for using the Bank App!");
    }

    private static void register() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        if (isUsernameTaken(username)) {
            System.out.println("Username already exists. Please choose another one.");
            return;
        }
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        User user = new User(username, password, 0);
        users.add(user);
        System.out.println("Registration successful!");
    }

    private static void login() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful!");
            loggedInMenu(user);
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void loggedInMenu(User user) {
        boolean logout = false;
        while (!logout) {
            System.out.println("Welcome, " + user.getUsername() + "!");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Send Money");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    System.out.println("Your balance: " + user.getBalance());
                    break;
                case 2:
                    System.out.println("Enter amount to deposit:");
                    double depositAmount = scanner.nextDouble();
                    user.deposit(depositAmount);
                    break;
                case 3:
                    System.out.println("Enter amount to withdraw:");
                    double withdrawAmount = scanner.nextDouble();
                    user.withdraw(withdrawAmount);
                    break;
                case 4:
                    System.out.println("Enter recipient's username:");
                    String recipientUsername = scanner.nextLine();
                    User recipient = getUserByUsername(recipientUsername);
                    if (recipient != null) {
                        System.out.println("Enter amount to send:");
                        double sendAmount = scanner.nextDouble();
                        user.sendMoney(recipient, sendAmount);
                    } else {
                        System.out.println("Recipient not found.");
                    }
                    break;
                case 5:
                    System.out.println("Enter new password:");
                    String newPassword = scanner.nextLine();
                    user.setPassword(newPassword);
                    System.out.println("Password changed successfully!");
                    break;
                case 6:
                    logout = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void loadUsersFromFile() {
        try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                String username = parts[0];
                String password = parts[1];
                double balance = Double.parseDouble(parts[2]);
                User user = new User(username, password, balance);
                users.add(user);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Users file not found. Creating new file.");
        }
    }

    private static void saveUsersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                writer.println(user.getUsername() + "," + user.getPassword() + "," + user.getBalance());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isUsernameTaken(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    private static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
