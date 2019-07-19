package nio.network;

import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class InetAddressTest {

    /**
     * 获取网络设备的所有InetAddress对象（IP地址）的标准主机名、主机别名及其他信息
     */
    @Test
    public static void getInetAddressTest() {
        try {
            //获取当前计算机中所有的网络接口；
            Enumeration<NetworkInterface> networkinterface = NetworkInterface.getNetworkInterfaces();
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
                        //获取此IP地址的完全限定域名，如：W5GJYLERDMOLNP0.funshion.com
                        System.out.println("==getCanonicalHostName:" + inetAddress.getCanonicalHostName());
                        //获取此IP地址的主机名,如：W5GJYLERDMOLNP0.funshion.com
                        System.out.println("==getHostName:" + inetAddress.getHostName());
                        //返回IP 地址字符串（以文本表现形式）,即:192.168.1.5
                        System.out.println("==getHostAddress:" + inetAddress.getHostAddress());
                        //返回此InetAddress 对象的原始IP地址，返回值是byte[]数组。
                        System.out.print("==getAddress:");
                        byte[] addressByte = inetAddress.getAddress();
                        for (int i = 0; i < addressByte.length; i++) {
                            /**
                             * 通过getAddress方法获取的是字节数组，是用十进制显示，
                             * 如果是IPv4地址，则直接用十进制表示，存在负数，则需要转换一下；
                             *   如：-64.-88.1.5；-64需要转换一下，256-64=192；-88转换一下则为256-88=168；
                             * 如果是IPv6地址，则需要将十进制转换为十六进制显示，且每两个字节为一组用冒号隔开
                             * */
                            System.out.print(addressByte[i] + ".");
                        }
                        System.out.println();
                        System.out.println("--------------------------");
                    }
                    System.out.println();
                    System.out.println("================================");
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取本机第一个网络设备的信息和环回地址信息
     */
    @Test
    public static void getLocalInetAddressTest() {
        try {
            //获取本机网络接口中第一个设备的信息和IP地址数据
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("获取本地主机中第一个网络接口的信息：" + localHost);
            System.out.print("localhost.getAddress()的IP地址为: ");
            //获取第一个设备的IP地址信息，以字节的方式输出；
            byte[] localIPAddress = localHost.getAddress();
            for (int i = 0; i < localIPAddress.length; i++) {
                System.out.print(" " + localIPAddress[i] + " ");
            }
            System.out.println();
            System.out.println(" " + localHost.getClass().getName());
            System.out.println("--------------");
            System.out.print("inetAddress.getLoopbackAddress的地址为：");
            //获取本机网络设备列表中的环回地址的信息
            InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
            System.out.println(loopbackAddress);
            byte[] loopbackIPAddress = loopbackAddress.getAddress();
            System.out.print("localhost对应的IP地址为：");
            for (int i = 0; i < loopbackIPAddress.length; i++) {
                System.out.print(" " + loopbackIPAddress[i] + " ");
            }
            System.out.println();
            System.out.println(" " + localHost.getClass().getName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据主机名、域名、网卡名称等信息获得InetAddress对象（即IP地址的相关信息）
     */
    @Test
    public static void getByNameTest() throws UnknownHostException {
        //根据主机名来获取InetAddress对象
        InetAddress myAddress = InetAddress.getByName("W5GJYLERDMOLNP0");
        //根据域名来获取InetAddress对象
        InetAddress baiduAddress = InetAddress.getByName("www.baidu.com");
        //根据IP地址来获取InetAddress对象
        InetAddress ipStringAddress = InetAddress.getByName("192.168.1.5");
        //根据本地网卡名称来获取InetAddress对象
        InetAddress localhostAddress = InetAddress.getByName("localhost");
        //获取InetAddress对象的IP地址
        System.out.println(myAddress.getClass().getName() + " " + myAddress + " " + myAddress.getHostAddress());
        System.out.println(baiduAddress.getClass().getName() + " " + baiduAddress + " " + baiduAddress.getHostAddress());
        System.out.println(ipStringAddress.getClass().getName() + " " + ipStringAddress + " " + ipStringAddress.getHostAddress());
        System.out.println(localhostAddress.getClass().getName() + " " + localhostAddress + " " + localhostAddress.getHostAddress());
        //无法根据网络设备的显示名称来获取InetAddress
        InetAddress mydd = InetAddress.getByName("Npcap Loopback Adapter");
        System.out.println(mydd);

        // IP地址无效、域名无效，则会报错
//        InetAddress ipErrorAddress=InetAddress.getByName("192.168.0.345");
//        System.out.println(ipErrorAddress);
//        InetAddress domainErrorAddress=InetAddress.getByName("www.3867qeeqwewe.ewwe");
//        System.out.println(domainErrorAddress);


    }

    /**
     * 根据主机名、域名、网卡名称等信息获取所有的IP地址信息
     */
    @Test
    public static void getAllByNameTest() throws UnknownHostException {
        //根据主机名来获取InetAddress对象
        InetAddress[] myAddressArray = InetAddress.getAllByName("W5GJYLERDMOLNP0");
        //根据域名来获取InetAddress对象
        InetAddress[] baiduAddressArray = InetAddress.getAllByName("www.baidu.com");
        //根据IP地址来获取InetAddress对象
        InetAddress[] ipStringAddressArray = InetAddress.getAllByName("192.168.1.7");
        //在windows中通过ipconfig可以看到所有的IP信息
        for (int i = 0; i < myAddressArray.length; i++) {
            InetAddress myAddress = myAddressArray[i];
            System.out.println("本机网卡的IP地址信息：" + myAddress.getClass().getName() + " " + myAddress.getHostAddress());
        }
        System.out.println("=================");



        for (int i = 0; i < baiduAddressArray.length; i++) {
            InetAddress baiduAddress = baiduAddressArray[i];
            System.out.println("www.baidu.com的IP地址信息：" + baiduAddress.getClass().getName() + " " + baiduAddress.getHostAddress());
        }
        System.out.println("=================");

        for (int i = 0; i < ipStringAddressArray.length; i++) {
            InetAddress ipStringAddress = ipStringAddressArray[i];
            System.out.println(ipStringAddress.getClass().getName() + " " + ipStringAddress.getHostAddress());
        }
        System.out.println("=================");

    }


    /**
     * 根据已知的IP地址获取InetAddress对象，
     */
    @Test
    public static void getByAddressTest() throws UnknownHostException {
        //新建一个字节数组，将已知的IP地址的每一个字节转换为int；
        byte[] byteArray = new byte[]{-64, -88, 1, 5};
        //根据已知的IP地址获取InetAddress对象
        InetAddress myAddress = InetAddress.getByAddress(byteArray);
        //获取myAddress类的名称，可以通过类的名称识别是IPv4还是IPv6地址
        System.out.println("myAddress.getClass().getName()：" + myAddress.getClass().getName());
        //获取myAddress的主机地址信息，也就是IP地址；
        System.out.println("myAddress.getHostAddress()：" + myAddress.getHostAddress());
        //获取不到Name信息时，则显示IP地址信息；
        System.out.println("myAddress.getHostName()：" + myAddress.getHostName());

    }

    /**
     * 根据提供的主机名和IP 地址创建InetAddress，并不对host的有效性进行验证。
     * 主机中169.254.93.147对应的主机别名为W5GJYLERDMOLNP0.funshion.com
     */
    @Test
    public static void getByHostAndAddressTest() throws UnknownHostException {
        byte[] byteArray = new byte[]{-87, -2, 93, -109};
        //根据提供的主机别名和IP 地址创建InetAddress ，并不对host的有效性进行验证。
        InetAddress myAddress = InetAddress.getByAddress("xiao", byteArray);

        System.out.println("myAddress.getClass().getName()：" + myAddress.getClass().getName());
        System.out.println("myAddress.getHostAddress()：" + myAddress.getHostAddress());
        System.out.println("myAddress.getHostName()：" + myAddress.getHostName());

    }

    /**
     * 根据主机名、域名、网卡名称等信息获得IP地址信息
     */
    @Test
    public static void getHostNameTest() throws UnknownHostException {
        //使用getLocalHost()创建InetAddress
        // getCanonicalHostName()是获取的规范的主机名，如： W5GJYLERDMOLNP0.funshion.com
        // getHostName()是获取的主机名，即W5GJYLERDMOLNP0
        InetAddress address1 = InetAddress.getLocalHost();
        System.out.println("Address1 CanonicalHostName: " + address1.getCanonicalHostName());
        System.out.println("Address1 HostName: " + address1.getHostName());
        System.out.println("=================");
        //使用域名创建InetAddress
        InetAddress address2 = InetAddress.getByName("www.ibm.com");
        System.out.println("Address2 CanonicalHostName: " + address2.getCanonicalHostName());
        System.out.println("Address2 HostName: " + address2.getHostName());
        System.out.println("=================");
        //使用IP地址创建InetAddress
        InetAddress address3 = InetAddress.getByName("192.168.1.6");
        System.out.println("Address3 CanonicalHostName: " + address3.getCanonicalHostName());
        System.out.println("Address3 HostName: " + address3.getHostName());
        System.out.println("=================");
    }


}

