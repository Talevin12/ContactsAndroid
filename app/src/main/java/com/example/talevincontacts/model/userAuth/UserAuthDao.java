package com.example.talevincontacts.model.userAuth;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.UUID;

@Dao
public interface UserAuthDao {
    @Insert
    void insertUser(UserAuth userAuth);

    @Query("SELECT EXISTS (SELECT * FROM userauthtable where username = :username)")
    LiveData<Boolean> isTaken(String username);

    @Query("SELECT password from userauthtable where username=:username")
    LiveData<String> getPassword(String username);

    @Query("SELECT userId from userauthtable where username=:username")
    LiveData<UUID> getUserId(String username);
}
