package fitness.manager_admin.config;

import fitness.manager_admin.dao.ClientDao;
import fitness.manager_admin.dao.SubscriptionDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@PropertySource("classpath:application.properties")
@Configuration
public class ClientDaoContextConfiguration {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public SubscriptionDao subscriptionDao(DataSource dataSource) {
        return new SubscriptionDao(dataSource);
    }

    @Bean
    public ClientDao clientDao(DataSource dataSource) {
        return new ClientDao(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
