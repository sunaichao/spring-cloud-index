package com.feign.cloud.controller;

import com.feign.cloud.domain.User;
import com.feign.cloud.feign.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Administrator on 2018/4/17.
 */
@RestController
public class ConsumerController {

    @Autowired
    HelloWorldService helloWorldService;

    @RequestMapping(value = "/feign-consumer",method = RequestMethod.GET)
    public String helloConsumer(){
        return helloWorldService.helloWorld();
    }

    @RequestMapping(value = "/feign-consumer2",method = RequestMethod.GET)
    public String helloConsumer2(){
        StringBuilder sb = new StringBuilder();
        User user = new User("DDD",32);
        sb.append(helloWorldService.helloWorld()).append("\n");
        sb.append(helloWorldService.hello("DIDI")).append("\n");
        sb.append(helloWorldService.hello("DIDI",30).toString()).append("\n");
        sb.append(helloWorldService.hello1(user)).append("\n");
        return sb.toString();
    }
}
