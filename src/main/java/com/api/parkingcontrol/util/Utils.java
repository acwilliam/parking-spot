package com.api.parkingcontrol.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Utils {

    public static LocalDate localDateTimeToLocalDate(LocalDateTime localDateTime) {


        return localDateTime.toLocalDate();
    }
}
