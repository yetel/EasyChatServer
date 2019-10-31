package com.easychat.netty;

import com.easychat.handler.WorkServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NettyServer {
    
    @Value("${server.netty.port}")
    private int nettyPort;
    private ServerBootstrap bootstrap;
    private NioEventLoopGroup boss;
    private NioEventLoopGroup worker;
    private Channel channel;
    
    @Autowired
    private WorkServerInitializer workServerInitializer;
    
    private void open(int port) throws Exception {
        bootstrap = new ServerBootstrap();
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();

        bootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(workServerInitializer);
        ChannelFuture channelFuture = bootstrap.bind(port).sync();
        log.info("netty 服务启动成功， 端口 = {}", port);
        channel = channelFuture.channel();
        
        //关闭监听服务器
        channel.closeFuture().sync();
    }
    
    
    public synchronized void start() {
        try {
            open(nettyPort);
        } catch (Exception e) {
            doClose();
        }

    }

    public synchronized void doClose() {
        log.info("服务器关闭......");
        // 关闭服务器连接
        try {
            if (channel != null) {
                channel.close();
            }
        } catch (Throwable e) {
            log.warn(e.getMessage(), e);
        }
        // 关闭连接到本服务器的连接
        // 关闭服务器
        try {
            if (bootstrap != null) {
                boss.shutdownGracefully();
                worker.shutdownGracefully();
            }
        } catch (Throwable e) {
            log.warn(e.getMessage(), e);
        }
        log.info("服务器关闭完成");
    }
    
}
