package com.example.talevincontacts.model.settings;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.UUID;

@Dao
public interface UserSettingsDao {
    @Insert
    void insertUserSettings(UserSettings userSettings);

    @Update
    void updateUserSettings(UserSettings userSettings);

    @Delete
    void deleteUserSettings(UserSettings userSettings);

    @Query("SELECT * FROM usersettings WHERE userId = :userId")
    LiveData<UserSettings> getUserSettingsByUserId(UUID userId);
}
