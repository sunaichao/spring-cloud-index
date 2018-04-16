package com.chit.cloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2018/4/13.
 */
@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloFallBack")
    public  String helloService(){
        return restTemplate.getForEntity("http://SPRINGCLOUD-HELLOWORLD-SERVICE/hystrixworld",String.class).getBody();
    }

    /**
     * 超时失败回调方法
     * @return
     */
    public String helloFallBack(){
        return "======error=======";
    }
}
