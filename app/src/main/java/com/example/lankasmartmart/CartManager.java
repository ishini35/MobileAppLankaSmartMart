package com.example.lankasmartmart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartManager {
    private static CartManager instance;
    private Map<Integer, CartItem> cartItems;

    private CartManager() {
        cartItems = new HashMap<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(ProductActivity product, int quantity) {
        if (cartItems.containsKey(product.getId())) {
            CartItem item = cartItems.get(product.getId());
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            cartItems.put(product.getId(), new CartItem(product, quantity));
        }
    }

    public void removeFromCart(int productId) {
        cartItems.remove(productId);
    }

    public void updateQuantity(int productId, int quantity) {
        if (cartItems.containsKey(productId)) {
            if (quantity <= 0) {
                removeFromCart(productId);
            } else {
                cartItems.get(productId).setQuantity(quantity);
            }
        }
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems.values());
    }

    public double getCartTotal() {
        double total = 0;
        for (CartItem item : cartItems.values()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public int getItemCount() {
        return cartItems.size();
    }

    public void clearCart() {
        cartItems.clear();
    }

    public static class CartItem {
        private ProductActivity product;
        private int quantity;

        public CartItem(ProductActivity product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public ProductActivity getProduct() { return product; }
        public void setProduct(ProductActivity product) { this.product = product; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public double getTotal() {
            return product.getPrice() * quantity;
        }
    }
}