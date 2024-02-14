package com.example.talevincontacts.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.talevincontacts.R;
import com.example.talevincontacts.callbacks.SaveContactCallback;
import com.example.talevincontacts.model.Gender;
import com.example.talevincontacts.model.contacts.Contact;
import com.example.talevincontacts.utils.ActivityUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.regex.Pattern;


public class ContactInfoFragment extends Fragment {
    final String TITLE_CREATE = "Create Contact";
    final String TITLE_INFO = "Contact Info";
    final String ERROR_FNAME = "* Fill First name\n";
    final String ERROR_PHONE = "* Fill phone\n";
    final String ERROR_EMAIL = "* Wrong email format\n";

    private final SaveContactCallback callback;
    private Contact contact;

    private TextView titleTextView;
    private TextInputLayout firstNameLayout;
    private TextInputEditText editTextFirstName;
    private TextInputEditText editTextLastName;
    private TextInputLayout phoneLayout;
    private TextInputEditText editTextPhone;
    private TextInputLayout emailLayout;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextDateOfBirth;
    private RadioGroup radioGender;
    private MaterialRadioButton radioButtonMale;
    private MaterialRadioButton radioButtonFemale;
    private MaterialRadioButton radioButtonOther;
    private MaterialButton submitButton;

    public ContactInfoFragment(Contact contact, SaveContactCallback callback) {
        this.callback = callback;
        this.contact = contact;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handleNewOrUpdateContact();

        editTextDateOfBirth.setOnClickListener(v -> showDatePickerDialog());

        initSubmitBtn();
    }

    private void initSubmitBtn() {
        submitButton.setOnClickListener(v -> {
            try {
                Contact contact = validateForm();
                callback.contactSaved(contact);
            } catch (Exception e) {
                ActivityUtils.showLongToast(getActivity(), e.getMessage());
            }
        });
    }

    private void handleNewOrUpdateContact() {
        if (contact == null) {
            titleTextView.setText(TITLE_CREATE);
        } else {
            titleTextView.setText(TITLE_INFO);

            setInfoFromContact();

            disableInputs();
        }
    }

    private void setInfoFromContact() {
        setEditTextFirstName(contact.getFirstName());
        setEditTextLastName(contact.getLastName());
        setEditTextPhone(contact.getPhoneNumber());
        setEditTextEmail(contact.getEmail());
        setEditTextDateOfBirth(contact.getDateOfBirth());
        setRadioBtnGender(contact.getGender());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_info, container, false);

        findViews(view);

