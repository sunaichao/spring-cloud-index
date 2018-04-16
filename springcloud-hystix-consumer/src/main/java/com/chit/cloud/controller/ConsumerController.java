package com.chit.cloud.controller;

import com.chit.cloud.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/4/13.
 */
@RestController
public class ConsumerController {

    @Autowired
    HelloService helloService;

    @RequestMapping(value = "/hystrix-consumer",method = RequestMethod.GET)
    public String helloConsumer(){
        return helloService.helloService();
    }
}
