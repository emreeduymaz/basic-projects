import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Client> clients = loadData("clients.dat");
    private static List<Account> accounts = loadData("accounts.dat");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create Client");
            System.out.println("2. Create Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. View Account");
            System.out.println("7. View Transactions");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createClient(scanner);
                    break;
                case 2:
                    createAccount(scanner);
                    break;
                case 3:
                    deposit(scanner);
                    break;
                case 4:
                    withdraw(scanner);
                    break;
                case 5:
                    transfer(scanner);
                    break;
                case 6:
                    viewAccount(scanner);
                    break;
                case 7:
                    viewTransactions(scanner);
                    break;
                case 8:
                    saveData("clients.dat", clients);
                    saveData("accounts.dat", accounts);
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void createClient(Scanner scanner) {
        int clientId = clients.size() + 1;
        System.out.print("Enter PESEL number: ");
        String pesel = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter country: ");
        String country = scanner.nextLine();
        System.out.print("Enter city: ");
        String city = scanner.nextLine();
        System.out.print("Enter street: ");
        String street = scanner.nextLine();

        Client client = new Client(clientId, pesel, name, surname, country, city, street);
        clients.add(client);
        saveData("clients.dat", clients);
    }

    private static void createAccount(Scanner scanner) {
        System.out.print("Enter client ID: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();
        Client client = clients.stream().filter(c -> c.getClientId() == clientId).findFirst().orElse(null);
        if (client != null) {
            Account account = new Account(client);
            accounts.add(account);
            saveData("accounts.dat", accounts);
        } else {
            System.out.println("Client not found.");
        }
    }

    private static void deposit(Scanner scanner) {
        System.out.print("Enter client ID: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();
        Account account = accounts.stream().filter(a -> a.getClient().getClientId() == clientId).findFirst().orElse(null);
        if (account != null) {
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            account.setBalance(account.getBalance() + amount);
            Transaction transaction = new Transaction("deposit", amount, new Date(), null, null);
            account.addTransaction(transaction);
            saveData("accounts.dat", accounts);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdraw(Scanner scanner) {
        System.out.print("Enter client ID: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();
        Account account = accounts.stream().filter(a -> a.getClient().getClientId() == clientId).findFirst().orElse(null);
        if (account != null) {
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            if (amount > account.getBalance()) {
                System.out.println("Insufficient funds.");
                return;
            }
            account.setBalance(account.getBalance() - amount);
            Transaction transaction = new Transaction("withdrawal", amount, new Date(), null, null);
            account.addTransaction(transaction);
            saveData("accounts.dat", accounts);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void transfer(Scanner scanner) {
        System.out.print("Enter your client ID: ");
        int fromClientId = scanner.nextInt();
        scanner.nextLine();
        Account fromAccount = accounts.stream().filter(a -> a.getClient().getClientId() == fromClientId).findFirst().orElse(null);
        if (fromAccount != null) {
            System.out.print("Enter recipient client ID: ");
            int toClientId = scanner.nextInt();
            scanner.nextLine();
            Account toAccount = accounts.stream().filter(a -> a.getClient().getClientId() == toClientId).findFirst().orElse(null);
            if (toAccount != null) {
                System.out.print("Enter amount to transfer: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();
                if (amount > fromAccount.getBalance()) {
                    System.out.println("Insufficient funds.");
                    return;
                }
                fromAccount.setBalance(fromAccount.getBalance() - amount);
                toAccount.setBalance(toAccount.getBalance() + amount);
                Transaction transaction = new Transaction("transfer", amount, new Date(), fromClientId, toClientId);
                fromAccount.addTransaction(transaction);
                toAccount.addTransaction(transaction);
                saveData("accounts.dat", accounts);
            } else {
                System.out.println("Recipient account not found.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void viewAccount(Scanner scanner) {
        System.out.print("Enter client ID: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();
        Account account = accounts.stream().filter(a -> a.getClient().getClientId() == clientId).findFirst().orElse(null);
        if (account != null) {
            System.out.println("Account holder: " + account.getClient().getName() + " " + account.getClient().getSurname());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("Transactions:");
            for (Transaction transaction : account.getTransactions()) {
                System.out.println(transaction.getDate() + " - " + transaction.getTransType() + " - " + transaction.getAmount());
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void viewTransactions(Scanner scanner) {
        System.out.print("Enter client ID: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();
        Account account = accounts.stream().filter(a -> a.getClient().getClientId() == clientId).findFirst().orElse(null);
        if (account != null) {
            System.out.print("Enter start date (YYYY-MM-DD): ");
            String startDateStr = scanner.nextLine();
            System.out.print("Enter end date (YYYY-MM-DD): ");
            String endDateStr = scanner.nextLine();
            try {
                Date startDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
                Date endDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
                for (Transaction transaction : account.getTransactions()) {
                    if (!transaction.getDate().before(startDate) && !transaction.getDate().after(endDate)) {
                        System.out.println(transaction.getDate() + " - " + transaction.getTransType() + " - " + transaction.getAmount());
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid date format.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> loadData(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private static <T> void saveData(String filename, List<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
