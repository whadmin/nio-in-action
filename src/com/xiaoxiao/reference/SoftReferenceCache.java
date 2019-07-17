package com.xiaoxiao.reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class SoftReferenceCache<T> {

    /** ReferenceQueue引用对象 **/
    private ReferenceQueue<T> referenceQueue = new ReferenceQueue<>();
    /** 保存Reference对象的列表 **/
    private List<Reference<T>> list = new ArrayList<>();

    /**
     * 将对象添加到缓存中
     */
    public synchronized void add(T obj){
        /** 构建软引用 **/
        Reference<T> reference = new SoftReference<T>(obj, referenceQueue);
        /** 加入列表中  **/
        list.add(reference);
    }

    /**
     * 获取缓存对象
     */
    public synchronized T get(int index){
        /** 清除失效的缓存 **/
        clear();
        if (index < 0 || list.size() < index){
            return null;
        }
        /** 获取Reference对象 **/
        Reference<T> reference = list.get(index);
        /** 返回Reference引用 **/
        return reference == null ? null : reference.get();
    }


    /**
     * 清除失效的缓存
     * 当reference引用对象被回收 reference对象会被加入referenceQueue
     * 遍历referenceQueue中的对象，将被回收的对象从缓存中清除
     */
    @SuppressWarnings("unchecked")
    private void clear(){
        Reference<T> reference;
        while (null != (reference = (Reference<T>) referenceQueue.poll())){
            list.remove(reference);
        }
    }

    public int size(){
        return list.size();
    }
}
