package com.xiaoxiao.reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Set;

/**
 * 创建软引用时，内存不足会去回收软引用的对象，但对于SoftReference本身同样需要记得回收
 *
 * 本例中通过监听ReferenceQueue中对象堆SoftReference对象进行清理
 */
public class SoftReferenceFreeRefObject {

    public static int removedSoftRefs = 0;

    public static class MyBigObject{
        int[] data = new int[128];
    }

    public static int CACHE_INITIAL_CAPACITY = 100_000;
    public static Set<SoftReference<MyBigObject>> cache = new HashSet<>(CACHE_INITIAL_CAPACITY);
    public static ReferenceQueue<MyBigObject> referenceQueue = new ReferenceQueue<>();

    public static void main(String[] args) {
        for (int i = 0; i < 100_000; i++) {
            MyBigObject obj = new MyBigObject();
            /** 创建一个软引用对象指向obj，并设置referenceQueue **/
            cache.add(new SoftReference<>(obj, referenceQueue));
            /** 清理cache中软引用已经被释放的SoftReference对象 **/
            clearUselessReferences();
        }
        System.out.println("End, removed soft references=" + removedSoftRefs);
    }

    /**
     * 清理cache中软引用已经被释放的SoftReference对象
     */
    public static void clearUselessReferences() {
        Reference<? extends MyBigObject> ref = referenceQueue.poll();
        while (ref != null) {
            /**  **/
            if (cache.remove(ref)) {
                removedSoftRefs++;
            }
            ref = referenceQueue.poll();
        }
    }
}
