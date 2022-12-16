package aop;

import aop.aspect.LoggingExecutionStatAspect;
import aop.counter.Counter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class ContextConfiguration {
    @Bean
    public Counter counter() {
        return new Counter();
    }

    @Bean
    public LoggingExecutionStatAspect aspect() {
        return new LoggingExecutionStatAspect();
    }
}
