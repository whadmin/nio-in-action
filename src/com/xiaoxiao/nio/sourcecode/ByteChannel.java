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

import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;


/**
 * 可以读写字节的通道。这个接口简单地统一了ReadableByteChannel，WritableByteChannel
 */

public interface ByteChannel
    extends ReadableByteChannel, WritableByteChannel
{

}
