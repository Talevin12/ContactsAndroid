package com.example.talevincontacts.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.talevincontacts.converters.DateConverter;
import com.example.talevincontacts.converters.UUIDConverter;
import com.example.talevincontacts.model.contacts.Contact;
import com.example.talevincontacts.model.contacts.ContactDao;
import com.example.talevincontacts.model.settings.UserSettings;
import com.example.talevincontacts.model.settings.UserSettingsDao;
import com.example.talevincontacts.model.userAuth.UserAuth;
import com.example.talevincontacts.model.userAuth.UserAuthDao;

@TypeConverters({UUIDConverter.class, DateConverter.class})
@Database(entities = {UserAuth.class, Contact.class, UserSettings.class}, version = 4, exportSchema = false)
public abstract class ContactsDatabase extends RoomDatabase {
    private static volatile ContactsDatabase INSTANCE;

    public static ContactsDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ContactsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ContactsDatabase.class, "ContactsDB")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserAuthDao getUserAuthDao();

    public abstract ContactDao getContactDao();

    public abstract UserSettingsDao getUserSettingsDao();
}
