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

import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;

/**
 * 支持异步I/O操作的通道。
 */

public interface AsynchronousChannel
    extends Channel
{
    /**
     * 关闭通道
     *
     * 如果一个线程在同时实现AsynchronousChannel通道上执行未完成，调用close()，线程会收到AsynchronousCloseException
     *
     * 在实现AsynchronousChannel通道上关闭后，进一步尝试启I/O操作，会立即完成，并抛出 ClosedChannelException
     *
     * @throws  IOException
     *          如果发生I/O错误
     */
    @Override
    void close() throws IOException;
}
