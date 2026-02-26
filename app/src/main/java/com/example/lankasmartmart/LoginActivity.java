package com.example.lankasmartmart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton;
    TextView signUpText, forgotPasswordText, googleSignInText;
    TextView appTitleText;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Initialize views
        emailEditText    = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton      = findViewById(R.id.loginButton);
        signUpText       = findViewById(R.id.signUpText);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);
        googleSignInText = findViewById(R.id.googleSignInText);

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

        // Login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email    = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validation
                if (email.isEmpty()) {
                    emailEditText.setError("Please enter your email!");
                    return;
                }
                if (password.isEmpty()) {
                    passwordEditText.setError("Please enter your password!");
                    return;
                }

                // Check database with plain password
                boolean userExists = databaseHelper.checkUser(email, password);

                if (userExists) {
                    // SUCCESS
                    Toast.makeText(LoginActivity.this,
                            "Login Successful!",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("USER_EMAIL", email);
                    startActivity(intent);
                    finish();

                } else {
                    // FAILED
                    Toast.makeText(LoginActivity.this,
                            "Wrong email or password!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Sign up click
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Forgot password click
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,
                        "Forgot Password clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Google sign in click
        googleSignInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,
                        "Google Sign-In clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}