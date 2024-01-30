import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FoodCenter {
    private String[][] cashiers;// Array to store customers in each queue
    private int[] queueSizes;// Array to store the size of each queue
    private int stock;// Number of burgers in stock

    public FoodCenter() {
        cashiers = new String[][] { new String[2], new String[3], new String[5] };
        queueSizes = new int[] { 0, 0, 0 };
        stock = 50;
    }

    public void displayMenu() {
        // Display the menu options
        System.out.println(" ");
        System.out.println("-------Foodies Fave Food center-------");
        System.out.println("Menu Options:");
        System.out.println("100 or VFQ: View all Queues.");
        System.out.println("101 or VEQ: View all Empty Queues.");
        System.out.println("102 or ACQ: Add customer to a Queue.");
        System.out.println("103 or RCQ: Remove a customer from a Queue.");
        System.out.println("104 or PCQ: Remove a served customer.");
        System.out.println("105 or VCS: View customers sorted in alphabetical order.");
        System.out.println("106 or SPD: Store Program Data into file.");
        System.out.println("107 or LPD: Load Program Data from file.");
        System.out.println("108 or STK: View Remaining Burgers Stock.");
        System.out.println("109 or AFS: Add Burgers to Stock.");
        System.out.println("999 or EXT: Exit the Program.");
    }

    public void viewAllQueues() {
        // Display the state of all queues
        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");

        int maxLength = 0;
        for (String[] cashier : cashiers) {
            maxLength = Math.max(maxLength, cashier.length);
        }

        // Iterate over each position in the queues and print 'O' if occupied, 'X' if not occupied
        for (int i = 0; i < maxLength; i++) {
            for (String[] cashier : cashiers) {
                if (i < cashier.length) {
                    System.out.print(cashier[i] != null ? "O " : "X ");
                } else {
                    System.out.print("  ");
                }
                System.out.print("\t\t");
            }
            System.out.println();
        }
        System.out.println("O-Occupied X-Not Occupied");
    }

    public void viewAllEmptyQueues() {
        System.out.println("Empty Queues:");
        for (int i = 0; i < cashiers.length; i++) {
            if (queueSizes[i] == 0 || queueSizes[i]< cashiers.length) {
                System.out.println("Cashier " + (i + 1));
            }
        }
    }

    public void addCustomerToQueue(int queueNumber) {
        // Add a customer to the specified queue
        if (queueNumber >= 1 && queueNumber <= 3) {
            int index = queueNumber - 1;
            if (queueSizes[index] < cashiers[index].length) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter customer name: ");
                String customerName = scanner.nextLine();
                cashiers[index][queueSizes[index]] = customerName;
                queueSizes[index]++;
                stock -= 5;
                System.out.println("Customer " + customerName + " added to queue " + queueNumber);
                if (stock <= 10) {
                    System.out.println("Warning: Low stock!");
                }
            } else {
                System.out.println("Queue is full!");
            }
        } else {
            System.out.println("Invalid queue number!");
        }
    }

    public void removeCustomerFromQueue(int queueNumber, int position) {
        // Remove a customer from the specified queue at the given position
        if (queueNumber >= 1 && queueNumber <= 3) {
            int index = queueNumber - 1;
            if (position >= 1 && position <= queueSizes[index]) {
                String removedCustomer = cashiers[index][position - 1];
                cashiers[index][queueSizes[index] - 1] = null;
                queueSizes[index]--;
                System.out.println("Removed customer: " + removedCustomer);

            } else {
                System.out.println("Invalid position!");
            }
        } else {
            System.out.println("Invalid queue number!");
        }
    }

    public void removeServedCustomerFromQueue(int queueNumber) {
        // Remove the first customer from the specified queue (served customer)
        if (queueNumber >= 1 && queueNumber <= 3) {
            int index = queueNumber - 1;
            if (queueSizes[index] > 0) {
                String removedCustomer = cashiers[index][0];
                for (int i = 0; i < queueSizes[index] - 1; i++) {
                    cashiers[index][i] = cashiers[index][i + 1];
                }
                cashiers[index][queueSizes[index] - 1] = null;
                queueSizes[index]--;
                System.out.println("Removed served customer: " + removedCustomer);
            } else {
                System.out.println("Queue is empty!");
            }
        } else {
            System.out.println("Invalid queue number!");
        }
    }

    public void viewCustomersSortedAlphabetically() {
        // View all customers in alphabetical order
        System.out.println("Customers Sorted in Alphabetical Order:");
        String[] allCustomers = getAllCustomers();
        sortCustomersAlphabetically(allCustomers);

        for (String customer : allCustomers) {
            System.out.println(customer);
        }
    }

    private String[] getAllCustomers() {
        // Retrieve all customers from the queues and return as an array
        int totalCustomers = 0;
        for (int size : queueSizes) {
            totalCustomers += size;
        }

        String[] allCustomers = new String[totalCustomers];
        int index = 0;
        for (String[] cashier : cashiers) {
            for (String customer : cashier) {
                if (customer != null) {
                    allCustomers[index] = customer;
                    index++;
                }
            }
        }

        return allCustomers;
    }

    private void sortCustomersAlphabetically(String[] customers) {
        // Sort the customers array in alphabetical order using bubble sort algorithm
        int n = customers.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (customers[j].compareToIgnoreCase(customers[j + 1]) > 0) {
                    String temp = customers[j];
                    customers[j] = customers[j + 1];
                    customers[j + 1] = temp;
                }
            }
        }
    }

    public void storeDataToFile() {
        try {
            System.out.print("Enter the filename: ");
            Scanner scanner = new Scanner(System.in);
            String filename = scanner.nextLine();

            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (String[] cashier : cashiers) {
                for (String customer : cashier) {
                    if (customer != null) {
                        // Write cashier queue data
                        writer.write("Cashier Queue Data:\n");
                        for (int i = 0; i < cashiers.length; i++) {
                            writer.write("Cashier " + (i + 1) + ": ");
                            for (int j = 0; j < cashiers[i].length; j++) {
                                writer.write(cashiers[i][j] != null ? cashiers[i][j] : "-");
                                if (j < cashiers[i].length - 1) {
                                    writer.write(", ");
                                }
                            }
                            writer.write("\n");
                        }
                        // Write stock data
                        writer.write("\nStock: " + stock);
                        writer.newLine();
                    }
                }
            }
            writer.close();

            System.out.println("Data stored successfully to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error occurred while storing data to file: " + e.getMessage());
        }
    }

    public void loadDataFromFile() {
        try {
            System.out.print("Enter the filename: ");
            Scanner scanner = new Scanner(System.in);
            String filename = scanner.nextLine();

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Cashier Queue Data:")) {
                    System.out.println(line);
                    while ((line = reader.readLine()) != null && !line.isEmpty()) {
                        System.out.println(line);
                    }
                } else if (line.startsWith("Stock: ")) {
                    System.out.println(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error occurred while loading data from file: " + e.getMessage());
        }
    }

    public void viewRemainingBurgersStock() {
        System.out.println("Remaining Burgers Stock: " + stock);
    }
    public void addBurgersToStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of burgers to add: ");
        int burgersToAdd = scanner.nextInt();
        scanner.nextLine();
        stock += burgersToAdd;
        System.out.println("Added " + burgersToAdd + " burgers to stock.");
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FoodCenter foodCenter = new FoodCenter();

        while (true) {
            foodCenter.displayMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "100":
                case "VFQ":
                    foodCenter.viewAllQueues();
                    break;
                case "101":
                case "VEQ":
                    foodCenter.viewAllEmptyQueues();
                    break;
                case "102":
                case "ACQ":
                    System.out.print("Enter the queue number: ");
                    int queueNumber = scanner.nextInt();
                    scanner.nextLine();
                    foodCenter.addCustomerToQueue(queueNumber);
                    break;
                case "103":
                case "RCQ":
                    System.out.print("Enter the queue number: ");
                    queueNumber = scanner.nextInt();
                    System.out.print("Enter the position: ");
                    int position = scanner.nextInt();
                    scanner.nextLine();
                    foodCenter.removeCustomerFromQueue(queueNumber, position);
                    break;
                case "104":
                case "PCQ":
                    System.out.print("Enter the queue number: ");
                    queueNumber = scanner.nextInt();
                    scanner.nextLine();
                    foodCenter.removeServedCustomerFromQueue(queueNumber);
                    break;
                case "105":
                case "VCS":
                    foodCenter.viewCustomersSortedAlphabetically();
                    break;
                case "106":
                case "SPD":
                    foodCenter.storeDataToFile();
                    break;
                case "107":
                case "LPD":
                    foodCenter.loadDataFromFile();
                    break;
                case "108":
                case "STK":
                    foodCenter.viewRemainingBurgersStock();
                    break;
                case "109":
                case "AFS":
                    foodCenter.addBurgersToStock();
                    break;
                case "999":
                case "EXT":
                    System.out.println("Exiting the program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
}
