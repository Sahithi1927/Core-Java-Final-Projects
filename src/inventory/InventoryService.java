package inventory;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {
	private Map<Integer, Product> products = new HashMap<>();

    // Add product
    public void addProduct(Product product) {

        if (products.containsKey(product.getProductId())) {
            throw new IllegalArgumentException(
                "Product ID already exists."
            );
        }

        products.put(product.getProductId(), product);

        System.out.println("Product added successfully.");
    }

    // Search product
    public Product searchProduct(int productId) {

        Product product = products.get(productId);

        if (product == null) {
            throw new IllegalArgumentException(
                "Product not found."
            );
        }

        return product;
    }

    // Update price
    public void updatePrice(int productId, double newPrice) {

        if (newPrice <= 0) {
            throw new IllegalArgumentException(
                "Price must be greater than 0."
            );
        }

        Product product = searchProduct(productId);

        product.setPrice(newPrice);

        System.out.println("Price updated successfully.");
    }

    // Add stock
    public void addStock(int productId, int quantity) {

        Product product = searchProduct(productId);

        product.addStock(quantity);

        System.out.println("Stock added successfully.");
    }

    // Sell product
    public double sellProduct(int productId, int quantity) {

        Product product = searchProduct(productId);

        double total = product.getPrice() * quantity;

        product.sellProduct(quantity);

        return total;
    }

    // Display all products
    public void displayAllProducts() {

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product product : products.values()) {
            System.out.println(product);
        }
    }

    // Display low stock products
    public void displayLowStockProducts() {

        boolean found = false;

        for (Product product : products.values()) {

            if (product.getStock() < 5) {

                System.out.println(product);

                found = true;
            }
        }

        if (!found) {
            System.out.println("No low stock products.");
        }
    }

    // Delete product
    public void deleteProduct(int productId) {

        if (!products.containsKey(productId)) {
            throw new IllegalArgumentException(
                "Product not found."
            );
        }

        products.remove(productId);

        System.out.println("Product deleted successfully.");
    }
}

