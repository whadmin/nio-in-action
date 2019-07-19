package nio.network;

import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetworkInterfaceStaticMethodTest {


    /**
     * ����������ȡָ����NetworkInterface����
     */
    @Test
    public static void getByIndexTest() {
        Enumeration<NetworkInterface> networkinterface = null;
        try {
            //��ȡ��ǰ����������е�����ӿڣ�
            networkinterface = NetworkInterface.getNetworkInterfaces();
            //�������е�����ӿڣ�û����һ�����˳�whileѭ��
            while (networkinterface.hasMoreElements()) {
                //��ȡһ������ӿ�
                NetworkInterface eachNetworkinterface = networkinterface
                        .nextElement();
                //��ȡ�����豸�����ƣ������������ƣ���eth0
                System.out.println("getName:"
                        + eachNetworkinterface.getName());
                // ��������豸��ʾ����,
                System.out.println("getDisplayName:"
                        + eachNetworkinterface.getDisplayName());
                //�ж�����ӿ��ǲ��ǵ�Ե���豸��
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
     * ���������豸���ƻ�ȡNetworkInterface ����
     */
    @Test
    public static void getByNameTest() throws SocketException {
        NetworkInterface newNetworkInterface = NetworkInterface.getByName("lo");
        System.out.println("ͨ��lo��ȡ��NetworkInterface������Ϊ: " + newNetworkInterface.getName());
        System.out.println("����Ϊlo���豸����ʾ����Ϊ: " + newNetworkInterface.getDisplayName());

    }

    /**
     * ����InetAddress��������ȡNetworkInterface ����
     */
    @Test
    public static void getByInetAddressTest() throws SocketException, UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName("localhost");
        NetworkInterface byInetAddressNetworkInterface = NetworkInterface.getByInetAddress(inetAddress);
        System.out.println("ip��ַ����Ϊlocalhost���豸����: " + byInetAddressNetworkInterface.getName());
        System.out.println("IP��ַ����Ϊlocalhost���豸����ʾ���ƣ�" + byInetAddressNetworkInterface.getDisplayName());
        System.out.println();
        InetAddress ipStringInetAddress = InetAddress.getByName("192.168.1.5");
        NetworkInterface ipStringNetworkInterface = NetworkInterface.getByInetAddress(ipStringInetAddress);
        System.out.println("ip��ַΪ192.168.1.5���豸����: " + ipStringNetworkInterface.getName());
        System.out.println("IP��ַΪ192.168.1.5���豸����ʾ���ƣ�" + ipStringNetworkInterface.getDisplayName());
    }

}
