import java.sql.*;
import java.util.*;

class LaundryItemException extends Exception {
    public LaundryItemException(String message) {
        super(message);
    }
}

interface customer_Details {
    public void customer_details();
}

class order {
    public void order_sumarry() {
        System.out.println("No order yet..!!");
        System.out.println();
    }
}

class LaundryItem extends order {
    public int itemId;
    public String itemcategory;
    public int itemquantity;

    private static Map<String, Double> itemPrices = new HashMap<>();
    static {
        
        itemPrices.put("shirt", 5.0);
        itemPrices.put("pants", 7.0);
        itemPrices.put("jacket", 10.0);
        itemPrices.put("shorts", 10.0);
        itemPrices.put("t-shirt", 10.0);
        itemPrices.put("jeans", 10.0);
        
    }

    public LaundryItem(int itemId, String itemcategory,
            int itemquantity) throws LaundryItemException {
        this.itemId = itemId;
        this.itemcategory = itemcategory;

        this.itemquantity = itemquantity <= 0 ? 1 : itemquantity;
        if (itemquantity < 0) {
            throw new LaundryItemException(
                    "Item quantity cannot be negative..!!" + "\nRe-Write the item quantity carefully..!!");
        }

    }

    public void order_sumarry() {
        System.out.println("Order I'D : " + itemId + "\nOrder type : " + itemcategory
                + "\nOrder price : " + itemPrices.get(itemcategory) + "\nOrder Quantity : "
                + itemquantity);
        System.out.println();
    }

    public double calculatePrice() {
        Double price = itemPrices.get(itemcategory);

    
        if (price != null) {
            return price * itemquantity;
        } else {
            System.err.println("Warning: Unknown item category '" + itemcategory + "'. Setting price to 0.");
            return 0.0;
        }
    }

    class Discount {
        private static final double DISCOUNT_RATE = 0.1; 

        public double calculateDiscount(double totalAmount) {
            return totalAmount * DISCOUNT_RATE;
        }
    }
}

class Customer implements customer_Details {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/java_project";
    private static final String USER = "root";
    private static final String PASSWORD = "Naga93511!?";
    public int customerId;
    public String customerName;
    public String phoneNumber;
    public String address;
    public double totalAmount;
    public List<LaundryItem> basket;
    public List<String> feedbackList;

    public Customer(int customerId, String customerName, String phoneNumber, String address) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.totalAmount = 0.0;
        this.basket = new ArrayList<>();
        feedbackList = new ArrayList<>();
    }

    public void customer_details() {
        System.out.println("Customer I'D : " + customerId + "\nCustomer Name :" + customerName
                + "\nCustomer phone number :" + phoneNumber + "\nCustomer Address : " + address);
        System.out.println();
    }

    public void addItemToBasket(LaundryItem item) {
        System.out.println("Added item to the basket : " + item.itemcategory);
        basket.add(item);
        totalAmount += item.calculatePrice();
        System.out.println();
    }

    public void viewBasket() {
        if (basket.isEmpty()) {
            System.out.println("Customer's basket is empty..!!");
            System.out.println();
        } else {
            System.out.println("Customer's Basket:");
            for (LaundryItem item : basket) {
                System.out.println("Item: " + item.itemcategory + ", Quantity: " + item.itemquantity);
            }
        }
        System.out.println();
    }

    public double checkout() {
        return totalAmount;
    }

    public void provideFeedback(String feedback) {
        feedbackList.add(feedback);
        System.out.println("Thank you for your feedback..!!");
    }

    public void viewFeedback() {
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback available..!!");
        } else {
            System.out.println("Customer Feedback :");
            for (String feedback : feedbackList) {
                System.out.println(feedback);
            }
        }
        System.out.println();
    }

    public void insertIntoDatabase() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String query = "INSERT INTO CustomerDetails (customerId, customerName, phoneNumber, address, totalAmount) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, customerId);
                preparedStatement.setString(2, customerName);
                preparedStatement.setString(3, phoneNumber);
                preparedStatement.setString(4, address);
                preparedStatement.setDouble(5, totalAmount);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to insert customer details into the database.");
        }
    }
}

class LaundryManagementApp extends order {
    static {
        System.out.println();
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
        System.out.println("!!..Welcome to.." + "NAGA's LAUNDARY SERVICES..!!");
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
        System.out.println();
    }
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/java_project";
    private static final String USER = "root";
    private static final String PASSWORD = "Naga93511!?";

    static {
        try {
            DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database!");
        }
    }

    public void order_sumarry() {
        super.order_sumarry();
        System.out.println("Ordering..");
    }

    public static void main(String[] args) throws LaundryItemException {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter your name : ");
            String str = scan.nextLine();
            System.out.println();

            LaundryManagementApp lma = new LaundryManagementApp();

            System.out.print("Order summary : ");
            System.out.println();
            lma.order_sumarry();
            System.out.println("Enter your details..!! ");
            String xx;
            System.out.print("Mobile : ");
            xx = scan.nextLine();
            System.out.print("Address : ");
            String z;
            z = scan.nextLine();
            int a;
            System.out.print("Enter customer id : ");
            a = scan.nextInt();
            Customer c1 = new Customer(a, str, xx, z);
            boolean addMoreItems = true;
            LaundryItem l = null; 
            int i = 1;
            while (addMoreItems) {
                i++;
                try {
                    System.out.print("Enter item Name : ");
                    String itemcategory = scan.next();
                    System.out.print("Enter item Quantity : ");
                    int itemquantity = scan.nextInt();
                    l = new LaundryItem(i, itemcategory, itemquantity);
                    c1.addItemToBasket(l);
                } catch (LaundryItemException e) {
                    System.err.println("LaundryItemException: " + e.getMessage());
                    System.out.println();
                }
                System.out.print("Add more items ? (true/false) : ");
                addMoreItems = scan.nextBoolean();
                scan.nextLine();
                System.out.println();
            }
            System.out.print("Want costumer details ? true/false : ");
            boolean x;
            x = scan.nextBoolean();
            System.out.println();
            if (x) {
                c1.customer_details();
            } else if (x == false) {
                System.out.println("OK..!!");
                System.out.println();
            } else {
                System.out.println("SORRY..!! invalid entry");
                System.out.println();
            }
            System.out.print("Want order summary ? true/false : ");
            boolean y;
            y = scan.nextBoolean();
            if (y) {
                l.order_sumarry();
            } else if (y == false) {
                System.out.println("OK..!!");
                System.out.println();
            } else {
                System.out.println("SORRY..!!  Invalid Entry..!!");
                System.out.println();
            }
            System.out.print("Want to provide feedback ? true/false: ");
            boolean feedbackOption = scan.nextBoolean();
            scan.nextLine();
            if (feedbackOption) {
                System.out.print("Please provide your feedback : ");
                String feedback = scan.nextLine();
                c1.provideFeedback(feedback);
                System.out.println();
            }
            c1.viewBasket();
            double total = c1.checkout();
            LaundryItem.Discount discountCalculator = l.new Discount();
            double discount = discountCalculator.calculateDiscount(total);
            System.out.println("Total Price : " + total + " $");
            System.out.println();
            System.out.println("Discount : " + discount + " $");
            System.out.println("Final Price after Discount : " + (total - discount) + " $");
            System.out.println();
            System.out.print("View customer feedback ? true/false: ");
            boolean viewFeedback = scan.nextBoolean();
            if (viewFeedback) {
                c1.viewFeedback();
            }
            System.out.println();
            System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
            System.out.println("      !!..THANK YOU.." + "SEE YOU SOON..!!");
            System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
            System.out.println();
            c1.insertIntoDatabase();
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}