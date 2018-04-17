package com.chit.cloud.controller;

import com.chit.cloud.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/4/12.
 */
@RestController
public class HelloWorldController {
    private final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/helloworld",method = RequestMethod.GET)
    public String index() throws Exception{
        ServiceInstance instance = discoveryClient.getLocalServiceInstance();
        logger.info("/helloworld,host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return "helloworld";
    }

    @RequestMapping(value = "/hystrixworld",method = RequestMethod.GET)
    public String hystrixworld() throws Exception{
        ServiceInstance instance = discoveryClient.getLocalServiceInstance();
        Thread.sleep(10000);
        logger.info("/helloworld,host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return "helloworld";
    }

    @RequestMapping(value = "/hello1",method = RequestMethod.GET)
    public String hello(@RequestParam String name){
        return "Hello " + name;
    }

    @RequestMapping(value = "hello2",method = RequestMethod.GET)
    public User hello(@RequestHeader String name, @RequestHeader Integer age){
        return new User(name,age);
    }

    @RequestMapping(value = "hello3",method = RequestMethod.POST)
    public String hello(@RequestBody User user){
        return "hello " + user.getName() + ", " + user.getAge();
    }
}
