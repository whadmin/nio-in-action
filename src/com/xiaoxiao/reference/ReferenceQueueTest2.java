package com.xiaoxiao.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ReferenceQueueTest2 {

    private static ReferenceQueue<byte[]> referenceQueue = new ReferenceQueue<>();
    private static int _1M = 1024 * 1024;

    public static void main(String[] args) throws InterruptedException {
        final Map<Object, MyWeakReference> hashMap = new HashMap<>();
        Thread thread = new Thread(() -> {
            try {
                int n = 0;
                MyWeakReference k;
                while(null != (k = (MyWeakReference) referenceQueue.remove())) {
                    System.out.println((++n) + "回收了:" + k);
                    //反向获取，移除对应的entry
                    hashMap.remove(k.key);
                    //额外对key对象作其它处理，比如关闭流，通知操作等
                }
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();

        for(int i = 0;i < 10000;i++) {
            byte[] bytesKey = new byte[_1M];
            byte[] bytesValue = new byte[_1M];
            hashMap.put(bytesKey, new MyWeakReference(bytesKey, bytesValue, referenceQueue));
        }
    }

    static class MyWeakReference extends WeakReference<byte[]> {
        private Object key;
        MyWeakReference(Object key, byte[] referent, ReferenceQueue<? super byte[]> q) {
            super(referent, q);
            this.key = key;
        }
    }
}
