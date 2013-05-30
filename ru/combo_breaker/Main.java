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
        hazel.put("Sherlock Holmes", 1);
        hazel.put("Doctor Watson", 2);
        hazel.put("Professor Moriarti", 3);

        ConcurrentHashMap<String, Integer> elastic = new ConcurrentHashMap<String, Integer>();
        elastic.put("Sherlock Holmes", 1);
        elastic.put("Doctor Watson", 2);
        elastic.put("Professor Moriarti", 3);

        new BreakerThread(hazel, elastic, q).start();
        new WatchDog(hazel, elastic, q).start();
 //       new BreakerThread(hazel, elastic, q).start();
//        new BreakerThread(hazel, elastic).start();
    }
}
