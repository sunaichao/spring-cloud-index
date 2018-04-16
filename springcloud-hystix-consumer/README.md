**服务容错示例**
---
**搭建服务容错**
---
**一、创建spring boot工程**

命名为springcloud-hystric-consumer，并在pom.xml中引入依赖，代码如下，详细的内容请查看源文件：
```xml
<dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-ribbon</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-hystrix</artifactId>
    </dependency>
  </dependencies>
```
---

**二、创建应用程序主类**

```java
package com.chit.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 */
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class App 
{
    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }
}

```

通过@EnableDiscoveryClient注解让该应用注册为Eureka客户端应用，以获得服务发现的能力。同时创建RestTemplate的Spring Bean实例，并且通过@LoadBalanced注解开启客户端负载均衡。
通过@EnableCircuitBreaker注解开启断路器功能。

---

**三、加入断路器功能**

```java
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

```

通过增加@HystrixCommand注解，来制定回调方法。

---

**四、启动验证**

优先启动springcloud-eureka-server服务-注册中心，然后启动服务提供者springcloud-helloworld-service服务。
然后启动服务springcloud-hystrix-consumer。
调用服务接口：127.0.0.1:10802/hystrix-consumer
返回：======error=======

即服务消费者因调用的服务超时从而触发熔断请求，并调用回调逻辑返回结果。
