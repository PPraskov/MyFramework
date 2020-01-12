package app.configuration;

import core.annotations.configuration.EnableHibernate;
import core.configuration.ApplicationSettings;
import core.configuration.DependencySettings;
import core.configuration.HttpServerSettings;

import java.util.Map;

@EnableHibernate
public class AppSettings extends ApplicationSettings {

    @Override
    public void configureDependencies(DependencySettings dependencySettings) {
        dependencySettings
                .enableSingletonPattern();
    }

    @Override
    public void configureHibernate(Map<String, String> settings) {
        settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/demo_db?createDatabaseIfNotExist=true");
        settings.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        settings.put("hibernate.connection.username", "root");
        settings.put("hibernate.connection.password", "root");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.hbm2ddl.auto", "update");
    }

    @Override
    public void configureHttpServer(HttpServerSettings settings) {
        settings
                .setAddressAndPort("127.0.0.1",8000);
    }
}
