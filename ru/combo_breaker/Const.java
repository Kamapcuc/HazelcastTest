package ru.combo_breaker;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;

public class Const {
    public static final String key = "Some key";
    public static AtomicInteger version =  new AtomicInteger(1);
    public static HazelcastInstance instance = getHazelcastInstance();

    private static HazelcastInstance getHazelcastInstance(){
        String filename = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "ru/combo_breaker/hazelcast.xml";
        Config conf = null;
        try {
            conf = new XmlConfigBuilder(filename).build();
        } catch (FileNotFoundException ignored) {
        }
        return Hazelcast.newHazelcastInstance(conf);
    }
}
