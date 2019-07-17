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
import java.net.*;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.MembershipKey;
import java.nio.channels.NetworkChannel;

/**
 * MulticastChannel 接口的主要作用是使通道支持Internet Protocol ( IP ）多播。IP 多播就
 * 是将多个主机地址进行打包，形成一个组（ group ），然后将IP 报文向这个组进行
 * 发送，也就相当于同时向多个主机传输数据。
 *
 *
 * <p> <b>Usage Example:</b>
 * <pre>
 *     // join multicast group on this interface, and also use this
 *     // interface for outgoing multicast datagrams
 *     NetworkInterface ni = NetworkInterface.getByName("hme0");
 *
 *     DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET)
 *         .setOption(StandardSocketOptions.SO_REUSEADDR, true)
 *         .bind(new InetSocketAddress(5000))
 *         .setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
 *
 *     InetAddress group = InetAddress.getByName("225.4.5.6");
 *
 *     MembershipKey key = dc.join(group, ni);
 * </pre>
 *
 * @since 1.7
 */

public interface MulticastChannel
    extends NetworkChannel
{
    /**
     * 关闭此通道

     *
     * @throws  IOException
     *          如果发生I/O错误
     */
    @Override void close() throws IOException;

    /**
     * 加入多播组以开始接收发送到该组的所有数据报，返回MembershipKey
     *
     * @param   group
     *          要加入的多播地址
     * @param   interf
     *          要加入组的网络接口
     * @return  MembershipKey
     *
     * @throws  IllegalArgumentException
     *          If the group parameter is not a {@link InetAddress#isMulticastAddress
     *          multicast} address, or the group parameter is an address type
     *          that is not supported by this channel
     * @throws  IllegalStateException
     *          如果通道已经具有特定于源的界面上的组
     * @throws  UnsupportedOperationException
     *          如果通道的套接字不是Internet协议套接字
     * @throws  ClosedChannelException
     *          如果该通道关闭
     * @throws  IOException
     *          如果发生I/O错误
     * @throws  SecurityException
     *          If a security manager is set, and its
     *          {@link SecurityManager#checkMulticast(InetAddress) checkMulticast}
     *          method denies access to the multiast group
     */
    MembershipKey join(InetAddress group, NetworkInterface interf)
        throws IOException;

    /**
     * 加入多播组以开始接收发送到该组的数据报来自给定的源地址。
     *
     *
     * @param   group
     *          要加入的多播地址
     * @param   interf
     *          要加入组的网络接口
     * @param   source
     *          来源地址
     *
     * @return  MembershipKey
     *
     * @throws  IllegalStateException
     *          如果通道当前是给定接口上接收所有数据报的组的成员
     * @throws  UnsupportedOperationException
     *          如果通道的套接字不是Internet协议套接字或不支持源筛选
     * @throws  ClosedChannelException
     *          如果该通道关闭
     * @throws  IOException
     *          如果发生I/O错误
     * @throws  SecurityException
     *          If a security manager is set, and its
     *          {@link SecurityManager#checkMulticast(InetAddress) checkMulticast}
     *          method denies access to the multiast group
     */
    MembershipKey join(InetAddress group, NetworkInterface interf, InetAddress source)
        throws IOException;
}
