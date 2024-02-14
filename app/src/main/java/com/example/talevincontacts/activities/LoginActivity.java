package com.example.talevincontacts.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.talevincontacts.database.ContactsDatabase;
import com.example.talevincontacts.databinding.ActivityLoginBinding;
import com.example.talevincontacts.model.userAuth.UserAuthDao;
import com.example.talevincontacts.utils.ActivityUtils;
import com.example.talevincontacts.utils.IntentUtils;

import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class LoginActivity extends AppCompatActivity {
    public static UUID userId;
    ActivityLoginBinding binding;
    UserAuthDao userAuthDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userAuthDao = ContactsDatabase.getInstance(this).getUserAuthDao();

        binding.loginBtn.setOnClickListener(view -> {
            String username = binding.loginUsername.getText().toString();
            String password = binding.loginPassword.getText().toString();

            if (!(username.isEmpty() || password.isEmpty())) {
                userAuthDao.getPassword(username).observe(this, passwordO -> {
                    if (passwordO != null) {
                        if (BCrypt.checkpw(password, passwordO)) {

                            userAuthDao.getUserId(username).observe(this, userIdO -> {
                                userId = userIdO;

                                ActivityUtils.showShortToast(LoginActivity.this, "Login Successful");

                                IntentUtils.openPageWithExtras(
                                        LoginActivity.this,
                                        ContactListActivity.class,
                                        new IntentUtils.Extra()
                                                .setKey("userId")
                                                .setObject(userIdO)
                                );
                            });
                        } else {
                            ActivityUtils.showShortToast(LoginActivity.this, "Invalid Password");
                        }
                    } else {
                        ActivityUtils.showShortToast(LoginActivity.this, "Invalid Username");
                    }
                });
            }
        });

        binding.goToSignup.setOnClickListener(view -> {
            IntentUtils.openPage(LoginActivity.this, SignupActivity.class);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.loginUsername.setText("");
        binding.loginPassword.setText("");
    }
}