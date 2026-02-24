package com.example.lankasmartmart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OrderConfirmationActivity extends AppCompatActivity {

    // UI Components
    private ImageView btnBack;
    private TextView orderIdText, estimatedDeliveryText, deliveryAddressText, totalAmountText;
    private Button trackOrderButton, continueShoppingButton;
    private LinearLayout navHome, navCategories, navCart, navProfile;

    // Data
    private DatabaseHelper databaseHelper;
    private int orderId;
    private int currentUserId;
    private double totalAmount;
    private String deliveryAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Get current user
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        currentUserId = prefs.getInt("USER_ID", -1);

        // Get order details from intent
        Intent intent = getIntent();
        orderId = intent.getIntExtra("ORDER_ID", -1);
        totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0);
        deliveryAddress = intent.getStringExtra("DELIVERY_ADDRESS");

        // Initialize views
        initializeViews();

        // Display order details
        displayOrderDetails();

        // Setup listeners
        setupListeners();
    }

    private void initializeViews() {
        // Top bar
        btnBack = findViewById(R.id.btnBack);

        // Order details
        orderIdText = findViewById(R.id.orderIdText);
        estimatedDeliveryText = findViewById(R.id.estimatedDeliveryText);
        deliveryAddressText = findViewById(R.id.deliveryAddressText);
        totalAmountText = findViewById(R.id.totalAmountText);

        // Buttons
        trackOrderButton = findViewById(R.id.trackOrderButton);
        continueShoppingButton = findViewById(R.id.continueShoppingButton);

        // Bottom navigation
        navHome = findViewById(R.id.navHome);
        navCategories = findViewById(R.id.navCategories);
        navCart = findViewById(R.id.navCart);
        navProfile = findViewById(R.id.navProfile);
    }

    private void displayOrderDetails() {
        // Generate order ID format: LKM + Year + Month + OrderID
        String formattedOrderId = generateOrderId();
        orderIdText.setText("Order #" + formattedOrderId);

        // Calculate estimated delivery (tomorrow, 2-4 PM)
        String estimatedDelivery = getEstimatedDelivery();
        estimatedDeliveryText.setText(estimatedDelivery);

        // Set delivery address
        if (deliveryAddress != null && !deliveryAddress.isEmpty()) {
            deliveryAddressText.setText(deliveryAddress);
        } else {
            deliveryAddressText.setText("123, Galle Road, Colombo 03");
        }

        // Set total amount
        totalAmountText.setText(String.format("LKR %.2f", totalAmount));
    }

    private String generateOrderId() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        return String.format("LKM%d/%02d/%04d", year, month, orderId);
    }

    private String getEstimatedDelivery() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1); // Add 1 day

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d", Locale.ENGLISH);
        String tomorrowDate = dateFormat.format(calendar.getTime());

        return tomorrowDate + ", 2-4 PM";
    }

    private void setupListeners() {
        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        // Track Order button
        trackOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to order tracking (you can implement this later)
                Toast.makeText(OrderConfirmationActivity.this,
                        "Order tracking coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        // Continue Shopping button
        continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        // Bottom navigation
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        navCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderConfirmationActivity.this, "Categories", Toast.LENGTH_SHORT).show();
            }
        });

        navCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderConfirmationActivity.this, "Cart", Toast.LENGTH_SHORT).show();
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderConfirmationActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void goHome() {
        Intent intent = new Intent(OrderConfirmationActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goHome();
    }
}