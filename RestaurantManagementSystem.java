import java.util.*;

class Restaurant {
    private String name;
    private List<Table> tables;
    private List<Order> orders;
    private List<CustomerFeedback> feedbacks;

    public Restaurant(String name) {
        this.name = name;
        this.tables = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void addFeedback(CustomerFeedback feedback) {
        feedbacks.add(feedback);
    }

    public List<Table> getTables() {
        return tables;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<CustomerFeedback> getFeedbacks() {
        return feedbacks;
    }

    public void displayAllInfo() {
        System.out.println("Restaurant: " + name);
        displayTables();
        displayOrders();
        displayFeedbacks();
    }

    public void displayTables() {
        System.out.println("Tables:");
        for (Table table : tables) {
            System.out.println("Table " + table.getTableNumber() + ": " + table.getStatus());
        }
    }

    public void displayOrders() {
        System.out.println("Orders:");
        for (Order order : orders) {
            System.out.printf("Order ID: %d, Customer: %s, Items: %s, Total Amount: ₹%.2f (Discounted: ₹%.2f)%n",
                    order.getOrderId(), order.getCustomerName(), order.getItems(), order.getTotalAmount(), order.getDiscountedAmount());
        }
    }

    public void displayFeedbacks() {
        System.out.println("Customer Feedbacks:");
        for (CustomerFeedback feedback : feedbacks) {
            System.out.println("Customer: " + feedback.getCustomerName() + ", Feedback: " + feedback.getFeedback() + ", Rating: " + feedback.getRating());
        }
    }
}

class Table {
    private int tableNumber;
    private String status; // available, occupied, reserved

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.status = "available"; // Initial status is available
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status; // Update the status of the table
    }
}

class Order {
    private static int orderCounter = 0;
    private int orderId;
    private String customerName;
    private List<String> items;
    private double totalAmount;
    private double discount;

    public Order(String customerName, List<String> items, double totalAmount, double discount) {
        this.orderId = ++orderCounter;
        this.customerName = customerName;
        this.items = items;
        this.totalAmount = totalAmount;
        this.discount = discount;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<String> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getDiscountedAmount() {
        return totalAmount - (totalAmount * discount / 100);
    }
}

class CustomerFeedback {
    private String customerName;
    private String feedback;
    private int rating;

    public CustomerFeedback(String customerName, String feedback, int rating) {
        this.customerName = customerName;
        this.feedback = feedback;
        this.rating = rating;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getFeedback() {
        return feedback;
    }

    public int getRating() {
        return rating;
    }
}

class RestaurantManagementSystem {
    private List<Restaurant> restaurants;
    private Restaurant selectedRestaurant;
    private String customerName; // Store customer name

    public RestaurantManagementSystem() {
        restaurants = new ArrayList<>();
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public Restaurant findRestaurant(String name) {
        for (Restaurant res : restaurants) {
            if (res.getName().equals(name)) {
                return res;
            }
        }
        return null;
    }

    public void processOrder() {
        Scanner scanner = new Scanner(System.in);
        if (customerName == null) {
            System.out.print("Enter customer name: ");
            customerName = scanner.nextLine(); // Ask for customer name once
        }

        System.out.print("Enter order items (comma separated): ");
        String[] itemsInput = scanner.nextLine().split(",");
        List<String> items = Arrays.asList(itemsInput);

        double totalAmount = items.size() * 10; // Example: Each item costs 10 rupees
        System.out.print("Enter discount percentage (0 if no discount): ");
        double discount = scanner.nextDouble();
        scanner.nextLine();

        Order order = new Order(customerName, items, totalAmount, discount);
        selectedRestaurant.addOrder(order);
        System.out.printf("Order processed for %s with total amount: ₹%.2f (Discount: %.2f%%)%n",
                customerName, order.getDiscountedAmount(), discount);
    }

    public void leaveFeedback() {
        Scanner scanner = new Scanner(System.in);
        if (customerName == null) {
            System.out.print("Enter customer name: ");
            customerName = scanner.nextLine(); // Ask for customer name once
        }

        System.out.print("Enter your feedback: ");
        String feedback = scanner.nextLine();
        System.out.print("Enter your rating (1-5): ");
        int rating = scanner.nextInt();
        scanner.nextLine();
        CustomerFeedback customerFeedback = new CustomerFeedback(customerName, feedback, rating);
        selectedRestaurant.addFeedback(customerFeedback);
        System.out.println("Thank you for your feedback!");
    }

    public void changeTableStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter table number to change status: ");
        int tableNumber = scanner.nextInt();
        scanner.nextLine();
        Table table = null;

        for (Table t : selectedRestaurant.getTables()) {
            if (t.getTableNumber() == tableNumber) {
                table = t;
                break;
            }
        }

        if (table == null) {
            System.out.println("Table not found.");
            return;
        }

        System.out.print("Enter new status (available, occupied, reserved): ");
        String newStatus = scanner.nextLine();
        table.setStatus(newStatus);
        System.out.println("Table " + tableNumber + " status updated to: " + newStatus);
    }

    public void showMenu() {
        System.out.println("1. Select Restaurant");
        System.out.println("2. Add Table");
        System.out.println("3. Process Order");
        System.out.println("4. Leave Feedback");
        System.out.println("5. Change Table Status");
        System.out.println("6. Display All Information (including orders and items)");
        System.out.println("7. Exit");
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter restaurant name: ");
                    String restaurantName = scanner.nextLine();
                    selectedRestaurant = findRestaurant(restaurantName);
                    if (selectedRestaurant != null) {
                        System.out.println("Selected restaurant: " + selectedRestaurant.getName());
                    } else {
                        System.out.println("Restaurant not found.");
                    }
                    break;
                case 2:
                    if (selectedRestaurant == null) {
                        System.out.println("Please select a restaurant first.");
                    } else {
                        System.out.print("Enter table number: ");
                        int tableNumber = scanner.nextInt();
                        scanner.nextLine();
                        selectedRestaurant.addTable(new Table(tableNumber));
                        System.out.println("Table added: " + tableNumber);
                    }
                    break;
                case 3:
                    if (selectedRestaurant == null) {
                        System.out.println("Please select a restaurant first.");
                    } else {
                        processOrder();
                    }
                    break;
                case 4:
                    if (selectedRestaurant == null) {
                        System.out.println("Please select a restaurant first.");
                    } else {
                        leaveFeedback();
                    }
                    break;
                case 5:
                    if (selectedRestaurant == null) {
                        System.out.println("Please select a restaurant first.");
                    } else {
                        changeTableStatus();
                    }
                    break;
                case 6:
                    if (selectedRestaurant == null) {
                        System.out.println("Please select a restaurant first.");
                    } else {
                        selectedRestaurant.displayAllInfo();
                    }
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        RestaurantManagementSystem rms = new RestaurantManagementSystem();
        rms.addRestaurant(new Restaurant("kbtcoe"));
        rms.addRestaurant(new Restaurant("Sushi Place"));
        rms.addRestaurant(new Restaurant("Burger Joint"));
        rms.run();
    }
}
