package ru.combo_breaker;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


public class Main {

    public static void main(String[] args) throws IOException {
        AtomicBoolean tmp = new AtomicBoolean(false);

        Map<String, Integer> hazel = Const.instance.getMap("repo");
        ConcurrentHashMap<String, Integer> elastic = new ConcurrentHashMap<String, Integer>();
        hazel.put(Const.key, 1);
        elastic.put(Const.key, 1);

        new BreakerThread(hazel, elastic, tmp).start();
        new WatchDog(hazel, elastic, tmp).start();
 //       new BreakerThread(hazel, elastic, q).start();
//        new BreakerThread(hazel, elastic).start();
    }
}
