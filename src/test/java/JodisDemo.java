import com.wandoulabs.jodis.JedisResourcePool;
import com.wandoulabs.jodis.RoundRobinJedisPool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Date;

/**
 * zookeeper 返回的地址不能直接访问
 * Created by moyong on 15/8/24.
 */
public class JodisDemo {
    public static void main(String[] args) throws IOException {
        JedisResourcePool jedisPool = new RoundRobinJedisPool("192.168.59.103:32862", 30000, "/zk/codis/db_test/proxy", new JedisPoolConfig());
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.set("foo", "bar");
//            String value = jedis.get("foo");
//        }
        Jedis jedis = jedisPool.getResource();
            jedis.set("foo", "bar");
            String value = jedis.get("foo");


    }
}
