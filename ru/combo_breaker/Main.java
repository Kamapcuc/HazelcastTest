package ru.combo_breaker;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;


public class Main {


    public static void main(String[] args) throws IOException {
        BlockingQueue<Boolean> q = new ArrayBlockingQueue<Boolean>(1);

        Map<String, IntObj> hazel = Const.instance.getMap("repo");
        hazel.put("Sherlock Holmes",new IntObj(1));
        hazel.put("Doctor Watson", new IntObj(2));
        hazel.put("Professor Moriarti", new IntObj(3));

        ConcurrentHashMap<String, IntObj> elastic = new ConcurrentHashMap<String, IntObj>();
        elastic.put("Sherlock Holmes", new IntObj(1));
        elastic.put("Doctor Watson", new IntObj(2));
        elastic.put("Professor Moriarti", new IntObj(3));

        new WatchDog(hazel, elastic, q).start();
        new BreakerThread(hazel, elastic, q).start();
 //       new BreakerThread(hazel, elastic, q).start();
//        new BreakerThread(hazel, elastic).start();
    }
}