        return view;
    }

    private void findViews(View view) {
        titleTextView = view.findViewById(R.id.frag_contact_info_title);
        firstNameLayout = view.findViewById(R.id.frag_contact_info_firstNameLayout);
        editTextFirstName = view.findViewById(R.id.frag_contact_info_editTextFirstName);
        editTextLastName = view.findViewById(R.id.frag_contact_info_editTextLastName);
        phoneLayout = view.findViewById(R.id.frag_contact_info_phoneLayout);
        editTextPhone = view.findViewById(R.id.frag_contact_info_editTextPhone);
        emailLayout = view.findViewById(R.id.frag_contact_info_emailLayout);
        editTextEmail = view.findViewById(R.id.frag_contact_info_editTextEmail);
        editTextDateOfBirth = view.findViewById(R.id.frag_contact_info_editTextDateOfBirth);
        radioGender = view.findViewById(R.id.frag_contact_info_radioGender);
        radioButtonMale = view.findViewById(R.id.frag_contact_info_radioButtonMale);
        radioButtonFemale = view.findViewById(R.id.frag_contact_info_radioButtonFemale);
        radioButtonOther = view.findViewById(R.id.frag_contact_info_radioButtonOther);
        submitButton = view.findViewById(R.id.frag_contact_info_submitButton);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year1, monthOfYear, dayOfMonth1) ->
                        editTextDateOfBirth.setText(LocalDate.of(year1, Month.of(monthOfYear + 1), dayOfMonth1).toString()),
                year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    private Contact validateForm() throws Exception {
        String errorMsg = "";

        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        LocalDate dateOfBirth = getDateFromInput();
        Gender gender = getGenderSelection();

        checkInputs(errorMsg, firstName, phone, email);

        Contact newContact = new Contact();
        newContact.setFirstName(firstName);
        newContact.setLastName(lastName);
        newContact.setPhoneNumber(phone);
        newContact.setEmail(email);
        newContact.setDateOfBirth(dateOfBirth);
        newContact.setGender(gender);

        return newContact;
    }

    private LocalDate getDateFromInput() {
        String dateOfBirthStr = editTextDateOfBirth.getText().toString();
        if (!dateOfBirthStr.isEmpty()) {
            return LocalDate.parse(dateOfBirthStr);
        }
        return null;
    }

    @Nullable
    private Gender getGenderSelection() {
        if (radioButtonMale.isChecked())
            return Gender.MALE;
        else if (radioButtonFemale.isChecked())
            return Gender.FEMALE;
        else if (radioButtonOther.isChecked())
            return Gender.OTHER;

        return null;
    }

    private void checkInputs(String errorMsg, String firstName, String phone, String email) throws Exception {
        if (TextUtils.isEmpty(firstName)) {
            firstNameLayout.setBoxStrokeColor(Color.RED);
            errorMsg += ERROR_FNAME;
        }
        if (TextUtils.isEmpty(phone)) {
            phoneLayout.setBoxStrokeColor(Color.RED);
            errorMsg += ERROR_PHONE;
        }
        if (!TextUtils.isEmpty(email)) {
            if (!checkEmail(email)) {
                emailLayout.setBoxStrokeColor(Color.RED);
                errorMsg += ERROR_EMAIL;
            }
        }

        if (!errorMsg.equals("")) {
            throw new Exception(errorMsg);
        }
    }

    private boolean checkEmail(String email) {
        String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        return pattern.matcher(email).matches();
    }

    public void disableInputs() {
        editTextFirstName.setEnabled(false);
        editTextLastName.setEnabled(false);
        editTextPhone.setEnabled(false);
        editTextEmail.setEnabled(false);
        editTextDateOfBirth.setEnabled(false);
        setEnabledForGendersBtns(false);
        submitButton.setVisibility(View.GONE);
    }

    public void enableInputs() {
        editTextFirstName.setEnabled(true);
        editTextLastName.setEnabled(true);
        editTextPhone.setEnabled(true);
        editTextEmail.setEnabled(true);
        editTextDateOfBirth.setEnabled(true);
        setEnabledForGendersBtns(true);
        submitButton.setVisibility(View.VISIBLE);
    }

    public void setEnabledForGendersBtns(boolean enabled) {
        for (int i = 0; i < radioGender.getChildCount(); i++) {
            radioGender.getChildAt(i).setEnabled(enabled);
        }
    }

    public void setEditTextFirstName(String firstName) {
        editTextFirstName.setText(firstName);
    }

    public void setEditTextLastName(String lastName) {
        if (lastName != null)
            editTextLastName.setText(lastName);
    }

    public void setEditTextPhone(String phone) {
        editTextPhone.setText(phone);
    }

    public void setEditTextEmail(String email) {
        if (email != null)
            editTextEmail.setText(email);
    }

    public void setEditTextDateOfBirth(LocalDate date) {
        if (date != null)
            editTextDateOfBirth.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public void setRadioBtnGender(Gender gender) {
        if (gender != null) {
            switch (gender) {
                case MALE:
                    radioButtonMale.setChecked(true);
                    break;
                case FEMALE:
                    radioButtonFemale.setChecked(true);
                    break;
                case OTHER:
                    radioButtonOther.setChecked(true);
                    break;
            }
        }
    }
}