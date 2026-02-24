package com.example.lankasmartmart;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BarcodeScannerActivity extends AppCompatActivity {

    View btnBack;
    Button btnManualEntry, btnHelp;
    View scannerLine;
    TextView scanningStatus;
    LinearLayout navHome, navCategories, navCart, navProfile;

    ObjectAnimator scanAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        // Init views
        btnBack        = findViewById(R.id.btnBack);
        btnManualEntry = findViewById(R.id.btnManualEntry);
        btnHelp        = findViewById(R.id.btnHelp);
        scannerLine    = findViewById(R.id.scannerLine);
        scanningStatus = findViewById(R.id.scanningStatus);
        navHome        = findViewById(R.id.navHome);
        navCategories  = findViewById(R.id.navCategories);
        navCart        = findViewById(R.id.navCart);
        navProfile     = findViewById(R.id.navProfile);

        // Start scanner line animation
        startScanAnimation();

        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Manual Entry — show dialog for user to type barcode
        btnManualEntry.setOnClickListener(v -> showManualEntryDialog());

        // Help button
        btnHelp.setOnClickListener(v -> showHelpDialog());

        // Bottom Navigation
        navHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        navCategories.setOnClickListener(v ->
                Toast.makeText(this, "Categories", Toast.LENGTH_SHORT).show()
        );

        navCart.setOnClickListener(v ->
                startActivity(new Intent(this, CartActivity.class))
        );

        navProfile.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class))
        );
    }

    // Animate the green scanning line up and down
    private void startScanAnimation() {
        scannerLine.post(() -> {
            int parentHeight = ((View) scannerLine.getParent()).getHeight();
            scanAnimator = ObjectAnimator.ofFloat(scannerLine, "translationY", 0f, parentHeight - 4f);
            scanAnimator.setDuration(2000);
            scanAnimator.setRepeatCount(ValueAnimator.INFINITE);
            scanAnimator.setRepeatMode(ValueAnimator.REVERSE);
            scanAnimator.setInterpolator(new LinearInterpolator());
            scanAnimator.start();
        });
    }

    // Manual entry dialog
    private void showManualEntryDialog() {
        android.widget.EditText input = new android.widget.EditText(this);
        input.setHint("Enter barcode number");
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        input.setPadding(40, 20, 40, 20);

        new AlertDialog.Builder(this)
                .setTitle("Manual Entry")
                .setMessage("Enter the product barcode manually:")
                .setView(input)
                .setPositiveButton("Search", (dialog, which) -> {
                    String barcode = input.getText().toString().trim();
                    if (!barcode.isEmpty()) {
                        handleBarcodeResult(barcode);
                    } else {
                        Toast.makeText(this, "Please enter a barcode", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Help dialog
    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle("How to Scan")
                .setMessage("1. Hold your phone steady\n\n" +
                        "2. Align the barcode within the green frame\n\n" +
                        "3. The app will automatically detect and search for the product\n\n" +
                        "4. If scanning fails, use Manual Entry to type the barcode number")
                .setPositiveButton("Got it", null)
                .show();
    }

    // Handle scanned or entered barcode
    private void handleBarcodeResult(String barcode) {
        scanningStatus.setText("Found: " + barcode);
        Toast.makeText(this, "Searching for product: " + barcode, Toast.LENGTH_SHORT).show();
        // TODO: Search product in database by barcode and navigate to product detail
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scanAnimator != null) scanAnimator.cancel();
    }
}