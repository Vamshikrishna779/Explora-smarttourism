package com.example.smarttourism;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.button.MaterialButton;

public class SettingsActivity extends AppCompatActivity {

    private SwitchMaterial switchDarkMode, switchNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Back Button
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Correctly reference Material Switches
        switchDarkMode = findViewById(R.id.switchDarkMode);
        switchNotifications = findViewById(R.id.switchNotifications);

        // Shared Preferences for settings
        SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isDarkMode = preferences.getBoolean("DarkMode", false);
        boolean isNotificationsEnabled = preferences.getBoolean("Notifications", true);

        switchDarkMode.setChecked(isDarkMode);
        switchNotifications.setChecked(isNotificationsEnabled);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("DarkMode", isChecked);
            editor.apply();
            Toast.makeText(this, "Dark Mode " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
        });

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("Notifications", isChecked);
            editor.apply();
            Toast.makeText(this, "Notifications " + (isChecked ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
        });

        // Language Button
        MaterialButton btnLanguage = findViewById(R.id.btnLanguage);
        btnLanguage.setOnClickListener(v -> {
            Toast.makeText(this, "Language Change Coming Soon!", Toast.LENGTH_SHORT).show();
        });

        // Privacy Policy
        MaterialButton btnPrivacy = findViewById(R.id.btnPrivacy);
        btnPrivacy.setOnClickListener(v -> {
            Toast.makeText(this, "Privacy Policy feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        // Logout Button
        MaterialButton btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logout feature coming soon! For now, it's free access.", Toast.LENGTH_SHORT).show();
        });
    }
}
