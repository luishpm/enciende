package org.enciende.servicios.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

/**
 * Clase principal de configuracion para el modulo de servicios
 * 
 * @author luishpm
 * Abril 13, 2016
 */
@Configuration
@PropertySource(value = { "classpath:webapp.properties" })
public class SpringWebConfig {
	
	/**
	 * Bean para poner el locale en es_MX
	 * 
	 * @return LolcaleResolver
	 */
	@Bean
	public LocaleResolver localeResolver() {
		FixedLocaleResolver resolver = new FixedLocaleResolver(new Locale("es", "MX"));
		return resolver;
	}
}
