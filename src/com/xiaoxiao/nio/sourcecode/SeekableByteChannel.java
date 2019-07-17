/*
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
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
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 在字节通道中维护position （位置），以及允许position发生改变。

 */

public interface SeekableByteChannel
    extends ByteChannel
{
    /**
     * 从该通道当前位置开始读取字节写入字节缓冲区，并更新通道中position
     */
    @Override
    int read(ByteBuffer dst) throws IOException;

    /**
     * 从给定缓冲区向此通道写入一个字节序列，并更新通道中position
     *
     */
    @Override
    int write(ByteBuffer src) throws IOException;

    /**
     * 返回此通道的position。
     *
     * @return  返回此通道的position

     *
     * @throws  ClosedChannelException
     *          如果通道被关闭
     * @throws  IOException
     *          如果发生IO异常
     */
    long position() throws IOException;

    /**
     * 设置此通道的position。
     *
     *
     * @param  newPosition
     *         新position,
     *
     * @return  This channel
     *
     * @throws  ClosedChannelException
     *          如果通道被关闭
     * @throws  IllegalArgumentException
     *          If the new position is negative
     * @throws  IOException
     *          如果发生IO异常
     */
    SeekableByteChannel position(long newPosition) throws IOException;

    /**
     * 此通道所连接的实体的当前大小
     *
     * @return  当前大小（以字节为单位）
     *
     * @throws  ClosedChannelException
     *          如果通道被关闭
     * @throws  IOException
     *          如果发生IO异常
     */
    long size() throws IOException;

    /**
     * 将此通道连接到的实体截断为给定大小
     *
     * @param  size
     *         新的大小，非负字节计数
     *
     * @return  This channel
     *
     * @throws  NonWritableChannelException
     *          If this channel was not opened for writing
     * @throws  ClosedChannelException
     *          如果通道被关闭
     * @throws  IllegalArgumentException
     *          如果新尺寸为负
     * @throws  IOException
     *          如果发生IO异常
     */
    SeekableByteChannel truncate(long size) throws IOException;
}
