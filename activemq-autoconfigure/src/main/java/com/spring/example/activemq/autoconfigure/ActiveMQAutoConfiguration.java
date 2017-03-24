package com.spring.example.activemq.autoconfigure;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

/**
 * Created by Mikhail on 24.03.2017.
 */
@Configuration
@AutoConfigureBefore(JmsAutoConfiguration.class)
@ConditionalOnClass({ ConnectionFactory.class, ActiveMQConnectionFactory.class })
@ConditionalOnMissingBean(ConnectionFactory.class)
@EnableConfigurationProperties({ActiveMQProperties.class,JmsProperties.class})
public class ActiveMQAutoConfiguration {

    @Autowired
    private ActiveMQProperties properties;

    @Autowired
    private JmsProperties jmsProperties;

    private final String EMBEDDED_DEFAULT_URL = "vm://localhost?broker.persistent=false";
    private final String DEFAULT_URL = "tcp://localhost:61616";

    @Bean
    @ConditionalOnProperty(prefix = "activemq.properties", name = "pool", havingValue = "false", matchIfMissing = true)
    public ConnectionFactory connectionFactoryNoPool() {
        if (properties.isInMemory())
            return new ActiveMQConnectionFactory(EMBEDDED_DEFAULT_URL);
        else
            return new ActiveMQConnectionFactory(DEFAULT_URL);
    }

    @Bean
    @ConditionalOnProperty(prefix = "activemq.properties", name = "pool", havingValue = "true")
    public ConnectionFactory connectionFactoryPool() {
        //sending message to the topic
        jmsProperties.setPubSubDomain(true);
        PooledConnectionFactory pooledConnectionFactory;
        if (properties.isInMemory())
            pooledConnectionFactory = new PooledConnectionFactory(new ActiveMQConnectionFactory(EMBEDDED_DEFAULT_URL));
        else
            pooledConnectionFactory = new PooledConnectionFactory(new ActiveMQConnectionFactory(DEFAULT_URL));
        pooledConnectionFactory.setMaxConnections(properties.getInnerPoolConfig().getMaxConnections());
        return pooledConnectionFactory;
    }
}
