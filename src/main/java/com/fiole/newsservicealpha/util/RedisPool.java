package com.fiole.newsservicealpha.util;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    //声明成static的原因：保证jedis连接池在tomcat启动时就加载出来
    //jedis连接池
    private static JedisPool pool;
    //与redis连接池连接的最大连接数
    private static int maxTotal = 20;
    //在这个连接池中最多有多少个状态为idle的jedis实例，jedis连接池里就是jedis的实例，idle就是空闲的jedis实例
    //在jedis连接池中最大的idle状态（空闲的）的jedis实例的个数
    private static int maxIdle = 10;
    //在jedis连接池中最小的idle状态（空闲的）的jedis实例的个数
    private static Integer minIdle = 2;

    //在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值为true，则得到的jedis实例肯定是可用的
    private static boolean testOnBorrow = true;
    //在return一个jedis实例的时候，是否要进行验证操作，如果赋值为true，则返回jedis连接池的jedis实例肯定是可用的
    private static boolean testOnReturn = true;

    private static String redisIp = "localhost";
    private static int redisPort = 6379;

    //初始化连接池，只会调用一次
    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        //连接池耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时，会抛出超时异常，默认为true
        config.setBlockWhenExhausted(true);

        //这里超时时间是2s
        pool = new JedisPool(config, redisIp, redisPort, 1000 * 2);
    }

    static {
        initPool();
    }

    //从连接池中拿取一个实例
    public static Jedis getJedis() {
        return pool.getResource();
    }
}
