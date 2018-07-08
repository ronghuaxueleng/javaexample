# springboot-dubbo-parent

依赖 [非官方的spring-boot-starter-dubbo](https://gitee.com/reger/spring-boot-starter-dubbo)

更新 springboot 版本为 2.0.1.RELEASE

本身是个父依赖，包含了3个 module

### 各 Module 介绍

| 名称                                                         | 作用                                    |
| :----------------------------------------------------------- | :-------------------------------------- |
| [springboot-dubbo-api](./springboot-dubbo-api/pom.xml) | Spring Boot 与 Dubbo 整合抽取的服务接口 |
| [springboot-dubbo-provider](./springboot-dubbo-provider/pom.xml) | Spring Boot 与 Dubbo 整合服务的提供方   |
| [springboot-dubbo-consumer](./springboot-dubbo-consumer/pom.xml) | Spring Boot 与 Dubbo 整合服务的消费方   |

### 步骤

1. 启动本地的 `Zookeeper` 服务，端口号为 **2181**

   > 这里 `Zookeeper` 可以直接使用单机版，如果需要配置集群版的，可以参考 http://xkcoding.com/2017/11/01/zookeeper-cluster.html

2. 启动两个 `springboot-dubbo-provider` 程序，端口号分别使用 **8082**、**8083**

3. 启动 `springboot-dubbo-consumer` 程序，端口号为 **9090**

4. 浏览器输入 http://localhost:9090/greet?name=test 

   可以看到，**8082**和**8083**端口随机打印

   > Hello, test, from port: 8082

   或

   > Hello, test, from port: 8083

5. 本地启动 `dubbo-admin` 可以查看服务提供方和消费方，也可以配置负载均衡策略等等

   > 关于 `dubbo-admin` 的安装和配置等，可以参考 http://xkcoding.com/2017/11/08/dubbo-admin-install.html