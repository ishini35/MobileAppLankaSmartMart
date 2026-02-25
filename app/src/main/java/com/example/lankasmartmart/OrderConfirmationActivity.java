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
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OrderConfirmationActivity extends AppCompatActivity {

    // UI Components
    private ImageView btnBack;
    private TextView orderIdText, estimatedDeliveryText, deliveryAddressText, totalAmountText;
    private Button trackOrderButton, continueShoppingButton;
    private LinearLayout navHome, navCategories, navCart, navProfile;

    // Data
    private int orderId;
    private double totalAmount;
    private String deliveryAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

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

        // Handle back press using AndroidX dispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goHome();
            }
        });
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        orderIdText = findViewById(R.id.orderIdText);
        estimatedDeliveryText = findViewById(R.id.estimatedDeliveryText);
        deliveryAddressText = findViewById(R.id.deliveryAddressText);
        totalAmountText = findViewById(R.id.totalAmountText);
        trackOrderButton = findViewById(R.id.trackOrderButton);
        continueShoppingButton = findViewById(R.id.continueShoppingButton);
        navHome = findViewById(R.id.navHome);
        navCategories = findViewById(R.id.navCategories);
        navCart = findViewById(R.id.navCart);
        navProfile = findViewById(R.id.navProfile);
    }

    private void displayOrderDetails() {
        orderIdText.setText(String.format(Locale.ENGLISH, "Order #%s", generateOrderId()));
        estimatedDeliveryText.setText(getEstimatedDelivery());

        if (deliveryAddress != null && !deliveryAddress.isEmpty()) {
            deliveryAddressText.setText(deliveryAddress);
        } else {
            deliveryAddressText.setText("123, Galle Road, Colombo 03");
        }

        totalAmountText.setText(String.format(Locale.ENGLISH, "LKR %.2f", totalAmount));
    }

    private String generateOrderId() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return String.format(Locale.ENGLISH, "LKM%d/%02d/%04d", year, month, orderId);
    }

    private String getEstimatedDelivery() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d", Locale.ENGLISH);
        return dateFormat.format(calendar.getTime()) + ", 2-4 PM";
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> goHome());

        trackOrderButton.setOnClickListener(v ->
                Toast.makeText(this, "Order tracking coming soon!", Toast.LENGTH_SHORT).show());

        continueShoppingButton.setOnClickListener(v -> goHome());

        navHome.setOnClickListener(v -> goHome());

        navCategories.setOnClickListener(v ->
                Toast.makeText(this, "Categories", Toast.LENGTH_SHORT).show());

        navCart.setOnClickListener(v ->
                startActivity(new Intent(this, CartActivity.class)));

        navProfile.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));
    }

    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}