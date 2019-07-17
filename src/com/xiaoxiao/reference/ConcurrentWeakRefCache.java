package com.xiaoxiao.reference;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentWeakRefCache<K,V> {

    private final int size;
    private final Map<K,V> eden;
    private final Map<K,V> longterm;

    public ConcurrentWeakRefCache(int size) {
        this.size = size;
        this.eden = new ConcurrentHashMap<>(size);
        this.longterm = new WeakHashMap<>(size);
    }

    public V get(K k) {
        // 先从eden中取
        V v = this.eden.get(k);
        if (v == null) {
            // 如果取不到再从longterm中取
            synchronized (longterm) {
                v = this.longterm.get(k);
            }
            // 如果取到则重新放到eden中
            if (v != null) {
                this.eden.put(k, v);
            }
        }
        return v;
    }

    public void put(K k, V v) {
        if (this.eden.size() >= size) {
            // 如果eden中的元素数量大于指定容量，将所有元素放到longterm中
            synchronized (longterm) {
                this.longterm.putAll(this.eden);
            }
            this.eden.clear();
        }
        this.eden.put(k, v);
    }
}
