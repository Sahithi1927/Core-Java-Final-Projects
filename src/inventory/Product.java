package inventory;

public class Product {
	private int productId;
    private String productName;
    private Category category;
    private double price;
    private int stock;

    public Product(int productId, String productName,
                   Category category, double price, int stock) {

        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void addStock(int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                "Stock quantity must be greater than 0."
            );
        }

        stock = stock + quantity;
    }

    public void sellProduct(int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                "Quantity must be greater than 0."
            );
        }

        if (quantity > stock) {
            throw new IllegalArgumentException(
                "Insufficient stock."
            );
        }

        stock = stock - quantity;
    }

    @Override
    public String toString() {

        return "ID: " + productId +
               ", Name: " + productName +
               ", Category: " + category +
               ", Price: " + price +
               ", Stock: " + stock;
    }
}

