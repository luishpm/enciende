package org.enciende.config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Clase principal para configuración de Spring
 * 
 * @author Luis Pérez
 *
 */
@Configuration
@ComponentScan({"org.enciende.business.impl","org.enciende.service.impl"})
@PropertySource(value = { "classpath:application.properties" })
public class SpringConfig {
	
	
	@Bean
	public SimpleDateFormat dateFormat(){
		return new SimpleDateFormat("dd-MM-yyyy");
	}
}
