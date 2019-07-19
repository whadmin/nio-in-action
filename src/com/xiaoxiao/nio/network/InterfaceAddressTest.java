package nio.network;

import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * 通过使用InterfaceAddresses类中的方法可以取得网络接口对应的IP地址、子网掩码和广播地址等相关信息。
 *
 * @author xiao
 **/

public class InterfaceAddressTest {

    /**
     * 获取网络接口中对应的网络IP地址、广播地址、网络号信息；
     */
    @Test
    public void getInterfaceAddressesTest() throws SocketException {
        //获取所有的网络接口设备
        Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
        while (networkInterface.hasMoreElements()) {
            NetworkInterface eachNetworkInterface = networkInterface.nextElement();
            //获取网络接口设备的名称
            System.out.println("getName获取网络设备的名称： " + eachNetworkInterface.getName());
            //获取网络接口设备的显示名称
            System.out.println("getDisplayName获取网络设备的显示名称： " + eachNetworkInterface.getDisplayName());
            //获取网络接口设备的接口地址信息，也就是网络接口的所有IP地址
            List<InterfaceAddress> addressList = eachNetworkInterface.getInterfaceAddresses();
            for (int i = 0; i < addressList.size(); i++) {
                //依次取出该网络接口的IP地址信息
                InterfaceAddress eachAddress = addressList.get(i);
                //获取该网络的IP地址，即192.168.1.5
                InetAddress inetAddress = eachAddress.getAddress();
                if (inetAddress != null) {
                    System.out.println("eachAddress.getAddress(): " + inetAddress.getHostAddress());
                }
                //获取该网络的广播地址，即192.168.1.255
                inetAddress = eachAddress.getBroadcast();
                if (inetAddress != null) {
                    System.out.println("eachAddress.getBroadcast(): " + inetAddress.getHostAddress());
                }
                //获取该网络的网络号位数，即24代表IP地址的前24位为网络号，后8位为主机号
                System.out.println("getNetworkPrefixLength: " + eachAddress.getNetworkPrefixLength());
                System.out.println();
            }
        }
    }
}
