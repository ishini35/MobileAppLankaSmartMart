package com.example.lankasmartmart;

public class CartItem {
    private int id;
    private String userId;
    private int productId;
    private String productName;
    private String productBrand;
    private double productPrice;
    private String productCategory;
    private int quantity;

    public CartItem(int id, String userId, int productId, String productName,
                    String productBrand, double productPrice, String productCategory, int quantity) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.productBrand = productBrand;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getUserId() { return userId; }
    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getProductBrand() { return productBrand; }
    public double getProductPrice() { return productPrice; }
    public String getProductCategory() { return productCategory; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getSubtotal() { return productPrice * quantity; }
}

