package com.example.lankasmartmart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView recyclerNotifications;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;
    private ImageButton btnBack;
    private LinearLayout navHome, navCategories, navCart, navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Initialize views
        recyclerNotifications = findViewById(R.id.recyclerNotifications);
        btnBack = findViewById(R.id.btnBack);
        navHome = findViewById(R.id.navHome);
        navCategories = findViewById(R.id.navCategories);
        navCart = findViewById(R.id.navCart);
        navProfile = findViewById(R.id.navProfile);

        // Setup RecyclerView
        recyclerNotifications.setLayoutManager(new LinearLayoutManager(this));

        // Load notifications
        loadNotifications();

        // Setup adapter
        notificationAdapter = new NotificationAdapter(this, notificationList);
        recyclerNotifications.setAdapter(notificationAdapter);

        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Bottom navigation
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to categories
            }
        });

        navCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to profile
            }
        });
    }

    private void loadNotifications() {
        notificationList = new ArrayList<>();

        // Add sample notifications with EXACT colors from your design
        notificationList.add(new Notification(
                1,
                "Weekend Special !",
                "20% off on personal care products",
                "5 min ago",
                R.drawable.ic_discount,
                Color.parseColor("#3D9970")  // Green
        ));

        notificationList.add(new Notification(
                2,
                "Order Delivered",
                "Your order #LSM2024 has been delivered",
                "1 hour ago",
                R.drawable.ic_delivery,
                Color.parseColor("#FF9800")  // Orange
        ));

        notificationList.add(new Notification(
                3,
                "Flash Sale Alert",
                "Chocolate Bars Flying Off the Shelves",
                "Yesterday",
                R.drawable.ic_flash_sale,
                Color.parseColor("#E91E63")  // Pink/Red
        ));
    }
}
