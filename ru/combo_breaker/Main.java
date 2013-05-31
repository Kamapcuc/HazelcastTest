package ru.combo_breaker;

import com.hazelcast.core.EntryListener;
import com.hazelcast.impl.MProxyImpl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;


public class Main {


    public static void main(String[] args) throws IOException {
        BlockingQueue<Boolean> q = new ArrayBlockingQueue<Boolean>(1);

        Map<String, Integer> hazelcast = Const.instance.getMap("repo");
        ConcurrentHashMap<String, Integer> elastic = new ConcurrentHashMap<String, Integer>();
        EntryListener majordomo = new KeyholeWatcher();
        ((MProxyImpl)hazelcast).addEntryListener(majordomo, Const.key, true);

        hazelcast.put(Const.key, 1);
        elastic.put(Const.key, 1);


        new BreakerThread(hazelcast, elastic, q).start();
        new WatchDog(hazelcast, elastic, q).start();
 //       new BreakerThread(hazelcast, elastic, q).start();
//        new BreakerThread(hazelcast, elastic).start();
    }
}
