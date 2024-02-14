package com.example.talevincontacts.converters;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter {
    @TypeConverter
    public static String StringFromDate(LocalDate date) {
        if (date != null) {
            return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return null;
    }

    @TypeConverter
    public static LocalDate dateFromString(String dateStr) {
        if (dateStr != null) {
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return null;
    }
}
