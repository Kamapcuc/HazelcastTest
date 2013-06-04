package ru.combo_breaker;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class WatchDog extends Thread {
    private int LOOP_MAX = 5;
    protected Map<String, Integer> hazelcast;
    protected BlockingQueue<Boolean> queue;

    public WatchDog(Map<String, Integer> hazelcast, BlockingQueue<Boolean> queue) {
        this.hazelcast = hazelcast;
        this.queue = queue;
    }

    private boolean checkEquals() {
        int etalon = Const.version.get();
        int hz = hazelcast.get(Const.key);
//        if (etalon == hz)
//            System.out.println(etalon + " == " + hz);
//        else
//            System.out.println(etalon + " != " + hz + " " + (etalon - hz));
        return etalon == hz;
    }

    @Override
    public void run() {
        try {
            while (true) {
                boolean tmp = checkEquals();
                int cnt = 0;
                while (!tmp && (cnt++ < LOOP_MAX)) {
                    Thread.sleep(1);       // in most cases 1 ms is enough
                    tmp = checkEquals();
                }
                if (!tmp) {
                    Thread.sleep(5000);    // i think 5 sec is enough to update cache
                    if (!checkEquals())    // but, it's not
                        System.exit(111);
                }
                if (queue.peek() == null)
                    queue.put(true);

            }
        } catch (InterruptedException ignored) {
        }
    }
}
