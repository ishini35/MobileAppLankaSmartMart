package com.example.lankasmartmart;

public class CartItem {
    private int productId;
    private String productName;
    private double price;
    private int quantity;
    private String imageUrl;

    public CartItem(int productId, String productName, double price, int quantity, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getImageUrl() { return imageUrl; }

    // Setters
    public void setQuantity(int quantity) { this.quantity = quantity; }
}