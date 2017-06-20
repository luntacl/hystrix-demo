package com.example.demo.hystrix;

import com.example.demo.MainService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.TimeUnit;

/**
 * Created by liutao on 2017/6/20.
 */
public class FallbackCommand extends HystrixCommand<String> {

    private String name;
    private MainService mainService;

    protected FallbackCommand(String name, MainService mainService) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        // 使用命令调用隔离方式,默认:采用线程隔离,ExecutionIsolationStrategy.THREAD
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                        /* 配置依赖超时时间,500毫秒*/
                        .withExecutionTimeoutInMilliseconds(500)
                        // 熔断器在整个统计时间内是否开启的阀值，默认20。也就是10秒钟内至少请求20次，熔断器才发挥起作用
                        .withCircuitBreakerRequestVolumeThreshold(100)
                        // 统计滚动的时间窗口,默认:5000毫秒circuitBreakerSleepWindowInMilliseconds
                        .withCircuitBreakerSleepWindowInMilliseconds(50000)
                        // 统计窗口的Buckets的数量,默认:10个,每秒一个Buckets统计
                        .withMetricsRollingStatisticalWindowBuckets(20)
                        // 使用信号量隔离时，命令调用最大的并发数,默认:10
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(10)
                        // 使用信号量隔离时，命令fallback(降级)调用最大的并发数,默认:10
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(20)
                        // 默认:50%。当出错率超过50%后熔断器启动.
                        .withCircuitBreakerErrorThresholdPercentage(50)
                        // 是否开启fallback降级策略 默认:true
                        .withFallbackEnabled(true)
                        // 使用线程隔离时，是否对命令执行超时的线程调用中断（Thread.interrupt()）操作.默认:true
                        .withExecutionIsolationThreadInterruptOnTimeout(true)
                        // 是否开启监控统计功能,默认:true
                        .withMetricsRollingPercentileEnabled(true)
                        // 是否开启请求日志,默认:true
                        .withRequestLogEnabled(true)
                        // 是否开启请求缓存,默认:true
                        .withRequestCacheEnabled(true)
                        //是否启用熔断器,默认true. 启动
                        .withCircuitBreakerEnabled(true)
                        // 是否强制开启熔断器阻断所有请求,默认:false,不开启
                        .withCircuitBreakerForceOpen(false)
                        // 是否允许熔断器忽略错误,默认false, 不开启
                        .withCircuitBreakerForceClosed(false)
                )
        );
        this.name = name;
        this.mainService = mainService;
    }

    @Override
    protected String getFallback() {
        return "execute failure";
    }

    @Override
    protected String run() throws Exception {
        //sleep 1 秒,调用会超时
        TimeUnit.MILLISECONDS.sleep(100);
        String s = mainService.doWith(name);
        System.out.println("s++++++++++++" + s);
//        throw new Exception("Exceptions !!!!!");
        return "Hello " + name + " thread:" + Thread.currentThread().getName();
    }
}
