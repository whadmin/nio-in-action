/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
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



public class SoftReference<T> extends Reference<T> {

    /**
     * 由垃圾回收器负责更新的时间戳
     */
    static private long clock;

    /**
     * 在get方法调用时更新的时间戳，当虚拟机选择软引用进行清理时，可能会参考这个字段。
     */
    private long timestamp;

    /**
     * 实例化SoftReference
     */
    public SoftReference(T referent) {
        super(referent);
        this.timestamp = clock;
    }

    /**
     * 实例化SoftReference
     */
    public SoftReference(T referent, ReferenceQueue<? super T> q) {
        super(referent, q);
        this.timestamp = clock;
    }

    /**
     * 返回引用指向的对象，如果referent已经被程序或者垃圾回收器清理，则返回null。
     */
    public T get() {
        T o = super.get();
        if (o != null && this.timestamp != clock)
            this.timestamp = clock;
        return o;
    }

}
