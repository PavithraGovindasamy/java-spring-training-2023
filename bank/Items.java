package bank;
/**
 * 
 * @author pavithra
 * class that implements the name and price of a item
 */
class Items {
    private String name;
    private double price;

    public Items(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}