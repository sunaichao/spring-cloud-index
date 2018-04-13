# spring-cloud-index
---
**一、服务发现与注册**

- [x] 涉及工程示例：
- springcloud-eureka-server：服务注册中心
- springcloud-helloworld-service：注册到服务注册中心，并且提供服务接口返回helloworld内容。
- springcloud-ribbon-consumer：注册到服务注册中心，并且消费==springcloud-helloworld-service==提供的服务。
---

**二、基础架构**

上述三个应用，演示了整个Eureka服务治理基础架构的三个核心要素：
- [x] 服务注册中心：Eureka提供的服务端，提供服务注册与发现功能。
- [x] 服务提供者：它将自己提供的服务注册到Eureka，以供其他应用发现。
- [x] 服务消费者：消费者从注册中心获取服务列表，从而知道去何处调用其所需的服务。

---




