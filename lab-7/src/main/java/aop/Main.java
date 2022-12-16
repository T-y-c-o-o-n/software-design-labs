package aop;

import aop.aspect.LoggingExecutionStatAspect;
import aop.counter.Counter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(
            ContextConfiguration.class
        );
        Counter counter = ctx.getBean(Counter.class);
        LoggingExecutionStatAspect aspect = ctx.getBean(LoggingExecutionStatAspect.class);
        Scanner scanner = new Scanner(System.in);
        System.out.println("type \"count\" to count how many calls are done");
        System.out.println("type \"stat\" for statistics log");
        System.out.println("type anything else for call");
        while (scanner.hasNext()) {
            String next = scanner.next();
            if ("count".equals(next)) {
                System.out.println(counter.count());
            } else if ("stat".equals(next)) {
                aspect.logStatistics();
            } else {
                counter.click();
            }
        }
    }
}
