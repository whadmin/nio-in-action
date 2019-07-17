package com.xiaoxiao.reference;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * -verbose:gc -Xms4m -Xmx4m -Xmn2m
 * 由于是软引用超过堆内存大小并不会堆内存溢出抛异常。
 */
public class SoftReferenceAuoFree {

    static class OOMClass{
        private int[] oom = new int[1024 * 100];// 100KB
    }

    public static void main(String[] args) throws InterruptedException {
        List<SoftReference> list = new ArrayList<SoftReference>();
        while(true){
            for (int i = 0; i < 100; i++) {
                /** OOMClass对象仅仅被软引用可达，在内存不足时会自动回收**/
                list.add(new SoftReference(new OOMClass()));
            }
            Thread.sleep(500);
        }
    }
}
