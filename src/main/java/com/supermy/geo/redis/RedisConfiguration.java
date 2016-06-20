package com.supermy.geo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.CountDownLatch;

@Configuration
public class RedisConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfiguration.class);

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
											MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	Receiver receiver(CountDownLatch latch) {
		return new Receiver(latch);
	}

	@Bean
	CountDownLatch latch() {
		return new CountDownLatch(1);
	}

	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}


    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {

//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxActive(100);
//		config.setMaxIdle(20);
//		config.setMaxWait(1000l);
//		config.setTestOnBorrow(false);
//
//		jedisPool = new JedisPool(config, "127.0.0.1", 6379);


		JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        JedisConnectionFactory ob = new JedisConnectionFactory(poolConfig);
        ob.setUsePool(true);
        ob.setHostName("127.0.0.1");
        //ob.setHostName("myredis_redis_1");//myredis is docker link-name
        ob.setPort(6379);
        return ob;
    }

//    @Bean
//    public StringRedisTemplate  stringRedisTemplate(){
//        return new StringRedisTemplate(jedisConnectionFactory());
//    }

	public static void main(String[] args) throws InterruptedException {

		ApplicationContext ctx = SpringApplication.run(RedisConfiguration.class, args);

        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);

        //StringRedisTemplate template = stringRedisTemplate();
        System.out.println(template.getConnectionFactory());

		CountDownLatch latch = ctx.getBean(CountDownLatch.class);

		LOGGER.error("Sending message...");
		template.convertAndSend("chat", "Hello from Redis!");

		latch.await();

		System.exit(0);
	}
}
