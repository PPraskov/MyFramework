package app.configuration;


import core.annotations.configuration.ConfigurationMethod;
import core.annotations.configuration.EnableHibernate;

import java.util.HashMap;
import java.util.Map;

@EnableHibernate
public class HibernateConfig {

    public HibernateConfig() {
    }

    @ConfigurationMethod
    public Map<String,String> config(){
        Map<String, String> settings = new HashMap<>();

        settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/demo_db?createDatabaseIfNotExist=true");
        settings.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        settings.put("hibernate.connection.username", "root");
        settings.put("hibernate.connection.password", "root");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.hbm2ddl.auto", "update");

        return settings;
    }
}
