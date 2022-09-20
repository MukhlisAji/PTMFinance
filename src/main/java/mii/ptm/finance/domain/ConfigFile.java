/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mii.ptm.finance.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 *
 * @author mukhlisaj
 */
@Configuration
@PropertySource("classpath:PTMFinance.properties")
public class ConfigFile {

    @Autowired
    private Environment env;

    @Bean
    public ConfigurationValue getConfigurationValue() {
        return new ConfigurationValue(
                env.getProperty("remedy.server"),
                env.getProperty("remedy.username"),
                env.getProperty("remedy.password"),
                env.getProperty("remedy.port"),
                env.getProperty("remedy.middleform.invoice"),
                env.getProperty("remedy.middleform.receipt"));

    }

}
