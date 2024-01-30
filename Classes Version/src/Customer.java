class Customer {
    private String firstName;
    private String lastName;
    public static int burgersRequired;

    public Customer(String firstName, String lastName, int burgersRequired) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.burgersRequired = burgersRequired;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getBurgersRequired() {
        return burgersRequired;
    }

    public static void displayMenu() {
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
        System.out.println("110 or IFQ: Print income of each queue.");
        System.out.println("112 or GUI: View status of the queues.");
        System.out.println("999 or EXT: Exit the Program.");
    }
}
