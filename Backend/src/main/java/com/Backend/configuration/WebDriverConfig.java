package com.Backend.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class WebDriverConfig {

    @Value("${aws.selenium.url}")
    private String seleniumUrl;

    @Bean
    public WebDriver webDriver() throws MalformedURLException {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("headless");
        return new RemoteWebDriver(new URL(seleniumUrl), options);
    }
}
