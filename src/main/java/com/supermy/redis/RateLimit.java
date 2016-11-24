package com.supermy.redis;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.FileCopyUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moyong on 16/11/22.
 */
public class RateLimit {

    private JedisPool jedisPool;
    private String script;
    // 省略了构造方法
    public void init() throws Exception {
        ClassPathResource resource = new ClassPathResource("redis/ratelimiting.lua");
        script = FileCopyUtils.copyToString(new EncodedResource(resource, "UTF-8").getReader());
    }

    /**
     * 提供限制速率的功能
     *
     * @param key        关键字
     * @param expireTime 过期时间
     * @param count      在过期时间内可以访问的次数
     * @return 没有超过指定次数则返回true, 否则返回false
     */
    public boolean isExceedRate(String key, long expireTime, int count) {
        List<String> params = new ArrayList<String>();
        params.add(Long.toString(expireTime));
        params.add(Integer.toString(count));
        Jedis jedis = jedisPool.getResource();
        //try(jedis!=null) {
            List<String> keys = new ArrayList<String>(1);
            keys.add(key);
            Long canSend = (Long) jedis.eval(script, keys, params);
            return canSend == 0;
        //}
    }

}
