package cn.com.dmg.redis;

import redis.clients.jedis.Jedis;

public class ConnectionRedis {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("192.168.246.128",6379);
        //查看服务是否运行，打出pong表示OK
        System.out.println("connection is OK==========>: "+jedis.ping());
    }

}
