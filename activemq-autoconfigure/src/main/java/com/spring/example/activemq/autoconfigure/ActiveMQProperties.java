package com.spring.example.activemq.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Mikhail on 24.03.2017.
 */
@ConfigurationProperties(prefix = "activemq.properties")
public class ActiveMQProperties {

    private boolean inMemory = true;

    private boolean pool = false;

    private InnerPoolConfig innerPoolConfig = new InnerPoolConfig();

    public boolean isInMemory() {
        return inMemory;
    }

    public void setInMemory(boolean inMemory) {
        this.inMemory = inMemory;
    }

    public boolean isPool() {
        return pool;
    }

    public void setPool(boolean pool) {
        this.pool = pool;
    }

    public InnerPoolConfig getInnerPoolConfig() {
        return innerPoolConfig;
    }

    public void setInnerPoolConfig(InnerPoolConfig innerPoolConfig) {
        this.innerPoolConfig = innerPoolConfig;
    }

    public static class InnerPoolConfig {

        /**
         * Maximum number of pooled connections.
         */
        private int maxConnections = 1;

        public int getMaxConnections() {
            return maxConnections;
        }

        public void setMaxConnections(int maxConnections) {
            this.maxConnections = maxConnections;
        }
    }
}
