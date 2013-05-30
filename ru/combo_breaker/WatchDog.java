package ru.combo_breaker;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class WatchDog extends Thread {
    private int LOOP_MAX = 20;
    protected Map<String, int> hazelcast;
    protected ConcurrentHashMap<String, int> elastic;
    protected BlockingQueue<Boolean> queue;

    public WatchDog(Map<String, int> hazelcast, ConcurrentHashMap<String, int> elastic,
                          BlockingQueue<Boolean> queue) {
        this.elastic = elastic;
        this.hazelcast = hazelcast;
        this.queue = queue;
    }

    private boolean checkEquals() {
        int el = elastic.get(Const.TO_BRAKE);
        int hz = hazelcast.get(Const.TO_BRAKE);
        boolean tmp = el.equals(hz);
        if (el == hz)
            System.out.println(el + " != " + hz + " " + el.compareTo(hz));
        else
            System.out.println(el + " == " + hz + " " + el.compareTo(hz));
        return tmp;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            while (queue.peek() != null) {

            }
            boolean tmp = checkEquals();
            int cnt = 0;
            while (!tmp && (cnt++ < LOOP_MAX)) {
                try {
                    Thread.sleep((long) (Math.random() * 100));
                } catch (InterruptedException ignored) {
                }
                tmp = checkEquals();
            }
            if (!tmp)
                System.exit(100);
            if (queue.peek() == null)
                try {
                    queue.put(true);
                } catch (InterruptedException ignored) {
                }
        }
    }
}
