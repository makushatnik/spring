package com.cdesign.spittr.config;

import com.cdesign.spittr.config.RootConfig.WebPackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.util.regex.Pattern;

/**
 * Created by Ageev Evgeny on 27.08.2016.
 */
@Configuration
@ComponentScan(basePackages = {"spitter"},
    excludeFilters = {
            @Filter(type = FilterType.CUSTOM, value = WebPackage.class)
    })
@Import({DataSourceConfig.class, CachingConfig.class, MailConfig.class})
public class RootConfig {
    public static class WebPackage extends RegexPatternTypeFilter {
        public WebPackage() {
            super(Pattern.compile("spittr\\.web"));
        }
    }
}
