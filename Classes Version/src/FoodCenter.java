import java.util.Scanner;

public class FoodCenter {
    public FoodCenter(int numQueues, int queueSize) {
        FoodQueue.queues = new FoodQueue[numQueues];
        for (int i = 0; i < numQueues; i++) {
            FoodQueue.queues[i] = new FoodQueue(queueSize);
        }

        FoodQueue.stock = 0;
        FoodQueue.waitingListQueue = new FoodQueue(queueSize);
    }

    public FoodCenter() {
        FoodQueue.queues = new FoodQueue[3];
        FoodQueue.queues[0] = new FoodQueue(2); // Queue 1 can hold 2 customers
        FoodQueue.queues[1] = new FoodQueue(3); // Queue 2 can hold 3 customers
        FoodQueue.queues[2] = new FoodQueue(5); // Queue 3 can hold 5 customers
        FoodQueue.stock = 50;
        FoodQueue.waitingListQueue = new FoodQueue(10);
    }


    public static void main(String[] args) {
        FoodCenter foodCenter = new FoodCenter();
        Scanner scanner = new Scanner(System.in);
        double burgerPrice = 650.0;

        while (true) {
            Customer.displayMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "100":
                case "VFQ":
                    FoodQueue.viewAllQueues();
                    break;
                case "101":
                case "VEQ":
                    FoodQueue.viewAllEmptyQueues();
                    break;
                case "102":
                case "ACQ":
                    System.out.print("Enter the queue number (1-3): ");
                    int queueNumber = scanner.nextInt();
                    scanner.nextLine();
                    FoodQueue.addCustomerToQueue(queueNumber);
                    break;
                case "103":
                case "RCQ":
                    System.out.print("Enter the queue number (1-3): ");
                    queueNumber = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter the position: ");
                    int position = scanner.nextInt();
                    scanner.nextLine();
                    FoodQueue.removeCustomerFromQueue(queueNumber, position);
                    break;
                case "104":
                case "PCQ":
                    System.out.print("Enter the queue number (1-3): ");
                    queueNumber = scanner.nextInt();
                    scanner.nextLine();
                    FoodQueue.removeServedCustomerFromQueue(queueNumber);
                    break;
                case "105":
                case "VCS":
                    FoodQueue.viewCustomersSortedAlphabetically();
                    break;
                case "106":
                case "SPD":
                    FoodQueue.storeDataToFile();
                    break;
                case "107":
                case "LPD":
                    FoodQueue.loadDataFromFile();
                    break;
                case "108":
                case "STK":
                    FoodQueue.viewRemainingBurgersStock();
                    break;
                case "109":
                case "AFS":
                    FoodQueue.addBurgersToStock();
                    break;
                case "110":
                case "IFQ":
                    FoodQueue.printQueueIncome(650.0);
                    break;
                case "112":
                case "GUI":
                    //HelloApplication.launch(HelloApplication.class, args);
                    break;
                case "999":
                case "EXT":
                    System.out.println("Exiting the program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}