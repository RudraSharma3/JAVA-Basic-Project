package fourth;
import third.order;
import first.LaundryItemException;
public class LaundryItem extends order {
    public int itemId;
    public String itemName;
    public String itemcategory;
    public boolean isclean;
    public double price;
    public int itemquantity;

    public LaundryItem(int itemId, String itemName, String itemcategory, boolean isclean, double price,
            int itemquantity) throws LaundryItemException {
        this.itemId = itemId;
        this.itemName = itemName; 
        this.itemcategory = itemcategory;
        this.isclean = isclean;
        if (price < 0) {
            throw new LaundryItemException(
                    "Item price cannot be negative..!!" + "\nRe-Write the item price carefully..!!");
        }
        this.price = price;
        this.itemquantity = itemquantity;
        if (itemquantity < 0) {
            throw new LaundryItemException(
                    "Item quantity cannot be negative..!!" + "\nRe-Write the item quantity carefully..!!");
        }

    }

    public void order_sumarry() {
        System.out.println("Order I'D : " + itemId + "\nOrder Name : " + itemName + "\nOrder type : " + itemcategory
                + "\nOrder price : " + price + "\nOrder is clean/Not clean : " + isclean + "\nOrder Quantity : "
                + itemquantity);
        System.out.println();
    }

    public void cleanItem() {
        isclean = true;
    }

    public double calculatePrice() {
        return isclean ? 0 : price * itemquantity;
    }
}
