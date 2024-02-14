package com.example.talevincontacts.model.userAuth;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "userauthtable")
public class UserAuth {
    @PrimaryKey
    @NonNull
    private UUID userId = UUID.randomUUID();
    private String username;
    private String password;

    @NonNull
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(@NonNull UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
