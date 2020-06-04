package com.ctgtmo.sshr.config;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @ClassName: RedisConfig.java
 * @Company: 乾通互联(北京)科技有限公司
 * @Description: redis配置
 * @author wanggongliang
 * @date 2018年12月4日 下午1:46:15
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
  @Bean
  RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
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

  public class Receiver {
    private CountDownLatch latch;

    @Autowired
    public Receiver(CountDownLatch latch) {
      this.latch = latch;
    }

    public void receiveMessage(String message) {
      latch.countDown();
    }
  }

}
