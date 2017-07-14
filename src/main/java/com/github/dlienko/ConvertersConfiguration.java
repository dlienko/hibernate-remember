package com.github.dlienko;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.dlienko.yoga.converter.ExerciseEntityToMetaConverter;
import com.github.dlienko.yoga.converter.ImageEntityToMetaConverter;

@Configuration
public class ConvertersConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        ImageEntityToMetaConverter imageConverter = new ImageEntityToMetaConverter();
        registry.addConverter(imageConverter);
        registry.addConverter(new ExerciseEntityToMetaConverter(imageConverter));
    }

}
