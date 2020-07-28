1.运行zookeeper （测试版本3.6.1）

```$xslt
docker pull zookeeper
docker run --privileged=true -d --name zookeeper --publish 2181:2181  -d zookeeper:latest
```

2.运行demo，先启动provider，再启动consumer

> 多网卡时，provider注册时自动获取的ip可能出现问题，导致consumer无法调用，此时我们可以通过手动指定ip来解决

```$xslt
dubbo.protocol.host=192.168.3.10
dubbo.provider.host=192.168.3.10
```
