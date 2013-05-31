package ru.combo_breaker;

import com.hazelcast.impl.MProxyImpl;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class WatchDog extends Thread {
    private int LOOP_MAX = 5;
    protected Map<String, Integer> hazelcast;
    protected ConcurrentHashMap<String, Integer> elastic;
    protected BlockingQueue<Boolean> queue;

    public WatchDog(Map<String, Integer> hazelcast, ConcurrentHashMap<String, Integer> elastic,
                          BlockingQueue<Boolean> queue) {
        this.elastic = elastic;
        this.hazelcast = hazelcast;
        this.queue = queue;
    }

    private boolean checkEquals() {
        int el = elastic.get(Const.key);
        int hz = hazelcast.get(Const.key);
        boolean tmp = el == hz;
        if (!tmp)
            System.out.println(el + " != " + hz + " " + (el - hz));
        else
            System.out.println(el + " == " + hz);
        return tmp;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            boolean tmp = checkEquals();
            int cnt = 0;
            while (!tmp && (cnt++ < LOOP_MAX)) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ignored) {
                }
                tmp = checkEquals();
            }
            if (!tmp)  {
                ((MProxyImpl)hazelcast).flush();
                tmp = checkEquals();
                System.exit(100);
            }
            if (queue.peek() == null)
                try {
                    queue.put(true);
                    System.out.println("Queued for update " + (Const.version.get() + 1));
                } catch (InterruptedException ignored) {
                }
        }
    }
}
