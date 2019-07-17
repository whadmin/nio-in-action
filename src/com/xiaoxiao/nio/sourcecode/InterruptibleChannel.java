/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
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

/*
 */

package com.xiaoxiao.nio.sourcecode;

import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.Channel;
import java.nio.channels.ClosedByInterruptException;


/**
 * 接口的主要作用是使通道能以异步的方式进行关闭与中断
 *
 * 如果一个线程在同时实现asynchronously和InterruptibleChannel通道上出现了阻塞状态，那么当其他线程调用这个通道的close()方法时，
 * 这个呈阻塞状态的线程将接收到AsynchronousCloseException 异常。
 *
 * 如果一个线程在同时实现asynchronously和InterruptibleChannel通道上出现了阻塞状态，那么当其他线程调用这个阻塞线程的
 * interrupt（）方法后，通道将被关闭，这个阻塞的线程将接收到ClosedByinterruptException异常
 *
 */
public interface InterruptibleChannel
    extends Channel
{

    /**
     * 关闭此通道
     *
     * 当前在此通道上的I/O操作中被阻止的任何线程都将收到 {@link AsynchronousCloseException}.
     *
     * @throws  IOException  如果发生I/O错误
     */
    public void close() throws IOException;

}
