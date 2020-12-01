package cn.com.dmg.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class RedisTransactionDemo {

    public static void main(String[] args) {
        //normalTrans();
        lockTrans();


    }

    public static void normalTrans(){
        Jedis jedis = new Jedis("192.168.246.128",6379);

        //监控key，如果该动了事务就被放弃
         /*3
         jedis.watch("serialNum");
         jedis.set("serialNum","s#####################");
         jedis.unwatch();*/

        Transaction transaction = jedis.multi();//被当作一个命令进行执行
        Response<String> response = transaction.get("serialNum");
        transaction.set("serialNum","s002");
        response = transaction.get("serialNum");
        transaction.lpush("list3","a");
        transaction.lpush("list3","b");
        transaction.lpush("list3","c");

        transaction.exec();
        //2 transaction.discard();
        System.out.println("serialNum***********"+response.get());
    }

    public static boolean lockTrans(){
        Jedis jedis = new Jedis("192.168.246.128", 6379);
        int balance;// 可用余额
        int debt;// 欠额
        int amtToSubtract = 10;// 实刷额度

        jedis.watch("balance");
        jedis.set("balance","15");//此句不该出现，讲课方便。模拟其他程序已经修改了该条目
        balance = Integer.parseInt(jedis.get("balance"));
        if (balance < amtToSubtract) {
            jedis.unwatch();
            System.out.println("modify");
            return false;
        } else {
            System.out.println("***********transaction");
            Transaction transaction = jedis.multi();
            transaction.decrBy("balance", amtToSubtract);
            transaction.incrBy("debt", amtToSubtract);
            transaction.exec();
            balance = Integer.parseInt(jedis.get("balance"));
            debt = Integer.parseInt(jedis.get("debt"));

            System.out.println("*******" + balance);
            System.out.println("*******" + debt);
            return true;
        }
    }
}
