package com.example.demo.hystrix;

import com.example.demo.MainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by liutao on 2017/6/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FallbackCommandTest {
    @Autowired
    private MainService mainService;

    @Test
    public void test() {
        int i = 0;
        do {
            FallbackCommand command = new FallbackCommand("liutao", mainService);
            String result = command.execute();
//            System.out.println("result::" + result);
//            if (result=="execute failure"){
//                break;
//            }
            System.out.println("the "+i+" result::" + result);
            i++;
        } while (i < 100);
    }
}