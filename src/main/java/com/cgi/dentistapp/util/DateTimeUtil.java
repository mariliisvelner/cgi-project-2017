package com.cgi.dentistapp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static String toString(LocalDateTime dateTime){
        return formatter.format(dateTime);
    }
}
