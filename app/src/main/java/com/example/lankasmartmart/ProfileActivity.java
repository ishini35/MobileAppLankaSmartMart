package com.example.lankasmartmart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    // UI Components
    private ImageView btnBack, profilePicture;
    private TextView userName, userEmail, userEmailDetail, userPhone, userLocation;
    private TextView homeAddress, officeAddress;
    private LinearLayout homeAddressLayout, officeAddressLayout;
    private LinearLayout navHome, navCategories, navCart, navProfile;
    private ImageView btnSettings;

    // Data
    private DatabaseHelper databaseHelper;
    private int currentUserId;
    private String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Get current user
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        currentUserId = prefs.getInt("USER_ID", -1);
        currentUserEmail = prefs.getString("USER_EMAIL", "");

        // Initialize views
        initializeViews();

        // Load user data
        loadUserData();

        // Load addresses
        loadAddresses();

        // Setup listeners
        setupListeners();
    }

    private void initializeViews() {
        // Top bar
        btnBack = findViewById(R.id.btnBack);
        btnSettings = findViewById(R.id.btnSettings);

        // Profile info
        profilePicture = findViewById(R.id.profilePicture);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);

        // Contact info
        userPhone = findViewById(R.id.userPhone);
        userEmailDetail = findViewById(R.id.userEmailDetail);
        userLocation = findViewById(R.id.userLocation);

        // Addresses
        homeAddressLayout = findViewById(R.id.homeAddressLayout);
        officeAddressLayout = findViewById(R.id.officeAddressLayout);
        homeAddress = findViewById(R.id.homeAddress);
        officeAddress = findViewById(R.id.officeAddress);

        // Bottom navigation
        navHome = findViewById(R.id.navHome);
        navCategories = findViewById(R.id.navCategories);
        navCart = findViewById(R.id.navCart);
        navProfile = findViewById(R.id.navProfile);
    }

    private void loadUserData() {
        if (currentUserId == -1) {
            Toast.makeText(this, R.string.user_not_logged_in, Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = databaseHelper.getUserByEmail(currentUserEmail);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("full_name"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));

                // Set user info
                userName.setText(name);
                userEmail.setText(email);
                userEmailDetail.setText(email);

                if (phone != null && !phone.isEmpty()) {
                    userPhone.setText(phone);
                } else {
                    userPhone.setText(R.string.not_provided);
                }

                // Set default location (you can make this dynamic)
                userLocation.setText(R.string.default_location);

            } catch (IllegalArgumentException e) {
                Toast.makeText(this, "Error loading user data", Toast.LENGTH_SHORT).show();
            } finally {
                cursor.close();
            }
        }
    }

    private void loadAddresses() {
        Cursor cursor = databaseHelper.getUserAddresses(currentUserId);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                do {
                    String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                    String addressLine = cursor.getString(cursor.getColumnIndexOrThrow("address_line"));
                    String city = cursor.getString(cursor.getColumnIndexOrThrow("city"));

                    String fullAddress = addressLine + ", " + city;

                    if (type.equals("Home")) {
                        homeAddress.setText(fullAddress);
                    } else if (type.equals("Office")) {
                        officeAddress.setText(fullAddress);
                    }

                } while (cursor.moveToNext());
            } catch (IllegalArgumentException e) {
                Toast.makeText(this, "Error loading addresses", Toast.LENGTH_SHORT).show();
            } finally {
                cursor.close();
            }
        } else {
            homeAddress.setText(R.string.no_home_address);
            officeAddress.setText(R.string.no_office_address);
        }
    }

    private void setupListeners() {
        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Settings button
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // Profile picture click (edit profile)
        profilePicture.setOnClickListener(v ->
                Toast.makeText(ProfileActivity.this, R.string.edit_profile_picture, Toast.LENGTH_SHORT).show()
        );

        // Home address click
        homeAddressLayout.setOnClickListener(v ->
                Toast.makeText(ProfileActivity.this, R.string.edit_home_address, Toast.LENGTH_SHORT).show()
        );

        // Office address click
        officeAddressLayout.setOnClickListener(v ->
                Toast.makeText(ProfileActivity.this, R.string.edit_office_address, Toast.LENGTH_SHORT).show()
        );

        // Bottom navigation
        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        navCategories.setOnClickListener(v ->
                Toast.makeText(ProfileActivity.this, "Categories", Toast.LENGTH_SHORT).show()
        );

        navCart.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, CartActivity.class);
            startActivity(intent);
        });

        navProfile.setOnClickListener(v ->
                Toast.makeText(ProfileActivity.this, R.string.already_on_profile, Toast.LENGTH_SHORT).show()
        );
    }
}