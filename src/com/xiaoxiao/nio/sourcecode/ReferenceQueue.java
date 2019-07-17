/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.xiaoxiao.nio.sourcecode;

/**
 * Reference queues, to which registered reference objects are appended by the
 * garbage collector after the appropriate reachability changes are detected.
 *
 * @author   Mark Reinhold
 * @since    1.2
 */

public class ReferenceQueue<T> {

    /**
     * Constructs a new reference-object queue.
     */
    public ReferenceQueue() { }

    private static class Null<S> extends ReferenceQueue<S> {
        boolean enqueue(Reference<? extends S> r) {
            return false;
        }
    }

    /** 标识Reference对象状态 **/
    static ReferenceQueue<Object> NULL = new Null<>();
    static ReferenceQueue<Object> ENQUEUED = new Null<>();

    /** 同步状态锁 **/
    static private class Lock { };
    private Lock lock = new Lock();


    /**
     * 链表头部引用
     */
    private volatile Reference<? extends T> head = null;

    /**
     * 链表节点长度
     */
    private long queueLength = 0;

    /**
     * 这个方法仅会被Reference中ReferenceHandler线程调用
     * 将Reference加入ReferenceQueue队列中
     */
    boolean enqueue(Reference<? extends T> r) { /* Called only by Reference class */
        synchronized (lock) {
            /**
             * 如果Reference对象没有设置queue，或者已经加入ReferenceQueue队列中 (queue == ENQUEUED)
             * 直接返回false
             * **/
            ReferenceQueue<?> queue = r.queue;
            if ((queue == NULL) || (queue == ENQUEUED)) {
                return false;
            }
            /** 判断 **/
            assert queue == this;

            /** 标识Reference对象加入ReferenceQueue队列   **/
            r.queue = ENQUEUED;
            /** 将Reference加入ReferenceQueue队列中 **/
            r.next = (head == null) ? r : head;
            head = r;
            /** 队列数量+1 **/
            queueLength++;
            /** 如果Reference对象为FinalReference 引用数量+1 **/
            if (r instanceof FinalReference) {
                sun.misc.VM.addFinalRefCount(1);
            }
            lock.notifyAll();
            return true;
        }
    }

    /**
     * 从队列头部弹出节点
     */
    public Reference<? extends T> poll() {
        if (head == null)
            return null;
        synchronized (lock) {
            return reallyPoll();
        }
    }

    @SuppressWarnings("unchecked")
    private Reference<? extends T> reallyPoll() {       /* Must hold lock */
        Reference<? extends T> r = head;
        /** 将Reference从ReferenceQueue队列中取出 **/
        if (r != null) {
            /** 获取ReferenceQueue队列head之后Reference对象 **/
            head = (r.next == r) ?
                null :
                r.next;

            /** 标识Reference对象从ReferenceQueue队列中被取出  **/
            r.queue = NULL;
            r.next = r;

            /** 队列数量+1 **/
            queueLength--;

            /** 如果Reference对象为FinalReference 引用数量+1 **/
            if (r instanceof FinalReference) {
                sun.misc.VM.addFinalRefCount(-1);
            }
            /** 返回 **/
            return r;
        }
        return null;
    }

    /**
     * 删除队列元素
     */
    public Reference<? extends T> remove() throws InterruptedException {
        return remove(0);
    }


    /**
     * 移除并返回队列首节点，此方法将阻塞到获取到一个Reference对象或者超时才会返回
     *  timeout时间的单位是毫秒
     */
    public Reference<? extends T> remove(long timeout)
        throws IllegalArgumentException, InterruptedException
    {
        if (timeout < 0) {
            throw new IllegalArgumentException("Negative timeout value");
        }
        synchronized (lock) {
            Reference<? extends T> r = reallyPoll();
            if (r != null) return r;
            long start = (timeout == 0) ? 0 : System.nanoTime();
            for (;;) {
                lock.wait(timeout);
                r = reallyPoll();
                if (r != null) return r;
                if (timeout != 0) {
                    long end = System.nanoTime();
                    timeout -= (end - start) / 1000_000;
                    if (timeout <= 0) return null;
                    start = end;
                }
            }
        }
    }


}
