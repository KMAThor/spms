package nc.ukma.thor.spms.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableTransactionManagement
@ComponentScan("nc.ukma.thor.spms")
@PropertySource("properties/jdbc.properties")
public class AppConfig {
	
	static final long oneMB = 1024 * 1024;
    static final String UPLOAD_LOCATION = System.getProperty("CATALINA_HOME");
	
    @Autowired
    private Environment environment;
	
    @Bean
    public DataSource dataSource() {
    	BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        dataSource.setMinIdle(1);
        dataSource.setMaxIdle(2);
        dataSource.setInitialSize(1);

        return dataSource;
    }
    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    
    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource){
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
    }
    
    @Bean(name="filterMultipartResolver")//name="multipartResolver"    ???????
    public CommonsMultipartResolver getResolver() throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();

        resolver.setMaxUploadSizePerFile(oneMB * 5);

        return resolver;
    }

    @Bean(name="fileUploadLocation")
    public String getFileUploadLocation() {
        return UPLOAD_LOCATION;
    }
}	
