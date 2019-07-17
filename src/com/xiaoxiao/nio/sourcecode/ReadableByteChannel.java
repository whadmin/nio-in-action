/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;


/**
 * 一个可以读取字节的通道。
 *
 * 在一个可读通道上只能进行一个读取操作任何给定时间。如果一个线程在通道上启动读取操作
 * 然后，任何其他试图启动另一个读取操作的线程都将阻止，直到第一个操作完成。是否有其他种类的
 * I/O操作可以与读操作同时进行，具体取决于通道的类型。
 */

public interface ReadableByteChannel extends Channel {

    /**
     * 将一系列字节从这个通道读取到给定的缓冲区中。（同步）
     *
     * @param  dst
     *         读取通道写入字节的缓冲区
     *
     * @return  读取的字节数
     *
     * @throws  NonReadableChannelException
     *          如果没有打开此通道进行读取
     *
     * @throws  ClosedChannelException
     *          如果该通道关闭
     *
     * @throws  AsynchronousCloseException
     *          如果另一个线程关闭此通道读取操作正在进行时
     *
     * @throws  ClosedByInterruptException
     *          如果另一个线程中断当前线程读取操作，正在此时关闭通道并设置当前线程的中断状态
     *
     * @throws  IOException
     *          如果发生其他I/O错误
     */
    public int read(ByteBuffer dst) throws IOException;

}
