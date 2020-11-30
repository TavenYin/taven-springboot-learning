####  AT demo

1. 创建数据库 db_order, db_product, sql脚本在at-sql下

2. 下载并启动seata
```
wget https://github.com/seata/seata/releases/download/v1.4.0/seata-server-1.4.0.zip

# start
sh seata-server.sh -p 8091 -h 127.0.0.1 -m file
```
3. 编译并运行此工程
at-starter com.github.taven.controller.BusinessController 为demo入口

#### TCC

##### tcc-local
运行 TccLocalApplication