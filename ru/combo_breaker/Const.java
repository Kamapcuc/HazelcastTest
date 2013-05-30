package ru.combo_breaker;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Const {
    public static final String TO_BRAKE = "Sherlock Holmes";
    public static AtomicInteger version =  new AtomicInteger(1);
    public static HazelcastInstance instance = getHazelcastInstance();

    private static HazelcastInstance getHazelcastInstance(){
        File f = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "ru/combo_breaker/hazelcast.xml");
        char[] buf = new char[(int) f.length()];
        try {
            new FileReader(f).read(buf, 0, (int) f.length());
        } catch (IOException ignored) {
        }
        return Hazelcast.newHazelcastInstance(new Config().setXmlConfig(new String(buf)));
    }
}
