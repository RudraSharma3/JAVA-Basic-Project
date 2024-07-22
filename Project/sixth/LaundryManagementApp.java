package sixth;
import first.LaundryItemException;
import third.order;
import fourth.LaundryItem;
import fifth.Customer;
import java.util.*;
public class LaundryManagementApp extends order {
    static {
        System.out.println();
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
        System.out.println("!!..Welcome to.." + "NAGA's LAUNDARY SERVICES..!!");
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
        System.out.println();
    }

    public void order_sumarry() {
        super.order_sumarry();
        System.out.println("Ordering..");
    }

    public static void main(String[] args) throws LaundryItemException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your name : ");
        String str = scan.nextLine();
        System.out.println();

        LaundryManagementApp lma = new LaundryManagementApp();

        System.out.print("Order summary : ");
        System.out.println();
        lma.order_sumarry();
        Customer c1 = new Customer(101, str, "123-456-7890", "123 Main St");
        LaundryItem l = new LaundryItem(0, str, str, false, 0, 0);
        boolean addMoreItems = true;

        while (addMoreItems) {
            try {
                System.out.print("Enter item Category : ");
                String itemcategory = scan.nextLine();
                System.out.print("Enter item name : ");
                String itemName = scan.nextLine();
                System.out.print("Enter item Quantity : ");
                int itemquantity = scan.nextInt();
                System.out.print("Is the item clean (true/false) : ");
                boolean isClean = scan.nextBoolean();
                System.out.print("Enter item price : ");
                double price = scan.nextDouble();
                l = new LaundryItem(1, itemName, itemcategory, isClean, price, itemquantity);
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
        if (x == true) {
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
        if (y == true) {
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
        System.out.println("Total Price : " + total + " $");
        System.out.println();
        System.out.print("View customer feedback ? true/false: ");
        boolean viewFeedback = scan.nextBoolean();
        if (viewFeedback) {
            c1.viewFeedback();
        }
        scan.close();
    }
}
