package com.example.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartUpdateListener {

    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;
    List<CartItem> cartItems = new ArrayList<>();

    TextView emptyCartText, subtotalText, deliveryFeeText, totalText;
    Button checkoutBtn;
    View backBtn, emptyCartLayout, cartContentLayout;

    DatabaseHelper dbHelper;
    String userId, userName, userEmail;
    double deliveryFee = 200.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        userName  = intent.getStringExtra("USER_NAME");
        userEmail = intent.getStringExtra("USER_EMAIL");
        userId    = userEmail != null ? userEmail : "guest";

        backBtn          = findViewById(R.id.backBtn);
        emptyCartLayout  = findViewById(R.id.emptyCartLayout);
        cartContentLayout = findViewById(R.id.cartContentLayout);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        subtotalText     = findViewById(R.id.subtotalText);
        deliveryFeeText  = findViewById(R.id.deliveryFeeText);
        totalText        = findViewById(R.id.totalText);
        checkoutBtn      = findViewById(R.id.checkoutBtn);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartItems, this, dbHelper);
        cartRecyclerView.setAdapter(cartAdapter);

        deliveryFeeText.setText("LKR " + String.format("%.2f", deliveryFee));

        backBtn.setOnClickListener(v -> finish());

        checkoutBtn.setOnClickListener(v -> {
            if (!cartItems.isEmpty()) {
                Intent checkoutIntent = new Intent(this, CheckoutActivity.class);
                checkoutIntent.putExtra("USER_NAME", userName);
                checkoutIntent.putExtra("USER_EMAIL", userEmail);
                checkoutIntent.putExtra("CART_TOTAL", dbHelper.getCartTotal(userId) + deliveryFee);
                startActivity(checkoutIntent);
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
        cartItems.addAll(dbHelper.getCartItems(userId));
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

    @Override
    public void onCartUpdated() {
        cartItems.clear();
        cartItems.addAll(dbHelper.getCartItems(userId));
        cartAdapter.notifyDataSetChanged();
        updateSummary();

        if (cartItems.isEmpty()) {
            emptyCartLayout.setVisibility(View.VISIBLE);
            cartContentLayout.setVisibility(View.GONE);
        }
    }

    private void updateSummary() {
        double subtotal = dbHelper.getCartTotal(userId);
        double total = subtotal + deliveryFee;
        subtotalText.setText("LKR " + String.format("%.2f", subtotal));
        totalText.setText("LKR " + String.format("%.2f", total));
    }
}

