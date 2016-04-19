package org.enciende.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Archivo de configuración de Spring para la parte 
 * de persitencia.
 * 
 * @author  Luis Perez
 *
 */
@Configuration
@EnableJpaRepositories(basePackages="org.enciende.persistence.repository",transactionManagerRef="transactionManager")
@EnableTransactionManagement
public class PersistenceConfig 
{
	@Autowired
	protected Environment env;
	

	/**
	 * Obtiene el bean para el entity manager (JPA) configurado
	 * para utilizar hibernate como adaptador.
	 * 
	 * @return El entity manager factory
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
	{
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(Boolean.TRUE);
		vendorAdapter.setShowSql(Boolean.FALSE);
		factory.setDataSource(getDataSource());
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(new String[]{"org.enciende.model"});
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		jpaProperties.put("hibernate.hbm2ddl.auto", "none");
		factory.setJpaProperties(jpaProperties);
		factory.afterPropertiesSet();
		factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		return factory;
	}
	
	/**
	 * Obtiene el bean para traducir las excepciones
	 * @return Una instancia {@link HibernateExceptionTranslator}
	 */
	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator()
	{
		return new HibernateExceptionTranslator();
	}
	
    
    /**
     * Regresa el manager de transacciones
     * 
     * @return Una instancia de {@link JpaTransactionManager}
     */
	@Primary
    @Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}
	
    /**
     * Regresa un datasource obtenido desde JNDI
     * utilizando el valor de la propiedad jdbc.cablePortalDS.jndi
     * 
     * @return Un data source para obtener conexiones a la bd
     */
    @Bean(destroyMethod = "")
    @Lazy
	@Profile("default") //TODO default profile 
    public DataSource getDataSource() {
//        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
//        dsLookup.setResourceRef(true);
//        DataSource dataSource = dsLookup.getDataSource(env.getProperty("jdbc.cablePortalDS.jndi"));
//        return dataSource;
    	BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		return dataSource;
    }
    
}