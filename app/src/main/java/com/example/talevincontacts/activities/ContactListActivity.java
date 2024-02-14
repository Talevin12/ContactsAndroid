package com.example.talevincontacts.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.talevincontacts.adapters.AdapterContacts;
import com.example.talevincontacts.database.ContactsDatabase;
import com.example.talevincontacts.databinding.ActivityContactListBinding;
import com.example.talevincontacts.model.contacts.Contact;
import com.example.talevincontacts.model.contacts.ContactDao;
import com.example.talevincontacts.model.settings.UserSettings;
import com.example.talevincontacts.model.settings.UserSettingsDao;
import com.example.talevincontacts.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity implements AdapterContacts.OnItemListener {
    ActivityContactListBinding binding;
    private ContactDao contactDao;
    private UserSettingsDao userSettingsDao;
    private AdapterContacts adapter_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBtns();
        initDaos();
    }

    private void initDaos() {
        ContactsDatabase contactsDatabase = ContactsDatabase.getInstance(this);
        contactDao = contactsDatabase.getContactDao();
        userSettingsDao = contactsDatabase.getUserSettingsDao();
    }

    private void initBtns() {
        binding.contactListAddContact.setOnClickListener(v ->
                IntentUtils.openPage(this, AddContactActivity.class));

        binding.contactListSettings.setOnClickListener(v ->
                IntentUtils.openPage(this, SettingsActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (LoginActivity.userId == null) {
            finish();
        } else {
            updateList();
        }
    }

    private void updateList() {
        contactDao.getUserContactsByUserId(LoginActivity.userId).observe(this, contactsObserver ->
                userSettingsDao.getUserSettingsByUserId(LoginActivity.userId).observe(this, settingsObserver -> {
                    initContactList(contactsObserver, settingsObserver);
                }));
    }

    private void initContactList(List<Contact> contactsObserver, UserSettings settingsObserver) {
        ArrayList<Contact> contacts = new ArrayList<>(contactsObserver);
        adapter_contacts = new AdapterContacts(contacts, settingsObserver, this);
        binding.contactListContacts.setLayoutManager(new LinearLayoutManager(this));

        if (binding.contactListContacts.getAdapter() == null)
            binding.contactListContacts.setAdapter(adapter_contacts);
        else
            binding.contactListContacts.swapAdapter(adapter_contacts, true);
    }

    @Override
    public void onItemClick(int position, Contact contact) {
        IntentUtils.openPageWithExtras(this, ContactInfoActivity.class,
                new IntentUtils.Extra().setKey("ContactSelected").setObject(contact));
    }
}