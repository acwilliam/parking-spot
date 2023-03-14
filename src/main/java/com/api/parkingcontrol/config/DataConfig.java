package com.api.parkingcontrol.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.format.DateTimeFormatter;

@Configuration
public class DataConfig {
    public static final String DATETIME_FORMART = "dd/MM/yyyy'T'HH:mm:ss'Z'";
    public static final String DATE_FORMART = "dd/MM/yyyy";
    public static LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMART));
    public static LocalDateSerializer LOCAL_DATE_SERIALIZER = new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMART));

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LOCAL_DATETIME_SERIALIZER);
        module.addSerializer(LOCAL_DATE_SERIALIZER);
        return new ObjectMapper().registerModule(module);
    }

}
