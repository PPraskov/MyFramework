package app.configuration;


import app.test.beans.DummyBean;
import core.annotations.configuration.BeanConfiguration;
import core.annotations.dependency.Bean;

@BeanConfiguration
public class BeanConfig {

    public BeanConfig() {
    }

    @Bean
    public DummyBean dummyBean(){
        return new DummyBean();
    }
}
