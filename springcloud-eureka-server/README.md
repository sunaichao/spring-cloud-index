# Spring Cloud Eureka

## 服务注册中心示例

使用**Netflix Eureka**来实现服务注册与发现,它既包含了服务端组件也包含了客户端组件。

**搭建服务注册中心**

**一、创建spring boot工程**

    命名为springcloud-eureka-server，并在pom.xml文件中引入依赖，代码如下,详细的内容请查看源文件：
```java
               <dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka-server</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
```
    
**二、修改配置文件application.properties,添加如下内容:**
```java
server.port=8761
server.context-path=/discovery
eureka.instance.hostname=localhost
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/discovery/eureka/
```
* eureka.client.register-with-eureka属性:代表的含义是本应用是否向注册中心注册自己
* eureka.client.fetch-registry属性:代表的含义是本应用是否需要去检索服务


**三、修改启动类**
```java
package com.chit.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SpringcloudEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudEurekaServerApplication.class, args);
	}
}

```

在完成上述配置后，启动应用并访问http://localhost:8761/,即可看到Eureka的信息面板。

![mahua](eureka-server.png)


至此一个单节点的注册中心就产生了。

---

## 配置详解

==EurekaClientConfigBean.class配置类==

```java
    public static final String PREFIX = "eureka.client";
    public static final String DEFAULT_URL = "http://localhost:8761/eureka/";
    public static final String DEFAULT_ZONE = "defaultZone";
    private static final int MINUTES = 60;
    @Autowired(
        required = false
    )
    PropertyResolver propertyResolver;
    private boolean enabled = true;
    @NestedConfigurationProperty
    private EurekaTransportConfig transport = new CloudEurekaTransportConfig();
    private int registryFetchIntervalSeconds = 30;
    private int instanceInfoReplicationIntervalSeconds = 30;
    private int initialInstanceInfoReplicationIntervalSeconds = 40;
    private int eurekaServiceUrlPollIntervalSeconds = 300;
    private String proxyPort;
    private String proxyHost;
    private String proxyUserName;
    private String proxyPassword;
    private int eurekaServerReadTimeoutSeconds = 8;
    private int eurekaServerConnectTimeoutSeconds = 5;
    private String backupRegistryImpl;
    private int eurekaServerTotalConnections = 200;
    private int eurekaServerTotalConnectionsPerHost = 50;
    private String eurekaServerURLContext;
    private String eurekaServerPort;
    private String eurekaServerDNSName;
    private String region = "us-east-1";
    private int eurekaConnectionIdleTimeoutSeconds = 30;
    private String registryRefreshSingleVipAddress;
    private int heartbeatExecutorThreadPoolSize = 2;
    private int heartbeatExecutorExponentialBackOffBound = 10;
    private int cacheRefreshExecutorThreadPoolSize = 2;
    private int cacheRefreshExecutorExponentialBackOffBound = 10;
    private Map<String, String> serviceUrl = new HashMap();
    private boolean gZipContent;
    private boolean useDnsForFetchingServiceUrls;
    private boolean registerWithEureka;
    private boolean preferSameZoneEureka;
    private boolean logDeltaDiff;
    private boolean disableDelta;
    private String fetchRemoteRegionsRegistry;
    private Map<String, String> availabilityZones;
    private boolean filterOnlyUpInstances;
    private boolean fetchRegistry;
    private String dollarReplacement;
    private String escapeCharReplacement;
    private boolean allowRedirects;
    private boolean onDemandUpdateStatusChange;
    private String encoderName;
    private String decoderName;
    private String clientDataAccept;
```


