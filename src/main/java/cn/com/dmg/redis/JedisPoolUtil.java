package cn.com.dmg.redis;
 
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
 
public class JedisPoolUtil {
  
 private static volatile JedisPool jedisPool = null;//被volatile修饰的变量不会被本地线程缓存，对该变量的读写都是直接操作共享内存。
  
  private JedisPoolUtil() {}
  
  public static JedisPool getJedisPoolInstance()
 {
     /**
      * 判断两次空
      *     1.第一次判断，若不为空，直接不用进synchronized，大大提高效率
      *     2.第二次判断，若锁上之前，被new了，则不会再被new
      */
     if(null == jedisPool)
    {
       synchronized (JedisPoolUtil.class)
      {
          if(null == jedisPool)
         {
           JedisPoolConfig poolConfig = new JedisPoolConfig();
           poolConfig.setMaxIdle(1000);
           poolConfig.setMaxTotal(1000);
           poolConfig.setMaxIdle(32);
           poolConfig.setMaxWaitMillis(100*1000);
           poolConfig.setTestOnBorrow(true);
            
            jedisPool = new JedisPool(poolConfig,"192.168.246.128");
         }
      }
    }
     return jedisPool;
 }
  
  public static void release(JedisPool jedisPool,Jedis jedis)
 {
     if(null != jedis)
    {
      jedisPool.returnResourceObject(jedis);
    }
 }
}
 
