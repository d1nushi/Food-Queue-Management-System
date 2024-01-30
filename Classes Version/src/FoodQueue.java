import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class FoodQueue {
    public static FoodQueue[] queues;// Array of FoodQueue objects
    public static FoodQueue waitingListQueue; // Waiting list queue
    public static Customer[] customers; // Array to hold customers in the queue
    public int maxSize; // Maximum number of customers allowed in the queue
    public int size; // Current number of customers in the queue
    public static int stock;

    public void FoodQueue(int numQueues, int queueSize) {
        FoodQueue.queues = new FoodQueue[numQueues];
        for (int i = 0; i < numQueues; i++) {
            FoodQueue.queues[i] = new FoodQueue(queueSize);
        }

        FoodQueue.stock = 0;
        FoodQueue.waitingListQueue = new FoodQueue(queueSize);
    }

    public void FoodQueue() {
        FoodQueue.queues = new FoodQueue[3];
        FoodQueue.queues[0] = new FoodQueue(2); // Queue 1 can hold 2 customers
        FoodQueue.queues[1] = new FoodQueue(3); // Queue 2 can hold 3 customers
        FoodQueue.queues[2] = new FoodQueue(5); // Queue 3 can hold 5 customers
        FoodQueue.stock = 50;
        FoodQueue.waitingListQueue = new FoodQueue(10);
    }



    public FoodQueue(int maxSize) {
        this.maxSize = maxSize;
        customers = new Customer[maxSize];
        size = 0;

    }

    public static void viewAllQueues() {
        // Display the state of all queues
        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");

        int maxLength = 0;
        for (FoodQueue queue : FoodQueue.queues) {
            maxLength = Math.max(maxLength,queue.getMaxSize() );
        }

        // Iterate over each position in the queues and print 'O' if occupied, 'X' if not occupied
        for (int i = 0; i < maxLength; i++) {
            for (FoodQueue queue : FoodQueue.queues) {
                if (i < queue.size()) {
                    System.out.print("O ");
                } else if (i < queue.getMaxSize())  {
                    System.out.print("X ");
                } else {
                    System.out.print("  ");
                }
                System.out.print("\t\t");
            }
            System.out.println();
        }
        System.out.println("O-Occupied X-Not Occupied");
    }

    public static void viewAllEmptyQueues() {
        System.out.println("Empty Queues:");
        for (int i = 0; i < FoodQueue.queues.length; i++) {
            if (FoodQueue.queues[i].isEmpty()) {
                System.out.println("Queue " + (i + 1));
            }
        }
    }


    public static void addCustomerToQueue(int queueNumber) {
        // Add a customer to the specified queue
        if (queueNumber >= 1 && queueNumber <= 3) {
            int index = queueNumber - 1;
            if (FoodQueue.queues[index].size() < FoodQueue.queues[index].getMaxSize()) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter customer's first name: ");
                String firstName = scanner.nextLine();
                System.out.print("Enter customer's last name: ");
                String lastName = scanner.nextLine();
                System.out.print("Enter the number of burgers required: ");
                int burgersRequired = scanner.nextInt();
                scanner.nextLine();
                Customer customer = new Customer(firstName, lastName, burgersRequired);
                FoodQueue.queues[index].enqueue(customer);
                FoodQueue.stock -= Customer.burgersRequired;
                System.out.println("Customer " + customer.getFullName() + " added to queue " + queueNumber);
                if (FoodQueue.stock <= 10) {
                    System.out.println("Warning: Low stock!");
                }
            } else {
                System.out.println("Queue is full!");
                FoodQueue.addCustomerToWaitingList();
            }
        } else {
            System.out.println("Invalid queue number!");
        }
    }

    private static void addCustomerToWaitingList() {
        if (allQueuesFull()) {
            if (FoodQueue.waitingListQueue.isFull()) {
                System.out.println("Waiting list queue is full. Customer cannot be added.");
                return;
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter customer's first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter customer's last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter the number of burgers required: ");
            int burgersRequired = scanner.nextInt();
            scanner.nextLine();
            Customer customer = new Customer(firstName, lastName, burgersRequired);
            FoodQueue.waitingListQueue.enqueue(customer);
            System.out.println("Customer " + customer.getFullName() + " added to the waiting list queue.");
        } else {
            System.out.println("There are available queues. Customer can be added directly.");
        }
    }

    private static boolean allQueuesFull() {
        for (FoodQueue queue : FoodQueue.queues) {
            if (!queue.isFull()) {
                return false;
            }
        }
        return true;
    }

    public static void removeCustomerFromQueue(int queueNumber, int position) {
        // Remove a customer from the specified queue at the given position
        if (queueNumber >= 1 && queueNumber <= 3) {
            int index = queueNumber - 1;
            if (position >= 1 && position <= FoodQueue.queues[index].size()) {
                Customer removedCustomer = FoodQueue.queues[index].dequeue(position);
                System.out.println("Removed customer: " + removedCustomer.getFullName());
            } else {
                System.out.println("Invalid position!");
            }
        } else {
            System.out.println("Invalid queue number!");
        }
    }

    public static void removeServedCustomerFromQueue(int queueNumber) {
        if (queueNumber >= 1 && queueNumber <= 3) {
            int index = queueNumber - 1;
            if (!FoodQueue.queues[index].isEmpty()) {
                Customer removedCustomer = FoodQueue.queues[index].dequeue(1);
                System.out.println("Served customer removed: " + removedCustomer.getFullName());

                // Check if there are customers in the Waiting List queue
                if (!FoodQueue.waitingListQueue.isEmpty()) {
                    Customer nextCustomer = FoodQueue.waitingListQueue.dequeue(1);
                    FoodQueue.queues[index].enqueue(nextCustomer);
                    System.out.println("Next customer in the Waiting List queue added to queue " + queueNumber + ": " + nextCustomer.getFullName());
                }
            } else {
                System.out.println("Queue is empty!");
            }
        } else {
            System.out.println("Invalid queue number!");
        }
    }

    public static void viewCustomersSortedAlphabetically() {
        System.out.println("Customers Sorted Alphabetically:");
        for (FoodQueue queue : FoodQueue.queues) {
            queue.displaySortedCustomers();
        }
    }

    public void displaySortedCustomers() {
        if (!isEmpty()) {
            Customer[] sortedCustomers = new Customer[size];
            System.arraycopy(customers, 0, sortedCustomers, 0, size);
            for (int i = 0; i < size - 1; i++) {
                for (int j = 0; j < size - i - 1; j++) {
                    if (sortedCustomers[j].getFullName().compareToIgnoreCase(sortedCustomers[j + 1].getFullName()) > 0) {
                        Customer temp = sortedCustomers[j];
                        sortedCustomers[j] = sortedCustomers[j + 1];
                        sortedCustomers[j + 1] = temp;
                    }
                }
            }
            for (int i = 0; i < size; i++) {
                System.out.println(sortedCustomers[i].getFullName() + " - " + sortedCustomers[i]);
            }
        }
    }

    public static void storeDataToFile() {
        try {
            System.out.print("Enter the filename: ");
            Scanner scanner = new Scanner(System.in);
            String filename = scanner.nextLine();

            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < FoodQueue.queues.length; i++) {
                FoodQueue queue = FoodQueue.queues[i];

                // Write cashier queue data
                writer.write("Queue " + (i + 1) + " Data:\n");
                for (int j = 0; j < queue.size(); j++) {
                    Customer customer = queue.customers[j];
                    writer.write("Customer " + (j + 1) + ": " + customer.getFullName() + "\n");
                }
                writer.newLine();
            }

            // Write stock data
            writer.write("Stock: " + FoodQueue.stock);
            writer.newLine();

            writer.close();

            System.out.println("Data stored successfully to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error occurred while storing data to file: " + e.getMessage());
        }
    }
    public static void loadDataFromFile() {
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



    public static void viewRemainingBurgersStock() {
        System.out.println("Remaining Burgers Stock: " + FoodQueue.stock);
    }

    public static void addBurgersToStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of burgers to add: ");
        int burgersToAdd = scanner.nextInt();
        scanner.nextLine();
        FoodQueue.stock += burgersToAdd;
        System.out.println("Added " + burgersToAdd + " burgers to stock.");
    }

    public double calculateQueueIncome(double burgerPrice) {
        double income = 0.0;
        for (int i = 0; i < size; i++) {
            Customer customer = customers[i];
            int burgersRequired = customer.getBurgersRequired();
            double customerIncome = burgersRequired * burgerPrice;
            income += customerIncome;
        }
        return income;
    }
    public static void printQueueIncome(double burgerPrice) {
        for (int i = 0; i < FoodQueue.queues.length; i++) {
            FoodQueue queue = FoodQueue.queues[i];
            double income = queue.calculateQueueIncome(burgerPrice);
            System.out.println("Queue " + (i + 1) + " Income: $" + income);
        }
    }



    public boolean isFull() {
        return size == maxSize;
    }
    public int getMaxSize() {
        return maxSize;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Customer customer) {
        if (!isFull()) {
            for (int i = size - 1; i >= 0; i--) {
                customers[i + 1] = customers[i];
            }
            customers[0] = customer;
            size++;
        }
    }

    public Customer dequeue(int position) {
        if (!isEmpty()) {
            Customer removedCustomer = customers[position - 1];
            for (int i = position - 1; i < size - 1; i++) {
                customers[i] = customers[i + 1];
            }
            customers[size - 1] = null;
            size--;
            return removedCustomer;
        }
        return null;
    }


}
