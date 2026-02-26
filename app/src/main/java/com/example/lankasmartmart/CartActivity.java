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
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;
    List<CartItem> cartItems = new ArrayList<>();

    TextView subtotalText, deliveryFeeText, totalText;
    Button checkoutBtn;
    View backBtn, emptyCartLayout, cartContentLayout;

    DatabaseHelper dbHelper;
    int userId;
    String userName, userEmail;
    double deliveryFee = 150.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dbHelper = new DatabaseHelper(this);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = prefs.getInt("USER_ID", -1);
        userEmail = prefs.getString("USER_EMAIL", "");

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
        cartAdapter = new CartAdapter(this, cartItems, () -> loadCart(), dbHelper, userId);
        cartRecyclerView.setAdapter(cartAdapter);

        deliveryFeeText.setText(getString(R.string.price_format, deliveryFee));

        backBtn.setOnClickListener(v -> finish());

        checkoutBtn.setOnClickListener(v -> {
            if (!cartItems.isEmpty()) {
                Intent checkoutIntent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(checkoutIntent);
            } else {
                Toast.makeText(CartActivity.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
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

        Cursor cursor = dbHelper.getCartItems(userId);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Fix: check column index before using it
                int colProductId   = cursor.getColumnIndex(DatabaseHelper.CART_PRODUCT_ID);
                int colName        = cursor.getColumnIndex("product_name");
                int colPrice       = cursor.getColumnIndex("product_price");
                int colQuantity    = cursor.getColumnIndex(DatabaseHelper.CART_QUANTITY);
                int colImage       = cursor.getColumnIndex("product_image");

                if (colProductId < 0 || colName < 0 || colPrice < 0
                        || colQuantity < 0 || colImage < 0) {
                    continue; // skip row if any column is missing
                }

                int productId       = cursor.getInt(colProductId);
                String productName  = cursor.getString(colName);
                double price        = cursor.getDouble(colPrice);
                int quantity        = cursor.getInt(colQuantity);
                String imageUrl     = cursor.getString(colImage);

                cartItems.add(new CartItem(productId, productName, price, quantity, imageUrl));

            } while (cursor.moveToNext());
            cursor.close();
        }

        // Use specific change events where possible; notifyDataSetChanged as last resort
        cartAdapter.notifyDataSetChanged();
        updateSummary();

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

        // Fix: use Locale.getDefault() and getString for formatted currency text
        subtotalText.setText(String.format(Locale.getDefault(), "LKR %.2f", subtotal));
        totalText.setText(String.format(Locale.getDefault(), "LKR %.2f", total));
    }
}