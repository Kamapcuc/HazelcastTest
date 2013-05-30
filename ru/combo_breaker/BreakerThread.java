package ru.combo_breaker;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;


public class BreakerThread extends Thread {
    protected Map<String, Integer> hazelcast;
    protected ConcurrentHashMap<String, Integer> elastic;
    protected BlockingQueue<Boolean> queue;

    public BreakerThread(Map<String, Integer> hazelcast, ConcurrentHashMap<String, Integer> elastic,
                          BlockingQueue<Boolean> queue) {
        this.elastic = elastic;
        this.hazelcast = hazelcast;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                queue.take();
            } catch (InterruptedException ignored) { }
            Integer tmp = Const.version.incrementAndGet();
            elastic.put(Const.TO_BRAKE, tmp);
            hazelcast.put(Const.TO_BRAKE, tmp);
//            uncomment to fix
//            hazelcast.get(Const.TO_BRAKE);
        }
    }
}
