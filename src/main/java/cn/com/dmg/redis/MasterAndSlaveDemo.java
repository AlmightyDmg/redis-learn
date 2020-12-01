package cn.com.dmg.redis;

import redis.clients.jedis.Jedis;

public class MasterAndSlaveDemo {
    public static void main(String[] args) throws Exception{
        Jedis jedis_M = new Jedis("192.168.246.128",6379);
        Jedis jedis_S = new Jedis("192.168.246.128",6380);

        jedis_S.slaveof("192.168.246.128",6379);

        jedis_M.set("k12","v12");
        Thread.sleep(5000);
        System.out.println(jedis_S.get("k12"));

    }
}
