/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.*;


/**
 * 将此通道中的字节序列读取到给定缓冲区数组中。
 */

public interface ScatteringByteChannel
    extends ReadableByteChannel
{

    /**
     * 将一个字节序列从此通道读取到给定缓冲区的子序列中。
     *
     * @param  dsts
     *         要传输字节的缓冲区数组
     *
     * @param  offset
     *         第一个读取通道的缓冲区，在数组的位置
     *
     * @param  length
     *         从offset开始数组中多少个缓冲区被通道读取数据写入
     *
     * @return 读取的字节数
     *
     * @throws  IndexOutOfBoundsException
     *          如果写入缓冲区字节大于字节数组总字节大小
     *
     * @throws  NonReadableChannelException
     *          如果通道未打开就开始读取
     *
     * @throws  ClosedChannelException
     *          如果通道关闭
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
    public long read(ByteBuffer[] dsts, int offset, int length)
        throws IOException;

    /**
     * 将此通道中的字节序列读取到给定缓冲区数组中。
     *
     *
     * @param  dsts
     *          要传输字节的缓冲区数组
     *
     * @return 读取的字节数
     *
     * @throws  NonReadableChannelException
     *          如果通道未打开就开始读取
     *
     * @throws  ClosedChannelException
     *           如果通道关闭
     *
     * @throws  AsynchronousCloseException
     *           如果另一个线程关闭此通道读取操作正在进行时
     *
     * @throws  ClosedByInterruptException
     *          如果另一个线程中断当前线程读操作，同时通道被关闭
     *
     * @throws  IOException
     *           如果发生其他I/O错误
     */
    public long read(ByteBuffer[] dsts) throws IOException;

}
