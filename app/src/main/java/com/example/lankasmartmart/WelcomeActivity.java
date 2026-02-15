package com.example.lankasmartmart;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnGetStarted;
    private TextView tvWelcome;
    private TextView tvAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Initialize views
        btnGetStarted = findViewById(R.id.btnGetStarted);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvAppName = findViewById(R.id.tvAppName);

        // Apply gradient to "Welcome" text
        applyGradientToText(tvWelcome);

        // Apply gradient to app name
        applyGradientToAppName(tvAppName);

        // Set click listener
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void applyGradientToText(TextView textView) {
        // Get text width
        textView.post(new Runnable() {
            @Override
            public void run() {
                float width = textView.getPaint().measureText(textView.getText().toString());

                // Create gradient shader
                Shader textShader = new LinearGradient(
                        0, 0, width, textView.getTextSize(),
                        new int[]{
                                ContextCompat.getColor(WelcomeActivity.this, R.color.gradient_start),
                                ContextCompat.getColor(WelcomeActivity.this, R.color.gradient_end)
                        },
                        null,
                        Shader.TileMode.CLAMP
                );

                textView.getPaint().setShader(textShader);
                textView.invalidate();
            }
        });
    }

    private void applyGradientToAppName(TextView textView) {
        textView.post(new Runnable() {
            @Override
            public void run() {
                float width = textView.getPaint().measureText(textView.getText().toString());

                // Create gradient shader - green gradient
                Shader textShader = new LinearGradient(
                        0, 0, width, textView.getTextSize(),
                        new int[]{
                                ContextCompat.getColor(WelcomeActivity.this, R.color.primary_green),
                                ContextCompat.getColor(WelcomeActivity.this, R.color.light_green_dark)
                        },
                        null,
                        Shader.TileMode.CLAMP
                );

                textView.getPaint().setShader(textShader);
                textView.invalidate();
            }
        });
    }
}