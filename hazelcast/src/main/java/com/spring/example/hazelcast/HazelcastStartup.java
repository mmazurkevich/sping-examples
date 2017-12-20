package com.spring.example.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastStartup {

    public static void main(String[] args) {
        //TODO:: http://docs.hazelcast.org/docs/2.0/manual/html/ch13.html
        Config config = new Config();
        NetworkConfig networkConfig = config.getNetworkConfig();
        networkConfig.getJoin().getMulticastConfig().setEnabled(false);
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
