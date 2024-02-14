package com.example.talevincontacts.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.talevincontacts.R;
import com.example.talevincontacts.callbacks.SaveContactCallback;
import com.example.talevincontacts.database.ContactsDatabase;
import com.example.talevincontacts.databinding.ActivityContactInfoBinding;
import com.example.talevincontacts.fragments.ContactInfoFragment;
import com.example.talevincontacts.model.contacts.Contact;
import com.example.talevincontacts.model.contacts.ContactDao;

public class ContactInfoActivity extends AppCompatActivity {
    ActivityContactInfoBinding binding;

    private ContactInfoFragment contactInfoFragment;

    private ContactDao contactDao = ContactsDatabase.getInstance(this).getContactDao();

    private Contact contact;

    SaveContactCallback callback = (updatedContact) -> {
        binding.contactInfoEdit.setVisibility(View.VISIBLE);
        binding.contactInfoFinishEdit.setVisibility(View.INVISIBLE);

        contactInfoFragment.disableInputs();

        if (!contact.equals(updatedContact)) {
            contact.updateContact(updatedContact);
            contactDao.updateContact(contact);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contact = (Contact) getIntent().getExtras().getSerializable("ContactSelected");

        initFragment();

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (LoginActivity.userId == null) {
            finish();
        }
    }

    private void initViews() {
        initEditBtn();

        initCancelEditBtn();

        initDeleteBtn();
    }

    private void initDeleteBtn() {
        binding.contactInfoDelete.setOnClickListener(v -> {
            contactDao.deleteContact(contact);
            finish();
        });
    }

    private void initCancelEditBtn() {
        binding.contactInfoFinishEdit.setOnClickListener(v -> {
            contactInfoFragment.disableInputs();

            binding.contactInfoEdit.setVisibility(View.VISIBLE);
            binding.contactInfoFinishEdit.setVisibility(View.INVISIBLE);
            binding.contactInfoDelete.setVisibility(View.VISIBLE);
        });
    }

    private void initEditBtn() {
        binding.contactInfoEdit.setOnClickListener(v -> {
            contactInfoFragment.enableInputs();

            binding.contactInfoEdit.setVisibility(View.INVISIBLE);
            binding.contactInfoFinishEdit.setVisibility(View.VISIBLE);
            binding.contactInfoDelete.setVisibility(View.INVISIBLE);
        });
    }

    private void initFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        contactInfoFragment = new ContactInfoFragment(contact, callback);
        fragmentTransaction.replace(R.id.contact_info_fragment, contactInfoFragment);
        fragmentTransaction.commit();
    }
}