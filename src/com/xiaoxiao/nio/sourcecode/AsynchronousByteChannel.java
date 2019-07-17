/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.channels.*;
import java.util.concurrent.Future;

/**
 * 可以读写字节的异步通道。
 */

public interface AsynchronousByteChannel
    extends AsynchronousChannel
{
    /**
     * 将字节从这个通道读取到给定的缓冲区中。
     *
     * @param   <A>
     *          操作时附加到I/O操作的对象类型。
     * @param   dst
     *          从通过中读取获得字节的缓冲区
     * @param   attachment
     *          要附加到I/O操作的对象；可以是@code null
     * @param   handler
     *          IO完成回调处理程序
     *
     * @throws  IllegalArgumentException
     *          如果缓冲区是只读的
     * @throws  ReadPendingException
     *          若在上一个read（）方法未完成之前，再次调用read（）方法
     * @throws  ShutdownChannelGroupException
      *          如果通过和一个已经终止的AsynchronousChannelGroup关联
     */
    <A> void read(ByteBuffer dst,
                  A attachment,
                  CompletionHandler<Integer, ? super A> handler);

    /**
     * 将字节从这个通道读取到给定的缓冲区中。
     *
     * @param   dst
     *          从通过中读取获得字节的缓冲区
     *
     * @return  表示操作结果的Future
     *
     * @throws  IllegalArgumentException
     *          如果缓冲区是只读的
     * @throws  ReadPendingException
     *          若在上一个read（）方法未完成之前，再次调用read（）方法
     */
    Future<Integer> read(ByteBuffer dst);

    /**
     * 从给定缓冲区向此通道写入一个字节序列。

     * @param   <A>
     *          操作时附加到I/O操作的对象类型。
     * @param   src
     *          写入通道字节缓冲区
     * @param   attachment
     *          要附加到I/O操作的对象；可以是@code null
     * @param   handler
     *          IO完成回调处理程序
     *
     * @throws  WritePendingException
     *          若在上一个write（）方法未完成之前，再次调用write（）方法
     * @throws  ShutdownChannelGroupException
     *          如果通过和一个已经终止的AsynchronousChannelGroup关联
     */
    <A> void write(ByteBuffer src,
                   A attachment,
                   CompletionHandler<Integer, ? super A> handler);

    /**
     * 从给定缓冲区向此通道写入一个字节序列。
     *
     * @param   src
     *          写入通道字节缓冲区
     *
     * @return 表示操作结果的Future
     *
     * @throws  WritePendingException
     *          若在上一个write（）方法未完成之前，再次调用write（）方法
     */
    Future<Integer> write(ByteBuffer src);
}
