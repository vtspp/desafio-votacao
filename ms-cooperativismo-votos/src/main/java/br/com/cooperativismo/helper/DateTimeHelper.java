package br.com.cooperativismo.helper;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeHelper {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String LOCAL_DATE_TIME_FORMATTED = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
}