package cn.com.dmg.redis;
 
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
 
public class RedisPoolDemo {
  public static void main(String[] args) {
     JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
     Jedis jedis = null;
     
     try 
     {
       jedis = jedisPool.getResource();
       jedis.set("k18","v183");
       
     } catch (Exception e) {
       e.printStackTrace();
     }finally{
       JedisPoolUtil.release(jedisPool, jedis);
     }
  }
}
 
