package com.example.redis.demo_redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoRedisApplicationTests {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() {

        stringRedisTemplate.opsForValue().set("name","wangwei");
//        stringRedisTemplate.opsForHash().putIfAbsent("16002","name","gehao");
    }

    //这个是模仿一个假的redis服务器端
    @Test
    public void redisServerFork() throws IOException {
        ServerSocket serverSocket  = new ServerSocket(6379);
        System.out.println("假的服务器启动了");
        Socket connect = serverSocket.accept();

        byte[] request = new byte[1024];

        connect.getInputStream().read(request);
        System.out.println("收到来自客户端的请求");
        System.out.println(new String(request));

    }

}
