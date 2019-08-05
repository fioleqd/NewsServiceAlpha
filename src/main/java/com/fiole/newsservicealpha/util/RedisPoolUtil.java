package com.fiole.newsservicealpha.util;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class RedisPoolUtil {
    //重新设置有效期
    //参数只有key和有效期，因为只需要根据key设置有效期即可
    public static Long expire(String key, int exTime) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            //设置有效期
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("setex key:{} error", key, e);
            return result;
        }finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    //exTime单位是s，设置session有效时间
    //当用户初次登录的时候，需要设置有限期，存在redis session中
    //后续如果用户再次请求登录，则只需要调用expire，重新设置有效期即可
    public static String setEx(String key, String value, int exTime) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key:{} value:{} error", key, value, e);
            return result;
        }finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    public static Long setNx(String key,String value){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setnx(key,value);
        }catch (Exception e){
            log.error("setnx key:{} value:{} error",key,value,e);
            return result;
        }finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    public static String set(String key, String value) {
        Jedis jedis = null;
        //jedis返回的结果
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            //设置key-value
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error", key, value, e);
            return result;
        }finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    public static String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            //根据key获取value值
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("set key:{} error", key, e);
            return result;
        }finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    public static Long del(String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            //根据key删除key-value
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("set key:{} error", key, e);
            return result;
        }finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }
}
