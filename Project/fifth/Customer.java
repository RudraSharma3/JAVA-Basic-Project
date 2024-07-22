package fifth;
import second.customer_Details;
import fourth.LaundryItem;
import java.util.*;
public class Customer implements customer_Details {
    public int customerId;
    public String customerName;
    public String phoneNumber;
    public String address;
    public double totalAmount; 
    public List<String> feedbackList;

    public Customer(int customerId, String customerName, String phoneNumber, String address) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.totalAmount = 0.0;
        feedbackList = new ArrayList<>();
    }

    public void customer_details() {
        System.out.println("Customer I'D : " + customerId + "\nCustomer Name :" + customerName
                + "\nCustomer phone number :" + phoneNumber + "\nCustomer Address : " + address);
        System.out.println();
    }

    public void addItemToBasket(LaundryItem item) {
        System.out.println("Added item to the basket : " + item.itemName);
        totalAmount += item.calculatePrice();
        System.out.println();
    }

    public void viewBasket() {
        System.out.println("Now cloths are waashed..!!");
        System.out.println("Customer's basket is empty..!!");
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
}
