# forChat
forChat是使用java的socket实现的多人聊天程序
采用客户端服务器模式
服务器端每接收到一个客户端的连接请求，就生成一条新的线程进行处理，并告知此时正在线上的所有其他用户该用户的上线消息
客户端登录后进入到聊天界面，进行群聊，可发送文件和发送消息
