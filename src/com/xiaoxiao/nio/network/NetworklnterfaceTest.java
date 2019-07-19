package nio.network;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetworklnterfaceTest {

    /**
     * 获取网络设备的名称、类型及运行状态
     */
    @Test
    public static void getNetworkInformationTest() {
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
                //获得网络接口的索引
                System.out.println("getIndex:"
                        + eachNetworkinterface.getIndex());
                //是否已经开启并运行
                System.out.println("isUp:"
                        + eachNetworkinterface.isUp());
                //是否为回调接口
                System.out.println("isLoopback:"
                        + eachNetworkinterface.isLoopback());
                System.out.println();
                System.out.println();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取当前网络接口中的MTU值，以太网的MTU为1500；MTU的值为-1时表示该网络接口被禁用
     */
    @Test
    public static void getNetworkMTUTest() {
        Enumeration<NetworkInterface> networkinterface = null;
        try {
            networkinterface = NetworkInterface.getNetworkInterfaces();

            while (networkinterface.hasMoreElements()) {
                NetworkInterface eachNetworkinterface = networkinterface
                        .nextElement();
                //获取网络没有被禁用的网络接口信息
                if (eachNetworkinterface.getMTU() != -1) {
                    //获取网络设备的名称，即网卡的名称，如eth0
                    System.out.println("getName:"
                            + eachNetworkinterface.getName());
                    // 获得网络设备显示名称,
                    System.out.println("getDisplayName:"
                            + eachNetworkinterface.getDisplayName());
                    //获得当前网络接口中MTU的大小，MTU为-1表示网络接口被禁用
                    System.out.println("getMTU:"
                            + eachNetworkinterface.getMTU());
                    System.out.println();
                    System.out.println();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    /**
     * 判断当前网络接口是否为虚拟网卡；
     */
    @Test
    public static void getVirtualTest() {
        Enumeration<NetworkInterface> networkinterface = null;
        try {
            //获取当前计算机中所有的网络接口；
            networkinterface = NetworkInterface.getNetworkInterfaces();
            //遍历所有的网络接口，没有下一个则退出while循环
            while (networkinterface.hasMoreElements()) {
                //获取一个网络接口
                NetworkInterface eachNetworkinterface = networkinterface
                        .nextElement();
                //判断当前网络设备是否为虚拟设备，true表示为虚拟设备，false代表是实体设备；
                if (eachNetworkinterface.isVirtual() == true) {
                    //获取网络设备的哈希值
                    System.out.println("hasCode:"
                            + eachNetworkinterface.hashCode());
                    //获取网络设备的名称，即网卡的名称，如eth0
                    System.out.println("getName:"
                            + eachNetworkinterface.getName());
                    // 获得网络设备显示名称,
                    System.out.println("getDisplayName:"
                            + eachNetworkinterface.getDisplayName());
                    //判断网络设备是否为虚拟设备
                    System.out.println("isVirtual:"
                            + eachNetworkinterface.isVirtual());
                    System.out.println("================");
                    System.out.println();
                }
            }
        } catch (
                SocketException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取网络设备的子接口设备，并打印子接口的信息
     */
    @Test
    public static void getSubInterfacesTest() {
        Enumeration<NetworkInterface> networkinterface = null;
        try {
            //获取当前计算机中所有的网络接口；
            networkinterface = NetworkInterface.getNetworkInterfaces();
            //遍历所有的网络接口，没有下一个则退出while循环
            while (networkinterface.hasMoreElements()) {
                //获取一个网络接口
                NetworkInterface eachNetworkinterface = networkinterface
                        .nextElement();
                //获取网络设备的哈希值
                System.out.println("hasCode:"
                        + eachNetworkinterface.hashCode());
                //获取网络设备的名称，即网卡的名称，如eth0
                System.out.println("getName:"
                        + eachNetworkinterface.getName());
                // 获得网络设备的显示名称,即驱动的名称，如：
                System.out.println("getDisplayName:"
                        + eachNetworkinterface.getDisplayName());
                //判断当前网络设备是否为虚拟网卡
                System.out.println("isVirtual:"
                        + eachNetworkinterface.isVirtual());
                //获取当前网络设备的父接口
                System.out.println("getParent:"
                        + eachNetworkinterface.getParent());
                System.out.println("getSubInterfaces:");
                System.out.println("=======");
                //获取网络设备的子接口信息
                Enumeration<NetworkInterface> networkInterfaceSub = eachNetworkinterface.getSubInterfaces();
                while (networkInterfaceSub.hasMoreElements()) {
                    //获取所有的子接口信息
                    NetworkInterface eachNetworkinterfaceSub = networkInterfaceSub.nextElement();

                    //获取子接口的网络设备的名称，即网卡的名称，如eth0
                    System.out.println("***getName:"
                            + eachNetworkinterfaceSub.getName());
                    // 获得子接口的显示名称,
                    System.out.println("***getDisplayName:"
                            + eachNetworkinterfaceSub.getDisplayName());
                    //判断子接口是否为虚拟网卡
                    System.out.println("***isVirtual:"
                            + eachNetworkinterfaceSub.isVirtual());
                    //获取子接口的父接口的哈希值，该值应该与网络设备的哈希值一致
                    System.out.println("***getParent hasCode:"
                            + eachNetworkinterfaceSub.getParent().hashCode());

                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取网络设备的MAC地址
     */
    @Test
    public static void getMACAddressTest() {
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
                System.out.println("getMACAddress: ");
                //获取网络设备的MAC地址，获取的地址是用十进制表示的，需要转换为十六进制，才能与MAC地址相匹配
                byte[] byteArray = eachNetworkinterface.getHardwareAddress();
                if (byteArray != null && byteArray.length != 0) {
                    for (int i = 0; i < byteArray.length; i++) {
                        System.out.print(byteArray[i] + ":");
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println();

            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }


    /**
     * 判断接口是否为点对点的通信
     */
    @Test
    public static void isPointToPointTest() {
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
                System.out.println("isPointToPoint:"
                        + eachNetworkinterface.isPointToPoint());
                System.out.println();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    /**
     * 判断接口是否支持广播
     */
    @Test
    public static void supportsMulticastTest() {
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
                //判断网络接口是否支持广播
                System.out.println("supportsMulticast:"
                        + eachNetworkinterface.supportsMulticast());
                System.out.println();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取网络设备的所有IP地址及详细信息(获取InetAddress)
     */
    @Test
    public static void getInetAddressTest() {
        Enumeration<NetworkInterface> networkinterface = null;
        try {
            //获取当前计算机中所有的网络接口；
            networkinterface = NetworkInterface.getNetworkInterfaces();
            //遍历所有的网络接口，没有下一个则退出while循环
            while (networkinterface.hasMoreElements()) {
                //获取一个网络接口
                NetworkInterface eachNetworkinterface = networkinterface
                        .nextElement();
                //当网络端口没有被禁用，则打印相关的网卡信息
                if (eachNetworkinterface.getMTU() != -1) {
                    //获取网络设备的名称，即网卡的名称，如wlan0
                    System.out.println("getName:"
                            + eachNetworkinterface.getName());
                    // 获得网络设备显示名称,即设备的实际显示名称，如：Broadcom 802.11n 网络适配器
                    System.out.println("getDisplayName:"
                            + eachNetworkinterface.getDisplayName());
                    System.out.println("==getInetAddress:");
                    //获取每一个网络设备的所有的IP地址信息，例如一个网卡有IPv4和IPv6地址；
                    Enumeration<InetAddress> enumInetAddress = eachNetworkinterface.getInetAddresses();
                    while (enumInetAddress.hasMoreElements()) {
                        InetAddress inetAddress = enumInetAddress.nextElement();
                        //获取此IP地址的主机名,如：W5GJYLERDMOLNP0.funshion.com
                        System.out.println("==getHostName:" + inetAddress.getHostName());
                        //返回IP 地址字符串（以文本表现形式）,即:192.168.1.5
                        System.out.println("==getHostAddress:" + inetAddress.getHostAddress());
                        System.out.println("-----------------------");
                    }
                    System.out.println();
                    System.out.println("===============================");

                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

}
