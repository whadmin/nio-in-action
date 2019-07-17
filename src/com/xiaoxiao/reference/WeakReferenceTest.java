package com.xiaoxiao.reference;

import java.lang.ref.WeakReference;

public class WeakReferenceTest {

    public static void main(String[] args) {


        ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();
        WeakReference salad = new WeakReference(new Object());
        //通过WeakReference的get()方法获取Apple
        System.out.println("ThreadLocal:" + salad.get());

        //开启代码threadLocal将被回收
        threadLocal=null;
        System.gc();
        try {
            //休眠一下，在运行的时候加上虚拟机参数-XX:+PrintGCDetails，输出gc信息，确定gc发生了。
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //如果为空，代表被回收了
        if (salad.get() == null) {
            System.out.println("clear");
        }
    }
}
