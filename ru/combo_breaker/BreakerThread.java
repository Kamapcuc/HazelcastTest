package ru.combo_breaker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class BreakerThread extends Thread {
    protected Map<String, Integer> hazelcast;
    protected ConcurrentHashMap<String, Integer> elastic;

    public BreakerThread(Map<String, Integer> hazelcast, ConcurrentHashMap<String, Integer> elastic) {
        this.elastic = elastic;
        this.hazelcast = hazelcast;
    }

    @Override
    public void run() {
        while (true)
        {
            Integer tmp = Const.version.incrementAndGet();
            elastic.put(Const.key, tmp);
            hazelcast.put(Const.key, tmp);
//            uncomment to fix
//            hazelcast.get(Const.key);
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException integer) {
                }
            }

        }
    }
}
