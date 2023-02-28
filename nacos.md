# Nacos

## Nacos下载安装运行
 
- 可以在github上下载nacos的安装包。
- 运行方式
    - 单机运行方式: startup.cmd -m standalone

## 鉴权插件

- 鉴权：通俗表达(谁能对某一个东西进行某种操作)，Nacos在设计鉴权插件时，将鉴权信息抽象为身份信息，资源和操作类型三个主要的概念。
    - 身份信息：是请求发起的主体在nacos鉴权插件的抽象。实现方式多样，包括用户名和密码或者AccessToken等。nacos会自动获取身份信息，供插件使用。其中必定包含remote_ip和请求来源ip
    - 资源: 是请求所操作对象在nacos鉴权插件中的抽象。主要包括:
        - 请求资源的命名空间: namespaceId
        - 请求资源的分组名: group
        - 请求资源的资源名: name
        - 请求资源的类型: type
        - 请求资源的配置: properties
    - 开发资源鉴权需要引用的maven坐标
    ```
        <dependency>
            <groupId>com.alibaba.nacos</groupId>
            <artifactId>nacos-auth-plugin</artifactId>
            <version>${project.version}</version>
        </dependency>
    ```
    - 随后实现com.alibaba.nacos.plugin.auth.spi.server.AuthPluginService接口， 并将您的实现添加到SPI的services当中