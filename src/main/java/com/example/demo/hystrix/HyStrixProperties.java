package com.example.demo.hystrix;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by liutao on 2017/6/19.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "hystrix")
public class HyStrixProperties {
    private int timeoutInMillions;
}
