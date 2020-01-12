package core.annotations.scheduled;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScheduledTask {
    Frequency ocurrence();
    String minute();
    String hour() default "";
    String date() default "";
    String month() default "";
}
