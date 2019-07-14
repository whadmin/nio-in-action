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

import sun.misc.Cleaner;
import sun.misc.JavaLangRefAccess;
import sun.misc.SharedSecrets;

/**
 * Abstract base class for reference objects.  This class defines the
 * operations common to all reference objects.  Because reference objects are
 * implemented in close cooperation with the garbage collector, this class may
 * not be subclassed directly.
 *
 * @author   Mark Reinhold
 * @since    1.2
 */

public abstract class Reference<T> {


    /**
     * referent引用
     */
    private T referent;

    /**
     * referenceQueue单项链表队列（head指针在ReferenceQueue内部）
     */
    volatile ReferenceQueue<? super T> queue;

    /**
     * referenceQueue队列 Reference指向下一个引用
     */
    @SuppressWarnings("rawtypes")
    Reference next;

    /**
     * pending单项链表队列（head指针就pending）
     */
    private static Reference<Object> pending = null;


    /**
     * pending队列 Reference指向下一个引用
     */
    transient private Reference<T> discovered;


    static private class Lock { }
    private static Lock lock = new Lock();


    static {
        /** 获取当前线程最高线程父类的分组 **/
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        for (ThreadGroup tgn = tg;
             tgn != null;
             tg = tgn, tgn = tg.getParent());

        /** 实例ReferenceHandler并启动ReferenceHandler线程 **/
        Thread handler = new ReferenceHandler(tg, "Reference Handler");
        handler.setPriority(Thread.MAX_PRIORITY);
        handler.setDaemon(true);
        handler.start();

        SharedSecrets.setJavaLangRefAccess(new JavaLangRefAccess() {
            @Override
            public boolean tryHandlePendingReference() {
                return tryHandlePending(false);
            }
        });
    }

    /**
     * 实例化Reference
     */
    Reference(T referent) {
        this(referent, null);
    }

    /**
     * 实例化Reference
     */
    Reference(T referent, ReferenceQueue<? super T> queue) {
        this.referent = referent;
        this.queue = (queue == null) ? ReferenceQueue.NULL : queue;
    }


    private static class ReferenceHandler extends Thread {
        /** 确保类已经被初始化 **/
        private static void ensureClassInitialized(Class<?> clazz) {
            try {
                Class.forName(clazz.getName(), true, clazz.getClassLoader());
            } catch (ClassNotFoundException e) {
                throw (Error) new NoClassDefFoundError(e.getMessage()).initCause(e);
            }
        }

        static {
            /** 初始化InterruptedException **/
            ensureClassInitialized(InterruptedException.class);
            /** 初始化Cleaner **/
            ensureClassInitialized(Cleaner.class);
        }

        ReferenceHandler(ThreadGroup g, String name) {
            super(g, name);
        }

        /** run **/
        public void run() {
             /**  死循环调用  **/
            while (true) {
                tryHandlePending(true);
            }
        }
    }


    static boolean tryHandlePending(boolean waitForNotify) {
        Reference<Object> r;
        Cleaner c;
        try {
            synchronized (lock) {
                /** 判断是当前对象是否加入pending，且是队列首节点 **/
                if (pending != null) {
                    /** 当前对象从pending队列出队 **/
                    r = pending;
                    pending = r.discovered;
                    r.discovered = null;
                    /** 判断当前对象是否是Cleaner  **/
                    c = r instanceof Cleaner ? (Cleaner) r : null;
                } else {
                    /** 等待 **/
                    if (waitForNotify) {
                        lock.wait();
                    }
                    // retry if waited
                    return waitForNotify;
                }
            }
        } catch (OutOfMemoryError x) {
            Thread.yield();
            return true;
        } catch (InterruptedException x) {
            return true;
        }

        /** Cleaner清理工作 **/
        if (c != null) {
            c.clean();
            return true;
        }

        /** 加入ReferenceQueue队列 **/
        ReferenceQueue<? super Object> q = r.queue;
        if (q != ReferenceQueue.NULL) q.enqueue(r);
        return true;
    }


    /**
     * referent引用
     */
    public T get() {
        return this.referent;
    }

    /**
     * 清理referent引用
     */
    public void clear() {
        this.referent = null;
    }


    /**
     * referent引用对象是否为Enqueued状态,referent对象添加到ReferenceQueue队列
     */
    public boolean isEnqueued() {
        return (this.queue == ReferenceQueue.ENQUEUED);
    }

    /**
     * 将referent对象添加到ReferenceQueue队列
     */
    public boolean enqueue() {
        return this.queue.enqueue(this);
    }

}
