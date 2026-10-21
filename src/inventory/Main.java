package inventory;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        InventoryService service = new InventoryService();

        while (true) {

            System.out.println("\n===== INVENTORY MANAGEMENT SYSTEM =====");

            System.out.println("1. Add Product");
            System.out.println("2. Search Product");
            System.out.println("3. Update Price");
            System.out.println("4. Add Stock");
            System.out.println("5. Sell Product");
            System.out.println("6. Show All Products");
            System.out.println("7. Show Low Stock Products");
            System.out.println("8. Calculate Bill");
            System.out.println("9. Delete Product");
            System.out.println("10. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            try {

                switch (choice) {

                    case 1:

                        System.out.print("Enter Product ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Product Name: ");
                        String name = sc.nextLine();

                        System.out.println("Select Category:");
                        System.out.println("1. LAPTOP");
                        System.out.println("2. MOBILE");
                        System.out.println("3. ACCESSORY");
                        System.out.println("4. HOME_APPLIANCE");

                        System.out.print("Enter category choice: ");
                        int categoryChoice = sc.nextInt();

                        Category category;

                        if (categoryChoice == 1) {
                            category = Category.LAPTOP;
                        } else if (categoryChoice == 2) {
                            category = Category.MOBILE;
                        } else if (categoryChoice == 3) {
                            category = Category.ACCESSORY;
                        } else if (categoryChoice == 4) {
                            category = Category.HOME_APPLIANCE;
                        } else {
                            throw new IllegalArgumentException(
                                "Invalid category choice."
                            );
                        }

                        System.out.print("Enter Price: ");
                        double price = sc.nextDouble();

                        System.out.print("Enter Stock: ");
                        int stock = sc.nextInt();

                        if (price <= 0) {
                            throw new IllegalArgumentException(
                                "Price must be greater than 0."
                            );
                        }

                        if (stock < 0) {
                            throw new IllegalArgumentException(
                                "Stock cannot be negative."
                            );
                        }

                        Product product = new Product(
                            id,
                            name,
                            category,
                            price,
                            stock
                        );

                        service.addProduct(product);

                        break;

                    case 2:

                        System.out.print("Enter Product ID: ");
                        int searchId = sc.nextInt();

                        Product found = service.searchProduct(searchId);

                        System.out.println(found);

                        break;

                    case 3:

                        System.out.print("Enter Product ID: ");
                        int priceId = sc.nextInt();

                        System.out.print("Enter New Price: ");
                        double newPrice = sc.nextDouble();

                        service.updatePrice(priceId, newPrice);

                        break;

                    case 4:

                        System.out.print("Enter Product ID: ");
                        int stockId = sc.nextInt();

                        System.out.print("Enter Quantity to Add: ");
                        int quantity = sc.nextInt();

                        service.addStock(stockId, quantity);

                        break;

                    case 5:

                        System.out.print("Enter Product ID: ");
                        int sellId = sc.nextInt();

                        System.out.print("Enter Quantity: ");
                        int sellQuantity = sc.nextInt();

                        double amount =
                            service.sellProduct(sellId, sellQuantity);

                        System.out.println(
                            "Sale successful. Amount: ₹" + amount
                        );

                        break;

                    case 6:

                        service.displayAllProducts();

                        break;

                    case 7:

                        service.displayLowStockProducts();

                        break;

                    case 8:

                        Bill bill = new Bill();

                        System.out.print("Enter Product ID: ");
                        int billId = sc.nextInt();

                        System.out.print("Enter Quantity: ");
                        int billQuantity = sc.nextInt();

                        double billAmount =
                            service.sellProduct(billId, billQuantity);

                        bill.addAmount(billAmount);

                        bill.displayBill();

                        break;

                    case 9:

                        System.out.print("Enter Product ID: ");
                        int deleteId = sc.nextInt();

                        service.deleteProduct(deleteId);

                        break;

                    case 10:

                        System.out.println(
                            "Thank you for using Inventory Management System."
                        );

                        sc.close();

                        return;

                    default:

                        System.out.println("Invalid choice.");
                }

            } catch (IllegalArgumentException e) {

                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

