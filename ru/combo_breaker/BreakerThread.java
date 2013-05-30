package ru.combo_breaker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


public class BreakerThread extends Thread {
    private Map<String, Integer> hazelcast;
    private ConcurrentHashMap<String, Integer> elastic;
    private AtomicBoolean flag;

    public BreakerThread(Map<String, Integer> hazelcast, ConcurrentHashMap<String, Integer> elastic,
                         AtomicBoolean flag) {
        this.elastic = elastic;
        this.hazelcast = hazelcast;
        this.flag = flag;
    }

    @Override
    public void run() {
        while (true) {
            if (flag.getAndSet(false)) {
                Integer tmp = Const.version.incrementAndGet();
                elastic.put(Const.key, tmp);
                hazelcast.put(Const.key, tmp);
//            uncomment to fix
//            hazelcast.get(Const.key);
            }
        }
    }
}
