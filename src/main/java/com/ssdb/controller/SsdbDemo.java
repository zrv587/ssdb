package com.ssdb.controller;

import org.nutz.ssdb4j.impl.SimpleClient;
import org.nutz.ssdb4j.spi.Response;
import org.nutz.ssdb4j.spi.SSDB;


/**
 * @param
 * @Author zr
 * @Date 2018-6-20 17:56
 */
public class SsdbDemo {
    public static void main(String[] args) throws Exception {
        {
            //ssdb的地址
            String host = "192.168.31.129";
            //端口号
            int port = 8888;
            //连接ssdb
            SSDB ssdb = new SimpleClient(host, port, 50000);
//            ssdb.hset("key",1,2);
            System.out.println(ssdb.exists("key").asString());
            if (ssdb.exists("key").asInt() > 0) {
                Response response = ssdb.get("key");
                System.out.println(response.asString());
            }
            ssdb.hset("user", "zs", "1123");
            ssdb.hdel("user", "zs");
            Response response1 = ssdb.hget("user", "zs");
            if (response1.datas.size() <= 0) {
                System.out.println(response1.stat);
                System.out.println("已经被清除");
            } else {
                System.out.println(ssdb.hget("user", "zs").asString());
            }
        }
    }
}
