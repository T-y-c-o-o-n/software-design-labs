package aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class LoggingExecutionStatAspect {

    private final Map<String, Integer> methodCount = new HashMap<>();
    private final Map<String, Long> methodSumTime = new HashMap<>();

    @Around("execution(* aop.counter..*.*(..))")
    public Object calculateExecutionStat(ProceedingJoinPoint joinPoint) throws Throwable {
        long startNs = System.nanoTime();
        String name = joinPoint.getSignature().getName();
        methodCount.put(name, methodCount.getOrDefault(name, 0) + 1);

        Object result = joinPoint.proceed(joinPoint.getArgs());

        methodSumTime.put(name, methodSumTime.getOrDefault(name, 0L) + System.nanoTime() - startNs);

        return result;
    }

    public void logStatistics() {
        for (String name : methodCount.keySet()) {
            System.out.println("Method \"" + name + "\" statistics:");
            int count = methodCount.get(name);
            long sum = methodSumTime.get(name);
            System.out.println("count: " + count);
            System.out.println("sum time: " + toSeconds(sum) + " seconds");
            System.out.println("ave time: " + toSeconds(sum / count) + " seconds");
        }
    }

    private double toSeconds(long sum) {
        return (double) sum / 1000000000d;
    }
}
