package ru.combo_breaker;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class WatchDog extends AbstractThread {
    private int LOOP_MAX = 20;

    public WatchDog(Map<String, IntObj> hazelcast,
                    ConcurrentHashMap<String, IntObj> elastic,
                    BlockingQueue<Boolean> queue) {
        super(hazelcast, elastic, queue);
    }

    private boolean checkEquals() {
        IntObj el = elastic.get(Const.TO_BRAKE);
        IntObj hz = hazelcast.get(Const.TO_BRAKE);
        boolean tmp = el.equals(hz);
        if (!tmp)
            System.out.println(el + " != " + hz);
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
