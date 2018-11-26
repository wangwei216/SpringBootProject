package com.example.redis.demo_redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RedisClient {

    OutputStream outputStream;
    InputStream inputStream;

    //如何自己实现一个简单的redis客户端
    public RedisClient(String host,int port) throws IOException {
        //需要先建立java和客户端的连接
        Socket socket = new Socket(host,port);
        //往外写数据
         outputStream = socket.getOutputStream();
        //读取服务器端的数据，读取从服务器端进来的流
         inputStream = socket.getInputStream();
    }

    public void set(String key ,String value) throws IOException {
        //1.需要先组装redis的指令
        StringBuffer data = new StringBuffer();
        data.append("*3").append("\r\n");
        data.append("$3").append("\r\n");
        data.append("SET").append("\r\n");
        System.out.println(data.toString());

        data.append("$").append(key.getBytes().length).append("\r\n");
        data.append(key).append("\r\n");
        System.out.println(data.toString());

        data.append("$").append(value.getBytes().length).append("\r\n");
        data.append(value).append("\r\n");
        System.out.println(data.toString());
        //*3
        //$3
        //SET
        //$7  这个7是key的字节长度
        //k123456
        //$21  这个21是value的字节长度     其他的都是固定的指令
        //hello I am is wangwei


        //2.然后把指令发给Redis的服务器端
        outputStream.write(data.toString().getBytes());
        System.out.println("发送成功----->");
        System.out.println(data);

        //3.把接受服务器端进行响应
        byte[] response = new byte[1024];
        inputStream.read(response);
        System.out.println("接收到响应---->");
        System.out.println(response.toString());
    }

    //这个是实现redis客户端的get方法
    public void get(String key) throws IOException {
        //1.需要先组装redis的指令
        StringBuffer data = new StringBuffer();
        data.append("*3").append("\r\n");
        data.append("$3").append("\r\n");
        data.append("GET").append("\r\n");
        System.out.println(data.toString());

        data.append("$").append(key.getBytes().length).append("\r\n");
        data.append(key).append("\r\n");
        System.out.println(data.toString());


        //2.然后把指令发给Redis的服务器端
        outputStream.write(data.toString().getBytes());
        System.out.println("get方法发送成功----->");
        System.out.println(data);

        //3.把接受服务器端进行响应
        byte[] response = new byte[1024];
        inputStream.read(response);
        System.out.println("get方法接收到响应---->");
        System.out.println(response.toString());
    }


    public static void main(String[] args) throws IOException {

        RedisClient redisClient = new RedisClient("127.0.0.1",6379);
        redisClient.set("k123456","hello I am is wangwei");
        redisClient.get("k123456");

    }


}
