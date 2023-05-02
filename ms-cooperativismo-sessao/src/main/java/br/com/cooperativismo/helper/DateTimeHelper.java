package br.com.cooperativismo.helper;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeHelper {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public String getLocalDateTimeFormatted() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }
}