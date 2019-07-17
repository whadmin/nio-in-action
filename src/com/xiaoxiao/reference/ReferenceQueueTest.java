package com.xiaoxiao.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ReferenceQueueTest {

    private static ReferenceQueue<byte[]> rq = new ReferenceQueue<>();
    private static int _1M = 1024 * 1024;

    public static void main(String[] args) {
        Object value = new Object();
        Map<WeakReference<byte[]>, Object> map = new HashMap<>();
        Thread thread = new Thread(ReferenceQueueTest::run);
        thread.setDaemon(true);
        thread.start();

        for(int i = 0;i < 100;i++) {
            byte[] bytes = new byte[_1M];
            WeakReference<byte[]> weakReference = new WeakReference<>(bytes, rq);
            map.put(weakReference, value);
            bytes=null;
        }
        System.out.println("map_read.txt.size->" + map.size());

        int aliveNum = 0;
        for (Map.Entry<WeakReference<byte[]>, Object> entry : map.entrySet()){
            if (entry != null){
                if (entry.getKey().get() != null){
                    aliveNum++;
                }
            }
        }
        System.out.println("total" + aliveNum);
    }

    private static void run() {
        try {
            int n = 0;
            WeakReference k;
            while ((k = (WeakReference) rq.remove()) != null) {
                System.out.println((++n) + "clear:" + k);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
