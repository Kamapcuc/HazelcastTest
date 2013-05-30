package ru.combo_breaker;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;


public class Main {


    public static void main(String[] args) throws IOException {
        BlockingQueue<Boolean> q = new ArrayBlockingQueue<Boolean>(1);

        Map<String, Integer> hazel = Const.instance.getMap("repo");
        ConcurrentHashMap<String, Integer> elastic = new ConcurrentHashMap<String, Integer>();
        hazel.put(Const.key, 1);
        elastic.put(Const.key, 1);

        BreakerThread tmp = new BreakerThread(hazel, elastic);
        new WatchDog(hazel, elastic, tmp).start();
        tmp.start();
 //       new BreakerThread(hazel, elastic, q).start();
//        new BreakerThread(hazel, elastic).start();
    }
}
