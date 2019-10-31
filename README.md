easychat server
本服务是一个简单的聊天im软件后端程序，采用springboot + netty框架
提供了单聊 群聊 发送消息 发送图片等简单功能
提供了两种类型的客户端 Android 跟 javafx客户端
具体的系统内容可以查看接口文档

使用说明
1.拉取代码
2.修改配置文件 设置好自己的mysql跟redis服务信息
3.将application.properties中的spring.profiles.active配置为dev
4.从项目resource目录下面的sql目录获取表结构数据，运行创建表结构
5.说明文档可以查看resource目录下面的readme目录下的接口文档，基本接口信息都有记录
