package com.example.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView welcomeText, profileName, profileEmail;
    EditText searchEditText;
    ImageView menuIcon, closeMenu;
    RelativeLayout profileOverlay;
    LinearLayout groceriesCategory, householdCategory, personalCareCategory, stationeryCategory;
    LinearLayout navHome, navCategories, navCart, navProfile;
    LinearLayout menuNotifications, menuSettings, menuBarcode, menuOrderHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        welcomeText = findViewById(R.id.welcomeText);
        searchEditText = findViewById(R.id.searchEditText);
        menuIcon = findViewById(R.id.menuIcon);
        closeMenu = findViewById(R.id.closeMenu);
        profileOverlay = findViewById(R.id.profileOverlay);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);

        groceriesCategory = findViewById(R.id.groceriesCategory);
        householdCategory = findViewById(R.id.householdCategory);
        personalCareCategory = findViewById(R.id.personalCareCategory);
        stationeryCategory = findViewById(R.id.stationeryCategory);

        navHome = findViewById(R.id.navHome);
        navCategories = findViewById(R.id.navCategories);
        navCart = findViewById(R.id.navCart);
        navProfile = findViewById(R.id.navProfile);

        menuNotifications = findViewById(R.id.menuNotifications);
        menuSettings = findViewById(R.id.menuSettings);
        menuBarcode = findViewById(R.id.menuBarcode);
        menuOrderHistory = findViewById(R.id.menuOrderHistory);

        // Get user data from intent
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");
        String userEmail = intent.getStringExtra("USER_EMAIL");

        // Extract name from email if USER_NAME is not provided
        if (userName == null || userName.isEmpty()) {
            if (userEmail != null && !userEmail.isEmpty()) {
                String emailName = userEmail.split("@")[0];
                emailName = emailName.replace(".", " ").replace("_", " ");

                String[] words = emailName.split(" ");
                StringBuilder capitalizedName = new StringBuilder();
                for (String word : words) {
                    if (word.length() > 0) {
                        capitalizedName.append(word.substring(0, 1).toUpperCase())
                                .append(word.substring(1).toLowerCase())
                                .append(" ");
                    }
                }
                userName = capitalizedName.toString().trim();
            } else {
                userName = "User";
            }
        }
        // In MainActivity.java, find menuNotifications click listener
        menuNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(intent);
                profileOverlay.setVisibility(View.GONE);
            }
        });

        // Set welcome text and profile info
        welcomeText.setText("Hello, " + userName + " !");
        profileName.setText(userName);

        if (userEmail != null && !userEmail.isEmpty()) {
            profileEmail.setText(userEmail);
        } else {
            profileEmail.setText("No email provided");
        }

        // Menu icon click - show overlay
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileOverlay.setVisibility(View.VISIBLE);
            }
        });

        // Close menu
        closeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileOverlay.setVisibility(View.GONE);
            }
        });

        // Hide overlay when clicking outside
        profileOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileOverlay.setVisibility(View.GONE);
            }
        });

        // Category clicks - Navigate to Products

        groceriesCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.putExtra("CATEGORY", "Groceries");
                startActivity(intent);
            }
        });

        householdCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.putExtra("CATEGORY", "Household");
                startActivity(intent);
            }
        });

        personalCareCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.putExtra("CATEGORY", "Personal Care");
                startActivity(intent);
            }
        });

        stationeryCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.putExtra("CATEGORY", "Stationery");
                startActivity(intent);
            }
        });

        // Bottom Navigation

        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Already on home
                Toast.makeText(MainActivity.this, "Already on Home", Toast.LENGTH_SHORT).show();
            }
        });

        navCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Create CategoriesActivity
                Toast.makeText(MainActivity.this, "Categories", Toast.LENGTH_SHORT).show();
            }
        });

        navCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Profile Menu Items

        menuNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Notifications", Toast.LENGTH_SHORT).show();
                profileOverlay.setVisibility(View.GONE);
            }
        });

        menuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                profileOverlay.setVisibility(View.GONE);
            }
        });

        menuBarcode.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BarcodeScannerActivity.class));
            profileOverlay.setVisibility(View.GONE);
        });

        menuOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Order History", Toast.LENGTH_SHORT).show();
                profileOverlay.setVisibility(View.GONE);
            }



        });
    }
}