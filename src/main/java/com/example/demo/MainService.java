package com.example.demo;

import org.springframework.stereotype.Service;

/**
 * Created by liutao on 2017/6/20.
 */
@Service
public class MainService {

    public String doWith(String param) {
        return "hello " + param;
    }
}
