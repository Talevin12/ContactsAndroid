package com.example.talevincontacts.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.talevincontacts.R;
import com.example.talevincontacts.api.Retrofit;
import com.example.talevincontacts.callbacks.SaveContactCallback;
import com.example.talevincontacts.database.ContactsDatabase;
import com.example.talevincontacts.databinding.ActivityAddContactBinding;
import com.example.talevincontacts.fragments.ContactInfoFragment;
import com.example.talevincontacts.model.Gender;
import com.example.talevincontacts.model.contacts.ContactDao;
import com.example.talevincontacts.utils.ActivityUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContactActivity extends AppCompatActivity {
    ActivityAddContactBinding binding;
    Retrofit retrofit = new Retrofit();
    private ContactDao contactDao = ContactsDatabase.getInstance(this).getContactDao();
    SaveContactCallback callback = (contact) -> {
        contact.setUserId(LoginActivity.userId);
        contactDao.insertContact(contact);
        finish();
    };

    private ContactInfoFragment contactInfoFragment = new ContactInfoFragment(null, callback);

    Callback<Retrofit.GenderInput> getGenderCallback = new Callback<Retrofit.GenderInput>() {
        @Override
        public void onResponse(Call<Retrofit.GenderInput> call, Response<Retrofit.GenderInput> response) {
            String genderStr = response.body().getGender();
            Gender gender = null;
            switch (genderStr) {
                case "male":
                    gender = Gender.MALE;
                    break;
                case "female":
                    gender = Gender.FEMALE;
                    break;
                case "other":
                    gender = Gender.OTHER;
                    break;
            }

            if (gender != null) {
                contactInfoFragment.setRadioBtnGender(gender);
                ActivityUtils.showShortToast(getBaseContext(), "Gender imported from API");
            }
        }

        @Override
        public void onFailure(Call<Retrofit.GenderInput> call, Throwable t) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initFragment();

        retrofit.getGender(getGenderCallback, "luc");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (LoginActivity.userId == null) {
            finish();
        }
    }

    private void initFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.add_contact_fragment, contactInfoFragment);
        fragmentTransaction.commit();
    }
}