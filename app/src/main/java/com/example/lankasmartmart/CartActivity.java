package com.example.lankasmartmart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;
    List<CartItem> cartItems = new ArrayList<>();

    TextView subtotalText, deliveryFeeText, totalText;
    Button checkoutBtn;
    View backBtn, emptyCartLayout, cartContentLayout;

    DatabaseHelper dbHelper;
    int userId; // CHANGED: Now using INTEGER user ID
    String userName, userEmail;
    double deliveryFee = 150.00; // CHANGED: Match CheckoutActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dbHelper = new DatabaseHelper(this);

        // Get user ID from SharedPreferences (consistent with other activities)
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = prefs.getInt("USER_ID", -1);
        userEmail = prefs.getString("USER_EMAIL", "");

        // Get user name from intent or SharedPreferences
        Intent intent = getIntent();
        userName = intent.getStringExtra("USER_NAME");
        if (userName == null || userName.isEmpty()) {
            userName = prefs.getString("USER_NAME", "User");
        }

        // Initialize views
        backBtn = findViewById(R.id.backBtn);
        emptyCartLayout = findViewById(R.id.emptyCartLayout);
        cartContentLayout = findViewById(R.id.cartContentLayout);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        subtotalText = findViewById(R.id.subtotalText);
        deliveryFeeText = findViewById(R.id.deliveryFeeText);
        totalText = findViewById(R.id.totalText);
        checkoutBtn = findViewById(R.id.checkoutBtn);

        // Setup RecyclerView
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartItems, new CartAdapter.CartUpdateListener() {
            @Override
            public void onCartUpdated() {
                loadCart();
            }
        }, dbHelper, userId);
        cartRecyclerView.setAdapter(cartAdapter);

        deliveryFeeText.setText("LKR " + String.format("%.2f", deliveryFee));

        // Back button
        backBtn.setOnClickListener(v -> finish());

        // Checkout button
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cartItems.isEmpty()) {
                    Intent checkoutIntent = new Intent(CartActivity.this, CheckoutActivity.class);
                    startActivity(checkoutIntent);
                } else {
                    Toast.makeText(CartActivity.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadCart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCart();
    }

    private void loadCart() {
        cartItems.clear();

        // Get cart items from database
        Cursor cursor = dbHelper.getCartItems(userId);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CART_PRODUCT_ID));
                String productName = cursor.getString(cursor.getColumnIndex("product_name"));
                double price = cursor.getDouble(cursor.getColumnIndex("product_price"));
                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CART_QUANTITY));
                String imageUrl = cursor.getString(cursor.getColumnIndex("product_image"));

                CartItem item = new CartItem(productId, productName, price, quantity, imageUrl);
                cartItems.add(item);

            } while (cursor.moveToNext());
            cursor.close();
        }

        cartAdapter.notifyDataSetChanged();
        updateSummary();

        // Show/hide empty cart message
        if (cartItems.isEmpty()) {
            emptyCartLayout.setVisibility(View.VISIBLE);
            cartContentLayout.setVisibility(View.GONE);
        } else {
            emptyCartLayout.setVisibility(View.GONE);
            cartContentLayout.setVisibility(View.VISIBLE);
        }
    }

    private void updateSummary() {
        double subtotal = 0.0;

        for (CartItem item : cartItems) {
            subtotal += item.getPrice() * item.getQuantity();
        }

        double total = subtotal + deliveryFee;

        subtotalText.setText("LKR " + String.format("%.2f", subtotal));
        totalText.setText("LKR " + String.format("%.2f", total));
    }
}