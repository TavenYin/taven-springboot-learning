#### 引导
1. 环境准备：mysql，redis（作为消息队列）

2. 执行resource/sql 下sql脚本

3. 启动项目后，GET http://localhost:8080/ws?userId=1 进入页面（这里userId随便填，服务端会对userId和当前Session建立绑定关系）

4. 建立websocket连接后，GET http://localhost:8080/send?receiverId=1&msg=hi 可以向指定userId推送消息

#### 关于本节内容
https://www.jianshu.com/p/b8e21b8f64fd