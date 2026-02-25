package com.example.lankasmartmart;

public class Product {
    private int id;
    private String name;
    private double price;
    private String category;
    private String description;
    private int stock;
    private boolean inStock;
    private int imageResource;

    // Constructor
    public Product(int id, String name, double price, String category,
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