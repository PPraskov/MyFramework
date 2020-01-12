package core.configuration;

import app.test.beans.DummyBean;
import core.annotations.configuration.BeanConfiguration;
import core.annotations.dependency.Bean;
import org.thymeleaf.context.Context;

@BeanConfiguration
public class DefaultBeans {

    public DefaultBeans() {
    }

    @Bean
    public DummyBean dummyBean() {
        return new DummyBean();
    }

    @Bean
    public Context context() {
        return new Context();
    }

}
