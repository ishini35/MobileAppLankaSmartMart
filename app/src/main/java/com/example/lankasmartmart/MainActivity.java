package com.example.lankasmartmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView welcomeText;
    EditText searchEditText;
    LinearLayout groceriesCategory, householdCategory, personalCareCategory, stationeryCategory;
    LinearLayout navHome, navCategories, navCart, navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeText = findViewById(R.id.welcomeText);
        searchEditText = findViewById(R.id.searchEditText);

        groceriesCategory = findViewById(R.id.groceriesCategory);
        householdCategory = findViewById(R.id.householdCategory);
        personalCareCategory = findViewById(R.id.personalCareCategory);
        stationeryCategory = findViewById(R.id.stationeryCategory);

        navHome = findViewById(R.id.navHome);
        navCategories = findViewById(R.id.navCategories);
        navCart = findViewById(R.id.navCart);
        navProfile = findViewById(R.id.navProfile);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");
        if (userName != null) {
            welcomeText.setText("Hello, " + userName + " !");
        }

        groceriesCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Groceries clicked", Toast.LENGTH_SHORT).show();
            }
        });

        householdCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Household clicked", Toast.LENGTH_SHORT).show();
            }
        });

        personalCareCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Personal Care clicked", Toast.LENGTH_SHORT).show();
            }
        });

        stationeryCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Stationery clicked", Toast.LENGTH_SHORT).show();
            }
        });

        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
            }
        });

        navCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Categories", Toast.LENGTH_SHORT).show();
            }
        });

        navCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Cart", Toast.LENGTH_SHORT).show();
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                finish();
            }
        });
    }
}