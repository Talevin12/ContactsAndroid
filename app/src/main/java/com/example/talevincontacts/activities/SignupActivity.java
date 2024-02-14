package com.example.talevincontacts.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.talevincontacts.database.ContactsDatabase;
import com.example.talevincontacts.databinding.ActivitySignupBinding;
import com.example.talevincontacts.model.settings.UserSettings;
import com.example.talevincontacts.model.settings.UserSettingsDao;
import com.example.talevincontacts.model.userAuth.UserAuth;
import com.example.talevincontacts.model.userAuth.UserAuthDao;
import com.example.talevincontacts.utils.ActivityUtils;
import com.example.talevincontacts.utils.IntentUtils;

import org.mindrot.jbcrypt.BCrypt;

public class SignupActivity extends AppCompatActivity {
    private static final int MIN_USERNAME_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9._]+$"; // alphanumeric, underscores, and periods
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]+$"; //lowercase, uppercase, and digit
    ActivitySignupBinding binding;
    UserAuthDao userAuthDao;
    UserSettingsDao userSettingsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userAuthDao = ContactsDatabase.getInstance(this).getUserAuthDao();
        userSettingsDao = ContactsDatabase.getInstance(this).getUserSettingsDao();

        binding.signupBtn.setOnClickListener(view -> {
            String username = binding.signupUsername.getText().toString().trim();
            String password = binding.signupPassword.getText().toString();

            try {
                checkInputsText(username, password);

                userAuthDao.isTaken(username).observe(this, isTakenO -> {
                    if (!isTakenO) {
                        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                        saveNewUser(username, hashedPassword);

                        IntentUtils.openPage(SignupActivity.this, LoginActivity.class);
                        finish();
                    } else {
                        ActivityUtils.showShortToast(SignupActivity.this, "Username Already Exists");
                    }
                });
            } catch (Exception e) {
                ActivityUtils.showLongToast(this, e.getMessage());
            }
        });

        binding.goToLogin.setOnClickListener(view ->

        {
            IntentUtils.openPage(SignupActivity.this, LoginActivity.class);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.signupUsername.setText("");
        binding.signupPassword.setText("");
    }

    private void saveNewUser(String username, String hashedPassword) {
        UserAuth userAuth = new UserAuth();
        userAuth.setUsername(username);
        userAuth.setPassword(hashedPassword);

        userAuthDao.insertUser(userAuth);

        saveSettingsForNewUser(userAuth);
    }

    private void saveSettingsForNewUser(UserAuth userAuth) {
        UserSettings initUserSettings = new UserSettings();
        initUserSettings.setUserId(userAuth.getUserId());
        userSettingsDao.insertUserSettings(initUserSettings);
    }

    private void checkInputsText(String username, String password) throws Exception {
        checkUsername(username);
        checkPassword(password);
    }

    private void checkUsername(String username) throws Exception {
        if (username == null || username.isEmpty()) {
            throw new Exception("Username Empty");
        }
        if (username.length() < MIN_USERNAME_LENGTH) {
            throw new Exception("Minimum username length = " + MIN_USERNAME_LENGTH);
        }
        if (!username.matches(USERNAME_PATTERN)) {
            throw new Exception("Username only alphanumeric, underscores, and periods");
        }
    }

    private void checkPassword(String password) throws Exception {
        if (password == null || password.isEmpty()) {
            throw new Exception("Password Empty");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new Exception("Minimum password length = " + MIN_PASSWORD_LENGTH);
        }
        if (!password.matches(PASSWORD_PATTERN)) {
            throw new Exception("Password only lowercase, uppercase, and digit");
        }
    }
}