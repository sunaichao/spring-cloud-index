package com.chit.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/4/12.
 */
@RestController
public class HelloWorldController {
    private final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/helloworld",method = RequestMethod.GET)
    public String index(){
        ServiceInstance instance = discoveryClient.getLocalServiceInstance();
        logger.info("/helloworld,host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return "helloworld";
    }
}