参数名 | 说明 | 默认值
---|---|---
enabled | 启用Eureka客户端 | true
registryFetchIntervalSeconds | 从Eureka服务端获取注册信息的间隔时间，</br>单位为秒 | 30
instanceInfoReplicationIntervalSeconds | 更新实例信息的变化到Eureka服务端的间隔时间，单位为秒 | 30
initialInstanceInfoReplicationIntervalSeconds | 初始化实例信息到Eureka服务端的间隔时间，单位为秒 | 40
eurekaServiceUrlPollIntervalSeconds | 轮询Eureka服务端地址更改的间隔时间，单位为秒。</br>当我们与spring cloud config配合，动态刷新</br>Eureka的serviceURL地址时需要关注参数 | 300
eurekaServerReadTimeoutSeconds | 读取Eureka Server信息的超时时间，单位为秒 | 8
eurekaServerConnectTimeoutSeconds | 连接Eureka Server的超时时间，单位为秒 | 5
eurekaServerTotalConnections | 从Eureka客户端到所有Eureka服务端的连接总数 | 200
eurekaServerTotalConnectionsPerHost | 从Eureka客户端到每个Eureka服务端主机的连接总数 | 50
eurekaConnectionIdleTimeoutSeconds | Eureka服务端连接的空闲关闭时间，单位为秒 | 30
heartbeatExecutorThreadPoolSize | 心跳连接池的初始化线程数 | 2
heartbeatExecutorExponentialBackOffBound | 心跳超时重试延迟时间的最大乘数值 | 10
cacheRefreshExecutorThreadPoolSize | 缓存刷新线程池的初始化线程数 | 2
cacheRefreshExecutorExponentialBackOffBound | 缓存刷新重试延迟时间的最大乘数值 | 10
useDnsForFetchingServiceUrls | 使用DNS来获取Eureka服务端的serviceUrl | false
registerWithEureka | 是否要将自身的实例信息注册到Eureka服务端 | true
preferSameZoneEureka | 是否偏好使用处于相同Zone的Eureka服务端 | true
filterOnlyUpInstances | 获取实例时是否过滤，仅保留UP状态的实例 | true
fetchRegistry | 是否从Eureka服务端获取注册信息 | true

==EurekaInstanceConfigBean.class配置类==

源代码：
```java
    private static final String UNKNOWN = "unknown";
    private HostInfo hostInfo;
    private InetUtils inetUtils;
    private String appname = "unknown";
    private String appGroupName;
    private boolean instanceEnabledOnit;
    private int nonSecurePort = 80;
    private int securePort = 443;
    private boolean nonSecurePortEnabled = true;
    private boolean securePortEnabled;
    private int leaseRenewalIntervalInSeconds = 30;
    private int leaseExpirationDurationInSeconds = 90;
    private String virtualHostName = "unknown";
    private String instanceId;
    private String secureVirtualHostName = "unknown";
    private String aSGName;
    private Map<String, String> metadataMap = new HashMap();
    private DataCenterInfo dataCenterInfo;
    private String ipAddress;
    private String statusPageUrlPath;
    private String statusPageUrl;
    private String homePageUrlPath;
    private String homePageUrl;
    private String healthCheckUrlPath;
    private String healthCheckUrl;
    private String secureHealthCheckUrl;
    private String namespace;
    private String hostname;
    private boolean preferIpAddress;
    private InstanceStatus initialStatus;
    private String[] defaultAddressResolutionOrder;
    private Environment environment;
```

参数名 | 说明 | 默认值
---|---|---
preferIpAddress | 是否优先使用ip地址做为主机名标识 | false
leaseRenewalIntervalInSeconds | Eureka客户端向服务端发送心跳的时间间隔，</br>单位为秒 | 30
leaseExpirationDurationInSeconds | Eureka服务端在收到最后一次心跳之后等待的时间上限，单位为秒。</br>超过该时间后服务端会将该服务实例从服务清单中剔除，</br>从而禁止服务调用请请求被发送到该实例上 | 30
nonSecurePort | 非安全的通信端口号 | 80
securePortEnabled | 是否启用安全的通信端口号 | 
nonSecurePortEnabled | 是否启用非安全的通信端口号 |true