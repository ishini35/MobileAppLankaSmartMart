package com.example.lankasmartmart;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SplashActivity extends AppCompatActivity {

    private TextView tvAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize TextView
        tvAppName = findViewById(R.id.tvAppName);

        // Apply gradient to text
        applyGradientToText();

        // Navigate to Welcome screen after 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void applyGradientToText() {
        tvAppName.post(new Runnable() {
            @Override
            public void run() {
                // Measure text width
                float width = tvAppName.getPaint().measureText(tvAppName.getText().toString());

                // Create gradient shader
                Shader textShader = new LinearGradient(
                        0, 0, width, 0,  // Horizontal gradient (left to right)
                        new int[]{
                                ContextCompat.getColor(SplashActivity.this, R.color.gradient_white_start),
                                ContextCompat.getColor(SplashActivity.this, R.color.gradient_white_end)
                        },
                        null,
                        Shader.TileMode.CLAMP
                );

                tvAppName.getPaint().setShader(textShader);
                tvAppName.invalidate();
            }
        });
    }
}