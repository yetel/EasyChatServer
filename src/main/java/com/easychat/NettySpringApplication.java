package com.easychat;

import com.easychat.netty.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Zed
 * date: 2019/08/16.
 * description:
 */
@SpringBootApplication
public class NettySpringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(NettySpringApplication.class, args);
        NettyServer nettyServer = ctx.getBean(NettyServer.class);
        nettyServer.start();
    }

}
