package com.example.talevincontacts.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.talevincontacts.database.ContactsDatabase;
import com.example.talevincontacts.databinding.ActivitySettingsBinding;
import com.example.talevincontacts.model.settings.UserSettings;
import com.example.talevincontacts.model.settings.UserSettingsDao;
//import com.example.talevincontacts.model.settings.UserSettings;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;

    private UserSettingsDao userSettingsDao = ContactsDatabase.getInstance(this).getUserSettingsDao();

    private UserSettings userSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserSettingsFromDB();

        binding.saveSettingsButton.setOnClickListener(v -> {
            updateUserSettings();

            userSettingsDao.updateUserSettings(userSettings);
        });

        binding.settingsLogoutBtn.setOnClickListener(v -> {
            LoginActivity.userId = null;
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (LoginActivity.userId == null) {
            finish();
        }
    }

    private void getUserSettingsFromDB() {
        userSettingsDao.getUserSettingsByUserId(LoginActivity.userId).observe(this, userSettingsO -> {
            userSettings = userSettingsO;
            setTogglesBySettings();
        });
    }

    private void updateUserSettings() {
        userSettings.setShowPhone(binding.showPhoneNumberToggle.isChecked());
        userSettings.setShowEmail(binding.showEmailToggle.isChecked());
        userSettings.setShowDOB(binding.showBirthdayToggle.isChecked());
        userSettings.setShowGender(binding.showGenderToggle.isChecked());
    }

    private void setTogglesBySettings() {
        binding.showPhoneNumberToggle.setChecked(userSettings.isShowPhone());
        binding.showEmailToggle.setChecked(userSettings.isShowEmail());
        binding.showBirthdayToggle.setChecked(userSettings.isShowDOB());
        binding.showGenderToggle.setChecked(userSettings.isShowGender());
    }
}