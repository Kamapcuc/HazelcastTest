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
            System.out.println("Started update to " + tmp);
            elastic.put(Const.key, tmp);
            hazelcast.put(Const.key, tmp);
            System.out.println("Finished update to " + tmp);
//            uncomment to fix
//            hazelcast.get(Const.key);
        }
    }
}
