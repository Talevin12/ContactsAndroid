package com.example.talevincontacts.model.contacts;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.talevincontacts.model.Gender;
import com.example.talevincontacts.model.userAuth.UserAuth;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "contacttable",
        foreignKeys = {
                @ForeignKey(entity = UserAuth.class,
                        parentColumns = "userId",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE)
        })
public class Contact implements Serializable {
    @PrimaryKey
    @NonNull
    private UUID contactId = UUID.randomUUID();
    @NonNull
    @ColumnInfo(name = "userId", index = true)
    private UUID userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private Gender gender;

    @NonNull
    public UUID getContactId() {
        return contactId;
    }

    public void setContactId(@NonNull UUID contactId) {
        this.contactId = contactId;
    }

    @NonNull
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(@NonNull UUID userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return Objects.equals(getContactId(), contact.getContactId()) &&
                Objects.equals(getUserId(), contact.getUserId()) &&
                Objects.equals(getFirstName(), contact.getFirstName()) &&
                Objects.equals(getLastName(), contact.getLastName()) &&
                Objects.equals(getPhoneNumber(), contact.getPhoneNumber()) &&
                Objects.equals(getEmail(), contact.getEmail()) &&
                Objects.equals(getDateOfBirth(), contact.getDateOfBirth()) &&
                getGender() == contact.getGender();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContactId(), getUserId(), getFirstName(), getLastName(), getPhoneNumber(), getEmail(), getDateOfBirth(), getGender());
    }

    public void updateContact(Contact contact) {
        firstName = contact.getFirstName();
        lastName = contact.getLastName();
        phoneNumber = contact.getPhoneNumber();
        email = contact.getEmail();
        dateOfBirth = contact.getDateOfBirth();
        gender = contact.getGender();
    }
}