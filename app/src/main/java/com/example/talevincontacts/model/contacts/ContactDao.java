package com.example.talevincontacts.model.contacts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

@Dao
public interface ContactDao {
    @Insert
    void insertContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Query("SELECT * FROM contacttable WHERE contactId = :contactId")
    Contact getUserContactByContactId(int contactId);

    @Query("SELECT * " +
            "FROM contacttable " +
            "WHERE userId = :userId")
    LiveData<List<Contact>> getUserContactsByUserId(UUID userId);
}
