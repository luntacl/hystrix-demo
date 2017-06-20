package com.example.demo.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import okhttp3.Response;

import java.util.concurrent.ExecutionException;

import com.netflix.hystrix.HystrixCommand.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liutao on 2017/6/19.
 */
public class HyStrixUtil {

    @Autowired
    private HyStrixProperties hp;

    public Response execute(String hotelServiceName,
                            String hotelServiceMethodGetHotelInfo,
                            String url) throws InterruptedException, ExecutionException {
        Setter setter = Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(hotelServiceName));//被调用服务
        setter.andCommandKey(HystrixCommandKey.Factory.asKey(hotelServiceMethodGetHotelInfo));//被调用服务的一个被调用方法
        setter.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(hp.getTimeoutInMillions()));
        return new HttpCallCommand(setter, url).execute();//同步执行
        //        Future<Response> future = new HttpCallCommand(setter, url).queue();//异步执行
        //        return future.get();//需要时获取
    }
}

