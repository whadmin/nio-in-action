package nio.network;

import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetworkInterfaceStaticMethodTest {


    /**
     * 根据索引获取指定的NetworkInterface对象。
     */
    @Test
    public static void getByIndexTest() {
        Enumeration<NetworkInterface> networkinterface = null;
        try {
            //获取当前计算机中所有的网络接口；
            networkinterface = NetworkInterface.getNetworkInterfaces();
            //遍历所有的网络接口，没有下一个则退出while循环
            while (networkinterface.hasMoreElements()) {
                //获取一个网络接口
                NetworkInterface eachNetworkinterface = networkinterface
                        .nextElement();
                //获取网络设备的名称，即网卡的名称，如eth0
                System.out.println("getName:"
                        + eachNetworkinterface.getName());
                // 获得网络设备显示名称,
                System.out.println("getDisplayName:"
                        + eachNetworkinterface.getDisplayName());
                //判断网络接口是不是点对点的设备；
                System.out.println("getIndex:"
                        + eachNetworkinterface.getIndex());
                System.out.println();
            }

            NetworkInterface newNetworkInterface = NetworkInterface.getByIndex(1);
            System.out.println("display name with indexNumber is 1: " + newNetworkInterface.getName());
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }


    /**
     * 根据网络设备名称获取NetworkInterface 对象。
     */
    @Test
    public static void getByNameTest() throws SocketException {
        NetworkInterface newNetworkInterface = NetworkInterface.getByName("lo");
        System.out.println("通过lo获取的NetworkInterface的名称为: " + newNetworkInterface.getName());
        System.out.println("名称为lo的设备的显示名称为: " + newNetworkInterface.getDisplayName());

    }

    /**
     * 根据InetAddress对象来获取NetworkInterface 对象。
     */
    @Test
    public static void getByInetAddressTest() throws SocketException, UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName("localhost");
        NetworkInterface byInetAddressNetworkInterface = NetworkInterface.getByInetAddress(inetAddress);
        System.out.println("ip地址名称为localhost的设备名称: " + byInetAddressNetworkInterface.getName());
        System.out.println("IP地址名称为localhost的设备的显示名称：" + byInetAddressNetworkInterface.getDisplayName());
        System.out.println();
        InetAddress ipStringInetAddress = InetAddress.getByName("192.168.1.5");
        NetworkInterface ipStringNetworkInterface = NetworkInterface.getByInetAddress(ipStringInetAddress);
        System.out.println("ip地址为192.168.1.5的设备名称: " + ipStringNetworkInterface.getName());
        System.out.println("IP地址为192.168.1.5的设备的显示名称：" + ipStringNetworkInterface.getDisplayName());
    }

}
