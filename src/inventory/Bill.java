package inventory;

public class Bill {
	private double total;

    public void addAmount(double amount) {

        total = total + amount;
    }

    public double getTotal() {

        return total;
    }

    public void displayBill() {

        System.out.println("----------------------");
        System.out.println("Total Bill: ₹" + total);
        System.out.println("----------------------");
    }
}

