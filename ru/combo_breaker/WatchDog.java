package ru.combo_breaker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WatchDog extends Thread {
    private int LOOP_MAX = 20;
    protected Map<String, Integer> hazelcast;
    protected ConcurrentHashMap<String, Integer> elastic;
    protected BreakerThread breaker;

    public WatchDog(Map<String, Integer> hazelcast, ConcurrentHashMap<String, Integer> elastic,
                    BreakerThread breaker) {
        this.elastic = elastic;
        this.hazelcast = hazelcast;
        this.breaker = breaker;
    }

    private boolean checkEquals() {
        int el = elastic.get(Const.key);
        int hz = hazelcast.get(Const.key);
        boolean tmp = el == hz;
        if (!tmp)
            System.out.println(el + " != " + hz + " " + (el - hz));
//        else
//            System.out.println(el + " == " + hz + " " + (el - hz));
        return tmp;
    }

    @Override
    public void run() {
        while (true) {
            boolean tmp = checkEquals();
            int cnt = 0;
            while (!tmp && (cnt++ < LOOP_MAX)) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
                tmp = checkEquals();
            }
            if (!tmp)
                System.exit(100);
            synchronized (breaker) {
            if (breaker.getState() == Thread.State.WAITING)
                breaker.notify();
            }
        }
    }
}
