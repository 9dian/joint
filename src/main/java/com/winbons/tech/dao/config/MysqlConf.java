package com.winbons.tech.dao.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource({ "classpath:/c3p0.properties", "classpath:/mysql.properties" })
@EnableTransactionManagement
public class MysqlConf {
    private static Logger log = LoggerFactory.getLogger(MysqlConf.class);

    @Autowired
    Environment env;

    @Bean(name = { "mysqlHibernateTemplate" })
    public HibernateTemplate getMysqlHibernateTemplate() {
        HibernateTemplate ht = new HibernateTemplate();
        ht.setSessionFactory(mysqlSessionFactory().getObject());
        return ht;
    }

    @Bean
    public LocalSessionFactoryBean mysqlSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(mysqlDataSource());
        sessionFactoryBean.setPackagesToScan(new String[] { "com.winbons.tech.dao.entity" });
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", this.env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", this.env.getProperty("hibernate.format_sql"));
        properties.put("hibernate.dialect", this.env.getProperty("mysql.hibernate.dialect"));
        properties.put("hibernate.default_schema", this.env.getProperty("mysql.hibernate.default_schema"));
        properties.put("hibernate.hbm2ddl.auto", this.env.getProperty("mysql.hibernate.hbm2ddl.auto"));
        sessionFactoryBean.setHibernateProperties(properties);
        return sessionFactoryBean;
    }

    @Bean
    public DataSource mysqlDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(this.env.getProperty("mysql.hibernate.connection.driver_class"));
        } catch (PropertyVetoException e) {
            log.error("jdbc property file config error: \n" + e.getLocalizedMessage());
        }
        dataSource.setJdbcUrl(this.env.getProperty("mysql.hibernate.connection.url"));

        dataSource.setUser(this.env.getProperty("mysql.hibernate.connection.username"));
        dataSource.setPassword(this.env.getProperty("mysql.hibernate.connection.password"));

        dataSource.setMinPoolSize(Integer.parseInt(this.env.getProperty("hibernate.c3p0.minPoolSize")));
        dataSource.setMaxPoolSize(Integer.parseInt(this.env.getProperty("hibernate.c3p0.maxPoolSize")));
        dataSource.setMaxIdleTime(Integer.parseInt(this.env.getProperty("hibernate.c3p0.maxIdleTime")));
        dataSource.setAcquireIncrement(Integer.parseInt(this.env.getProperty("hibernate.c3p0.acquireIncrement")));
        dataSource
                .setAcquireRetryAttempts(Integer.parseInt(this.env.getProperty("hibernate.c3p0.acquireRetryAttempts")));

        dataSource.setCheckoutTimeout(Integer.parseInt(this.env.getProperty("hibernate.c3p0.checkoutTimeout")));
        dataSource.setIdleConnectionTestPeriod(
                Integer.parseInt(this.env.getProperty("hibernate.c3p0.idleConnectionTestPeriod")));

        dataSource.setMaxConnectionAge(Integer.parseInt(this.env.getProperty("hibernate.c3p0.maxConnectionAge")));
        dataSource.setMaxStatements(Integer.parseInt(this.env.getProperty("hibernate.c3p0.maxStatements")));
        dataSource.setMaxStatementsPerConnection(
                Integer.parseInt(this.env.getProperty("hibernate.c3p0.maxStatementsPerConnection")));

        dataSource.setTestConnectionOnCheckin(
                Boolean.parseBoolean(this.env.getProperty("hibernate.c3p0.testConnectionOnCheckin")));

        dataSource.setTestConnectionOnCheckout(
                Boolean.parseBoolean(this.env.getProperty("hibernate.c3p0.testConnectionOnCheckout")));

        dataSource.setDebugUnreturnedConnectionStackTraces(
                Boolean.parseBoolean(this.env.getProperty("hibernate.c3p0.DebugUnreturnedConnectionStackTraces")));

        dataSource.setPreferredTestQuery(this.env.getProperty("hibernate.c3p0.preferredTestQuery"));
        dataSource.setUnreturnedConnectionTimeout(
                Integer.parseInt(this.env.getProperty("hibernate.c3p0.UnreturnedConnectionTimeout")));

        return dataSource;
    }

    @Bean(name = { "mysqlTransactionManager" })
    public HibernateTransactionManager mysqlTransactionManager() {
        HibernateTransactionManager htm = new HibernateTransactionManager();
        htm.setSessionFactory(mysqlSessionFactory().getObject());
        return htm;
    }
}