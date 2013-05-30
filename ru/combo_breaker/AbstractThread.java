package ru.combo_breaker;


import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractThread extends Thread {
    protected Map<String, IntObj> hazelcast;
    protected ConcurrentHashMap<String, IntObj> elastic;
    protected BlockingQueue<Boolean> queue;

    public AbstractThread(Map<String, IntObj> hazelcast, ConcurrentHashMap<String, IntObj> elastic,
                    BlockingQueue<Boolean> queue) {
        super();
        this.elastic = elastic;
        this.hazelcast = hazelcast;
        this.queue = queue;
    }

}
