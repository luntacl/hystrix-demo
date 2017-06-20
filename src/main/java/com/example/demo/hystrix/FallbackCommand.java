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
                /* 配置依赖超时时间,500毫秒*/
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(500)));
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
        TimeUnit.MILLISECONDS.sleep(501);
        String s = mainService.doWith(name);
        System.out.println("s++++++++++++" + s);
//        throw new Exception("Exceptions !!!!!");
        return "Hello " + name + " thread:" + Thread.currentThread().getName();
    }
}
