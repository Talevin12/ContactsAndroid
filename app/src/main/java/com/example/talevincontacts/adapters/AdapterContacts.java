package com.example.talevincontacts.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talevincontacts.R;
import com.example.talevincontacts.model.Gender;
import com.example.talevincontacts.model.contacts.Contact;
import com.example.talevincontacts.model.settings.UserSettings;
import com.google.android.material.textview.MaterialTextView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class AdapterContacts extends RecyclerView.Adapter<AdapterContacts.ContactViewHolder> {
    private final OnItemListener onItemListener;
    private ArrayList<Contact> contacts;
    private UserSettings userSettings;

    public AdapterContacts(ArrayList<Contact> contacts, UserSettings userSettings, OnItemListener onItemListener) {
        this.contacts = contacts;
        contacts.sort(Comparator.comparing(Contact::getFirstName)
                .thenComparing(Contact::getLastName));

        this.userSettings = userSettings;

        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);

        holder.contact_name.setText(contact.getFirstName() + " " + contact.getLastName());

        handlePhoneView(holder, contact);

        handleEmailView(holder, contact);

        handleDOBView(holder, contact);

        handleGenderView(holder, contact);
    }

    private void handleGenderView(@NonNull ContactViewHolder holder, Contact contact) {
        if (userSettings.isShowGender()) {
            if (contact.getGender() == Gender.FEMALE)
                holder.contact_image.setColorFilter(Color.argb(255, 255, 192, 203));
            else if (contact.getGender() == Gender.MALE)
                holder.contact_image.setColorFilter(Color.argb(255, 0, 191, 255));
            else
                holder.contact_image.setColorFilter(Color.argb(255, 0, 0, 0));
        } else
            holder.contact_image.setColorFilter(Color.argb(255, 0, 0, 0));
    }

    private void handleDOBView(@NonNull ContactViewHolder holder, Contact contact) {
        if (userSettings.isShowDOB() && contact.getDateOfBirth() != null) {
            holder.contact_dob.setVisibility(View.VISIBLE);
            holder.contact_dob.setText(contact.getDateOfBirth().format(DateTimeFormatter.ofPattern("MMM d, yyyy")));
        } else {
            holder.contact_dob.setVisibility(View.GONE);
        }
    }

    private void handleEmailView(@NonNull ContactViewHolder holder, Contact contact) {
        if (userSettings.isShowEmail() && contact.getEmail() != null && !contact.getEmail().isEmpty()) {
            holder.contact_email.setVisibility(View.VISIBLE);
            holder.contact_email.setText(contact.getEmail());
        } else {
            holder.contact_email.setVisibility(View.GONE);
        }
    }

    private void handlePhoneView(@NonNull ContactViewHolder holder, Contact contact) {
        if (userSettings.isShowPhone()) {
            holder.contact_phone.setVisibility(View.VISIBLE);
            holder.contact_phone.setText(contact.getPhoneNumber());
        } else {
            holder.contact_phone.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, Contact contact);
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final AdapterContacts.OnItemListener onItemListener;
        private AppCompatImageView contact_image;
        private MaterialTextView contact_name;
        private MaterialTextView contact_phone;
        private MaterialTextView contact_email;
        private MaterialTextView contact_dob;


        public ContactViewHolder(@NonNull View itemView, AdapterContacts.OnItemListener onItemListener) {
            super(itemView);

            contact_image = itemView.findViewById(R.id.contact_image);
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_phone = itemView.findViewById(R.id.contact_phone);
            contact_email = itemView.findViewById(R.id.contact_email);
            contact_dob = itemView.findViewById(R.id.contact_dob);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ((ViewGroup) view).getChildAt(0).setBackgroundResource(R.drawable.round_corners_gray);
            view.postDelayed(() -> {
                onItemListener.onItemClick(getAdapterPosition(), contacts.get(getAdapterPosition()));
                ((ViewGroup) view).getChildAt(0).setBackgroundColor(Color.WHITE);
            }, 200);
        }
    }
}
