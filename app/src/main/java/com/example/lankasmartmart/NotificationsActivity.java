package com.example.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    RecyclerView recyclerNotifications;
    LinearLayout emptyState;
    TextView btnMarkAllRead;
    View btnBack;
    LinearLayout navHome, navCategories, navCart, navProfile;

    NotificationAdapter adapter;
    List<NotificationModel> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Init views
        recyclerNotifications = findViewById(R.id.recyclerNotifications);
        emptyState = findViewById(R.id.emptyState);
        btnMarkAllRead = findViewById(R.id.btnMarkAllRead);
        btnBack = findViewById(R.id.btnBack);
        navHome = findViewById(R.id.navHome);
        navCategories = findViewById(R.id.navCategories);
        navCart = findViewById(R.id.navCart);
        navProfile = findViewById(R.id.navProfile);

        // Load notifications
        loadNotifications();

        // Setup RecyclerView
        adapter = new NotificationAdapter(this, notificationList);
        recyclerNotifications.setLayoutManager(new LinearLayoutManager(this));
        recyclerNotifications.setAdapter(adapter);

        // Show empty state if no notifications
        if (notificationList.isEmpty()) {
            recyclerNotifications.setVisibility(View.GONE);
            emptyState.setVisibility(View.VISIBLE);
        }

        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Mark all as read
        btnMarkAllRead.setOnClickListener(v -> {
            adapter.markAllAsRead();
        });

        // Bottom Navigation
        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        navCategories.setOnClickListener(v -> {
            // Navigate to categories
        });

        navCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });

        navProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
    }

    private void loadNotifications() {
        notificationList = new ArrayList<>();

        // Sample notifications matching the Figma design
        notificationList.add(new NotificationModel(
                "Weekend Special !",
                "20% off on personal care products",
                "5 min ago",
                R.drawable.ic_notifications,
                false,
                "promo"
        ));

        notificationList.add(new NotificationModel(
                "Order Delivered",
                "Your order #LSM2024 has been delivered",
                "1 hour ago",
                R.drawable.ic_shopping_cart,
                false,
                "order"
        ));

        notificationList.add(new NotificationModel(
                "Flash Sale Alert",
                "Chocolate Bars Flying Off the Shelves",
                "Yesterday",
                R.drawable.ic_notifications,
                true,
                "flash_sale"
        ));
    }
}