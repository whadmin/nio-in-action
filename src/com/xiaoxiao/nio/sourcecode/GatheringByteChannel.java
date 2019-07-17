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
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.*;


/**
 * 从给定缓冲区数组向此通道写入一个字节序列。
 *
 */

public interface GatheringByteChannel
    extends WritableByteChannel
{

    /**
     * 从给定缓冲区数组的子序列向此通道写入字节序列。
     *
     * @param  srcs
     *         写入通道字节缓冲区数组
     *
     * @param  offset
     *         第一个写入通道的缓冲区，在数组的位置
     *
     * @param  length
     *         从offset开始数组中多少个缓冲区写入通道
     *
     * @return  写入的字节数
     *
     * @throws  IndexOutOfBoundsException
     *          如果读取缓冲区字节大于字节数组总字节大小
     *
     * @throws  NonWritableChannelException
     *          如果通道未打开就开始写入
     *
     * @throws  ClosedChannelException
     *          如果通道关闭
     *
     * @throws  AsynchronousCloseException
     *          写入操作正在进行时，另一个线程关闭此通道
     *
     * @throws  ClosedByInterruptException
     *          如果另一个线程中断当前线程写操作，同时通道被关闭
     *
     * @throws  IOException
     *          如果发生其他I/O错误
     */
    public long write(ByteBuffer[] srcs, int offset, int length)
        throws IOException;


    /**
     * 从给定缓冲区数组向此通道写入一个字节序列。

     * @param  srcs
     *         写入通道字节缓冲区数组
     *
     * @return  写入的字节数
     *
     * @throws  NonWritableChannelException
     *          如果通道未打开就开始写入
     *
     * @throws  ClosedChannelException
     *          如果通道关闭
     *
     * @throws  AsynchronousCloseException
     *          写入操作正在进行时，另一个线程关闭此通道
     *
     * @throws  ClosedByInterruptException
     *          如果另一个线程中断当前线程写操作，同时通道被关闭
     *
     * @throws  IOException
     *          如果发生其他I/O错误
     */
    public long write(ByteBuffer[] srcs) throws IOException;

}
