## Spring Cloud Feign

---

feign使用示例

所需服务springcloud-eureka-server、springcloud-helloworld-service、springcloud-feign-consumer。

### 修改springcloud-helloworld-service服务。

增加服务接口 HelloWorldController.class

```java
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
```
为springcloud-feign-consumer的调用做好准备。

---

二、新建springcloud-feign-consumer服务

在主类中添加注解@EnableFeignClients 开启feign功能。
```java
package com.feign.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudFeignConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudFeignConsumerApplication.class, args);
	}
}

```

定义服务调用接口

```java
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

```

配置application.properties文件

```cfml
spring.application.name=springcloud-feign-consumer
server.port=9001
eureka.client.service-url.defaultZone=http://172.28.24.161:8761/discovery/eureka/
```

---

启动相应的服务。验证服务。http://localhost:9001/feign-consumer2

返回：helloworld Hello DIDI name=DIDI,age=30 hello DDD, 32 
