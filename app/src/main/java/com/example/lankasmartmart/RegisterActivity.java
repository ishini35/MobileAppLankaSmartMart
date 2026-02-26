package com.example.lankasmartmart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    CheckBox termsCheckBox;
    Button registerButton;
    TextView logInText;
    TextView appTitleText;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Initialize views
        nameEditText            = findViewById(R.id.nameEditText);
        emailEditText           = findViewById(R.id.emailEditText);
        passwordEditText        = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        termsCheckBox           = findViewById(R.id.termsCheckBox);
        registerButton          = findViewById(R.id.registerButton);
        logInText               = findViewById(R.id.logInText);

        // Gradient
        appTitleText = findViewById(R.id.appTitleText);
        TextPaint paint = appTitleText.getPaint();
        float width = paint.measureText("Lanka Smart Mart");
        Shader textShader = new LinearGradient(
                0, 0, 0, appTitleText.getTextSize(),
                new int[]{
                        Color.parseColor("#205C3D"),
                        Color.parseColor("#42D78A"),
                },
                null,
                Shader.TileMode.CLAMP
        );
        appTitleText.getPaint().setShader(textShader);

        // Register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name            = nameEditText.getText().toString().trim();
                String email           = emailEditText.getText().toString().trim();
                String password        = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                // Validation
                if (name.isEmpty()) {
                    nameEditText.setError("Please enter your name!");
                    return;
                }
                if (email.isEmpty()) {
                    emailEditText.setError("Please enter your email!");
                    return;
                }
                if (password.isEmpty()) {
                    passwordEditText.setError("Please enter your password!");
                    return;
                }
                if (confirmPassword.isEmpty()) {
                    confirmPasswordEditText.setError("Please confirm password!");
                    return;
                }
                if (password.length() < 6) {
                    passwordEditText.setError("Password must be 6+ characters!");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    confirmPasswordEditText.setError("Passwords don't match!");
                    return;
                }
                if (!termsCheckBox.isChecked()) {
                    Toast.makeText(RegisterActivity.this,
                            "Please accept Terms & Conditions",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check duplicate email
                if (databaseHelper.checkEmailExists(email)) {
                    emailEditText.setError("Email already registered!");
                    return;
                }

                // Save to database - plain text password
                boolean success = databaseHelper.addUser(
                        name,
                        email,
                        password, // Plain text - no hashing!
                        ""        // Phone empty for now
                );

                if (success) {
                    // SUCCESS
                    Toast.makeText(RegisterActivity.this,
                            "Registration Successful!",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    // FAILED
                    Toast.makeText(RegisterActivity.this,
                            "Registration Failed! Try again.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Login link
        logInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Terms checkbox toggle
    public void onTermsTextClick(View view) {
        termsCheckBox.setChecked(!termsCheckBox.isChecked());
    }
}