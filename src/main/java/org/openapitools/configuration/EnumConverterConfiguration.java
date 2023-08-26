package org.openapitools.configuration;

import ru.neoflex.openapi.dtos.EmploymentStatus;
import ru.neoflex.openapi.dtos.Gender;
import ru.neoflex.openapi.dtos.MaritalStatus;
import ru.neoflex.openapi.dtos.Position;
import ru.neoflex.openapi.dtos.Theme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class EnumConverterConfiguration {

    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.employmentStatusConverter")
    Converter<String, EmploymentStatus> employmentStatusConverter() {
        return new Converter<String, EmploymentStatus>() {
            @Override
            public EmploymentStatus convert(String source) {
                return EmploymentStatus.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.genderConverter")
    Converter<String, Gender> genderConverter() {
        return new Converter<String, Gender>() {
            @Override
            public Gender convert(String source) {
                return Gender.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.maritalStatusConverter")
    Converter<String, MaritalStatus> maritalStatusConverter() {
        return new Converter<String, MaritalStatus>() {
            @Override
            public MaritalStatus convert(String source) {
                return MaritalStatus.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.positionConverter")
    Converter<String, Position> positionConverter() {
        return new Converter<String, Position>() {
            @Override
            public Position convert(String source) {
                return Position.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.themeConverter")
    Converter<String, Theme> themeConverter() {
        return new Converter<String, Theme>() {
            @Override
            public Theme convert(String source) {
                return Theme.fromValue(source);
            }
        };
    }

}
