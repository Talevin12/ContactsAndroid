package com.example.talevincontacts.model.settings;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.talevincontacts.model.userAuth.UserAuth;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "usersettings",
        foreignKeys = {
                @ForeignKey(entity = UserAuth.class,
                        parentColumns = "userId",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE)
        })
public class UserSettings implements Serializable {
    @PrimaryKey
    @NonNull
    private UUID userId;
    private boolean showPhone = true;
    private boolean showEmail = true;
    private boolean showDOB = true;
    private boolean showGender = true;

    @NonNull
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(@NonNull UUID userId) {
        this.userId = userId;
    }

    public boolean isShowPhone() {
        return showPhone;
    }

    public void setShowPhone(boolean showPhone) {
        this.showPhone = showPhone;
    }

    public boolean isShowEmail() {
        return showEmail;
    }

    public void setShowEmail(boolean showEmail) {
        this.showEmail = showEmail;
    }

    public boolean isShowDOB() {
        return showDOB;
    }

    public void setShowDOB(boolean showDOB) {
        this.showDOB = showDOB;
    }

    public boolean isShowGender() {
        return showGender;
    }

    public void setShowGender(boolean showGender) {
        this.showGender = showGender;
    }
}
