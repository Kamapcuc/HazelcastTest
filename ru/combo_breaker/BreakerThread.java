package ru.combo_breaker;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;


public class BreakerThread extends AbstractThread {


    public BreakerThread(Map<String, IntObj> hazelcast,
                         ConcurrentHashMap<String, IntObj> elastic, BlockingQueue<Boolean> queue) {
        super(hazelcast, elastic, queue);
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                queue.take();
            } catch (InterruptedException ignored) { }
            IntObj tmp = new IntObj(Const.version.incrementAndGet());
            elastic.put(Const.TO_BRAKE, tmp);
            hazelcast.put(Const.TO_BRAKE, tmp);
//            elastic.get(Const.TO_BRAKE);
//            hazelcast.get(Const.TO_BRAKE);
        }
    }
}
