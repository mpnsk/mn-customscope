package example;

import io.micronaut.context.annotation.AliasFor;
import io.micronaut.context.annotation.Bean;
import io.micronaut.runtime.context.scope.ScopedProxy;

import javax.inject.Named;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ScopedProxy
@Documented
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Bean
public @interface CurrentRoom {
    @AliasFor(annotation = Named.class, member = "value")
    String value() default "";
}
