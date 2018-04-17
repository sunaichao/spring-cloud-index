package com.feign.cloud.feign;

import com.feign.cloud.domain.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/4/17.
 */
@FeignClient("SPRINGCLOUD-HELLOWORLD-SERVICE")
public interface HelloWorldService {

    @RequestMapping("/helloworld")
    public String helloWorld();

    @RequestMapping(value = "/hello1",method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "/hello2",method = RequestMethod.GET)
    User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @RequestMapping(value = "/hello3",method = RequestMethod.POST)
    String hello1(@RequestBody User user);
}
