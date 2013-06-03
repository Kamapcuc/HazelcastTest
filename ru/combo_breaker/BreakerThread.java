package ru.combo_breaker;

import java.util.Map;
import java.util.concurrent.BlockingQueue;


public class BreakerThread extends Thread {
    protected Map<String, Integer> hazelcast;
    protected BlockingQueue<Boolean> queue;

    public BreakerThread(Map<String, Integer> hazelcast, BlockingQueue<Boolean> queue) {
        this.hazelcast = hazelcast;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                queue.take();
            } catch (InterruptedException ignored) {
            }
            Integer tmp = Const.version.incrementAndGet();
            hazelcast.put(Const.key, tmp);
//            uncomment to fix
//            hazelcast.get(Const.key);
        }
    }
}
