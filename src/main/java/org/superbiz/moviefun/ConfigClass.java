package org.superbiz.moviefun;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class ConfigClass {


    @Bean
    public ServletRegistrationBean actionServletRegistration(ActionServlet actionServlet) {
        return new ServletRegistrationBean(actionServlet, "/moviefun/*");
    }

    @Bean
    public DatabaseServiceCredentials sendDatabaseServiceCredentials(@Value("${VCAP_SERVICES}") String vcapService) {
        return new DatabaseServiceCredentials(vcapService);
    }


    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpa = new HibernateJpaVendorAdapter();
        hibernateJpa.setDatabase(Database.MYSQL);
        hibernateJpa.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        hibernateJpa.setGenerateDdl(true);

        return hibernateJpa;
    }

    @Bean
    public HikariDataSource albumsDataSource(DatabaseServiceCredentials sendDatabaseServiceCredentials) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(sendDatabaseServiceCredentials.jdbcUrl("albums-mysql"));

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDataSource(dataSource);
        return hikariDataSource;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryAlbums
            (DataSource albumsDataSource, HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManager = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManager.setDataSource(albumsDataSource);
        localContainerEntityManager.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        localContainerEntityManager.setPackagesToScan("org.superbiz.moviefun.albums");
        localContainerEntityManager.setPersistenceUnitName("albums-db");
        return localContainerEntityManager;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManagerAlbums(EntityManagerFactory localContainerEntityManagerFactoryAlbums) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryAlbums);
        return jpaTransactionManager;
    }

    @Bean
    public TransactionOperations transactionOperationsAlbums(PlatformTransactionManager platformTransactionManagerAlbums) {
        return new TransactionTemplate(platformTransactionManagerAlbums);

    }

    @Bean
    public HikariDataSource moviesDataSource(DatabaseServiceCredentials sendDatabaseServiceCredentials) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(sendDatabaseServiceCredentials.jdbcUrl("movies-mysql"));

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDataSource(dataSource);
        return hikariDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryMovies
            (DataSource moviesDataSource, HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManager = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManager.setDataSource(moviesDataSource);
        localContainerEntityManager.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        localContainerEntityManager.setPackagesToScan("org.superbiz.moviefun.movies");
        localContainerEntityManager.setPersistenceUnitName("movies-db");
        return localContainerEntityManager;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManagerMovies(EntityManagerFactory localContainerEntityManagerFactoryMovies) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryMovies);
        return jpaTransactionManager;
    }

    @Bean
    public TransactionOperations transactionOperationsMovies(PlatformTransactionManager platformTransactionManagerMovies) {
        return new TransactionTemplate(platformTransactionManagerMovies);

    }

}
