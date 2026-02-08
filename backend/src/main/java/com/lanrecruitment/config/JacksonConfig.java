package com.lanrecruitment.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(LOCAL_DATE_TIME_FORMATTER));
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(LOCAL_DATE_TIME_FORMATTER));
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(LOCAL_DATE_FORMATTER));
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(LOCAL_DATE_FORMATTER));
            builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        };
    }
}

