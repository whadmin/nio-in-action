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
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.Set;

/**
 * 支持网络socket通道
 *
 */

public interface NetworkChannel
    extends Channel
{
    /**
     * 将通道的套接字绑定到本地地址。
     *
     * @param   local
     *          绑定套接字的地址
     *
     * @return  当前Channel
     *
     * @throws  AlreadyBoundException
     *          如果套接字已绑定
     * @throws  UnsupportedAddressTypeException
     *          如果不支持给定地址的类型
     * @throws  ClosedChannelException
     *          如果通道被关闭
     * @throws  IOException
     *          如果发生其他I/O错误
     * @throws  SecurityException
     *          如果安装了安全管理器并且拒绝未指定的权限
     *
     * @see #getLocalAddress
     */
    NetworkChannel bind(SocketAddress local) throws IOException;

    /**
     * 返回此通道套接字绑定到的套接字地址
     *
     * @return 套接字绑定到的套接字地址，或@code null 如果通道的套接字未绑定
     *
     * @throws  ClosedChannelException
     *          如果通道被关闭
     * @throws  IOException
     *          如果发生IO异常
     */
    SocketAddress getLocalAddress() throws IOException;

    /**
     * 设置socket选项的值。
     *

     * @param   name
     *          选项的名称
     * @param   value
     *          选项的值
     *
     * @return  This channel
     *
     * @throws  UnsupportedOperationException
     *          如果此通道不支持socket选项
     * @throws  IllegalArgumentException
     *          如果该值不是此套接字选项的有效值
     * @throws  ClosedChannelException
     *          如果通道被关闭
     * @throws  IOException
     *          如果发生IO异常
     *
     * @see java.net.StandardSocketOptions
     */
    <T> NetworkChannel setOption(SocketOption<T> name, T value) throws IOException;

    /**
     * 返回socket选项的值。
     *
     * @param   name
     *          选项的名称
     *
     * @return  选项的值，如果没有设置返回null
     *
     * @throws  UnsupportedOperationException
     *          如果此通道不支持socket选项
     * @throws  ClosedChannelException
     *          如果通道被关闭
     * @throws  IOException
     *          如果发生IO异常
     *
     * @see java.net.StandardSocketOptions
     */
    <T> T getOption(SocketOption<T> name) throws IOException;

    /**
     * 返回此通道支持的一组套接字选项
     */
    Set<SocketOption<?>> supportedOptions();
}
