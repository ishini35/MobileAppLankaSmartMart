package com.example.lankasmartmart;

public class ProductActivity {
    private int id;
    private String name;
    private double price;
    private String category;
    private String description;
    private int stock;
    private boolean inStock;
    private int imageResource;

    // Constructor
    public ProductActivity(int id, String name, double price, String category,
                           String description, int stock, int imageResource) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.stock = stock;
        this.inStock = stock > 0;
        this.imageResource = imageResource;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getStock() { return stock; }
    public void setStock(int stock) {
        this.stock = stock;
        this.inStock = stock > 0;
    }

    public boolean isInStock() { return inStock; }

    public int getImageResource() { return imageResource; }
    public void setImageResource(int imageResource) { this.imageResource = imageResource; }
}
private void loadProducts() {
    productList = new ArrayList<>();

    // Groceries with real images
    productList.add(new Product(1, "Basmati Rice - 1kg", 350.00,
            "Groceries", "Premium quality basmati rice", 10, R.drawable.img_rice));

    productList.add(new Product(2, "Imorich French Vanilla - 1L", 1290.00,
            "Groceries", "Rich and creamy French vanilla ice cream", 5, R.drawable.img_ice_cream));

    productList.add(new Product(3, "Munchee Choc Shock - 90g", 300.00,
            "Groceries", "Delicious chocolate biscuits", 20, R.drawable.img_chocolate));

    productList.add(new Product(4, "Tiara Sponge Layer Cake - 310g", 550.00,
            "Groceries", "Soft and fluffy sponge cake", 8, R.drawable.img_cake));


}