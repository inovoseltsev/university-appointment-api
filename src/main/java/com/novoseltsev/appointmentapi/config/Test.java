package com.novoseltsev.appointmentapi.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) throws JsonProcessingException {
//        Date date = java.sql.Date.valueOf("");
//        System.out.println(new java.util.Date(date.getTime()));
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("-MM-yyyy k:mm");
        System.out.println(LocalDateTime.parse("13-02-2021 17:30",
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
//        String str = "1986-04-08 12:30";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
//        System.out.println(dateTime);
    }
}
