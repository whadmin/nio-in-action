package nio.network;

import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class InetAddressTest {

    /**
     * ��ȡ�����豸������InetAddress����IP��ַ���ı�׼������������������������Ϣ
     */
    @Test
    public static void getInetAddressTest() {
        try {
            //��ȡ��ǰ����������е�����ӿڣ�
            Enumeration<NetworkInterface> networkinterface = NetworkInterface.getNetworkInterfaces();
            //�������е�����ӿڣ�û����һ�����˳�whileѭ��
            while (networkinterface.hasMoreElements()) {
                //��ȡһ������ӿ�
                NetworkInterface eachNetworkinterface = networkinterface
                        .nextElement();
                //������˿�û�б����ã����ӡ��ص�������Ϣ
                if (eachNetworkinterface.getMTU() != -1) {
                    //��ȡ�����豸�����ƣ������������ƣ���wlan0
                    System.out.println("getName:"
                            + eachNetworkinterface.getName());
                    // ��������豸��ʾ����,���豸��ʵ����ʾ���ƣ��磺Broadcom 802.11n ����������
                    System.out.println("getDisplayName:"
                            + eachNetworkinterface.getDisplayName());
                    System.out.println("==getInetAddress:");
                    //��ȡÿһ�������豸�����е�IP��ַ��Ϣ������һ��������IPv4��IPv6��ַ��
                    Enumeration<InetAddress> enumInetAddress = eachNetworkinterface.getInetAddresses();
                    while (enumInetAddress.hasMoreElements()) {
                        InetAddress inetAddress = enumInetAddress.nextElement();
                        //��ȡ��IP��ַ����ȫ�޶��������磺W5GJYLERDMOLNP0.funshion.com
                        System.out.println("==getCanonicalHostName:" + inetAddress.getCanonicalHostName());
                        //��ȡ��IP��ַ��������,�磺W5GJYLERDMOLNP0.funshion.com
                        System.out.println("==getHostName:" + inetAddress.getHostName());
                        //����IP ��ַ�ַ��������ı�������ʽ��,��:192.168.1.5
                        System.out.println("==getHostAddress:" + inetAddress.getHostAddress());
                        //���ش�InetAddress �����ԭʼIP��ַ������ֵ��byte[]���顣
                        System.out.print("==getAddress:");
                        byte[] addressByte = inetAddress.getAddress();
                        for (int i = 0; i < addressByte.length; i++) {
                            /**
                             * ͨ��getAddress������ȡ�����ֽ����飬����ʮ������ʾ��
                             * �����IPv4��ַ����ֱ����ʮ���Ʊ�ʾ�����ڸ���������Ҫת��һ�£�
                             *   �磺-64.-88.1.5��-64��Ҫת��һ�£�256-64=192��-88ת��һ����Ϊ256-88=168��
                             * �����IPv6��ַ������Ҫ��ʮ����ת��Ϊʮ��������ʾ����ÿ�����ֽ�Ϊһ����ð�Ÿ���
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
     * ��ȡ������һ�������豸����Ϣ�ͻ��ص�ַ��Ϣ
     */
    @Test
    public static void getLocalInetAddressTest() {
        try {
            //��ȡ��������ӿ��е�һ���豸����Ϣ��IP��ַ����
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("��ȡ���������е�һ������ӿڵ���Ϣ��" + localHost);
            System.out.print("localhost.getAddress()��IP��ַΪ: ");
            //��ȡ��һ���豸��IP��ַ��Ϣ�����ֽڵķ�ʽ�����
            byte[] localIPAddress = localHost.getAddress();
            for (int i = 0; i < localIPAddress.length; i++) {
                System.out.print(" " + localIPAddress[i] + " ");
            }
            System.out.println();
            System.out.println(" " + localHost.getClass().getName());
            System.out.println("--------------");
            System.out.print("inetAddress.getLoopbackAddress�ĵ�ַΪ��");
            //��ȡ���������豸�б��еĻ��ص�ַ����Ϣ
            InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
            System.out.println(loopbackAddress);
            byte[] loopbackIPAddress = loopbackAddress.getAddress();
            System.out.print("localhost��Ӧ��IP��ַΪ��");
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
     * �������������������������Ƶ���Ϣ���InetAddress���󣨼�IP��ַ�������Ϣ��
     */
    @Test
    public static void getByNameTest() throws UnknownHostException {
        //��������������ȡInetAddress����
        InetAddress myAddress = InetAddress.getByName("W5GJYLERDMOLNP0");
        //������������ȡInetAddress����
        InetAddress baiduAddress = InetAddress.getByName("www.baidu.com");
        //����IP��ַ����ȡInetAddress����
        InetAddress ipStringAddress = InetAddress.getByName("192.168.1.5");
        //���ݱ���������������ȡInetAddress����
        InetAddress localhostAddress = InetAddress.getByName("localhost");
        //��ȡInetAddress�����IP��ַ
        System.out.println(myAddress.getClass().getName() + " " + myAddress + " " + myAddress.getHostAddress());
        System.out.println(baiduAddress.getClass().getName() + " " + baiduAddress + " " + baiduAddress.getHostAddress());
        System.out.println(ipStringAddress.getClass().getName() + " " + ipStringAddress + " " + ipStringAddress.getHostAddress());
        System.out.println(localhostAddress.getClass().getName() + " " + localhostAddress + " " + localhostAddress.getHostAddress());
        //�޷����������豸����ʾ��������ȡInetAddress
        InetAddress mydd = InetAddress.getByName("Npcap Loopback Adapter");
        System.out.println(mydd);

        // IP��ַ��Ч��������Ч����ᱨ��
//        InetAddress ipErrorAddress=InetAddress.getByName("192.168.0.345");
//        System.out.println(ipErrorAddress);
//        InetAddress domainErrorAddress=InetAddress.getByName("www.3867qeeqwewe.ewwe");
//        System.out.println(domainErrorAddress);


    }

    /**
     * �������������������������Ƶ���Ϣ��ȡ���е�IP��ַ��Ϣ
     */
    @Test
    public static void getAllByNameTest() throws UnknownHostException {
        //��������������ȡInetAddress����
        InetAddress[] myAddressArray = InetAddress.getAllByName("W5GJYLERDMOLNP0");
        //������������ȡInetAddress����
        InetAddress[] baiduAddressArray = InetAddress.getAllByName("www.baidu.com");
        //����IP��ַ����ȡInetAddress����
        InetAddress[] ipStringAddressArray = InetAddress.getAllByName("192.168.1.7");
        //��windows��ͨ��ipconfig���Կ������е�IP��Ϣ
        for (int i = 0; i < myAddressArray.length; i++) {
            InetAddress myAddress = myAddressArray[i];
            System.out.println("����������IP��ַ��Ϣ��" + myAddress.getClass().getName() + " " + myAddress.getHostAddress());
        }
        System.out.println("=================");



        for (int i = 0; i < baiduAddressArray.length; i++) {
            InetAddress baiduAddress = baiduAddressArray[i];
            System.out.println("www.baidu.com��IP��ַ��Ϣ��" + baiduAddress.getClass().getName() + " " + baiduAddress.getHostAddress());
        }
        System.out.println("=================");

        for (int i = 0; i < ipStringAddressArray.length; i++) {
            InetAddress ipStringAddress = ipStringAddressArray[i];
            System.out.println(ipStringAddress.getClass().getName() + " " + ipStringAddress.getHostAddress());
        }
        System.out.println("=================");

    }


    /**
     * ������֪��IP��ַ��ȡInetAddress����
     */
    @Test
    public static void getByAddressTest() throws UnknownHostException {
        //�½�һ���ֽ����飬����֪��IP��ַ��ÿһ���ֽ�ת��Ϊint��
        byte[] byteArray = new byte[]{-64, -88, 1, 5};
        //������֪��IP��ַ��ȡInetAddress����
        InetAddress myAddress = InetAddress.getByAddress(byteArray);
        //��ȡmyAddress������ƣ�����ͨ���������ʶ����IPv4����IPv6��ַ
        System.out.println("myAddress.getClass().getName()��" + myAddress.getClass().getName());
        //��ȡmyAddress��������ַ��Ϣ��Ҳ����IP��ַ��
        System.out.println("myAddress.getHostAddress()��" + myAddress.getHostAddress());
        //��ȡ����Name��Ϣʱ������ʾIP��ַ��Ϣ��
        System.out.println("myAddress.getHostName()��" + myAddress.getHostName());

    }

    /**
     * �����ṩ����������IP ��ַ����InetAddress��������host����Ч�Խ�����֤��
     * ������169.254.93.147��Ӧ����������ΪW5GJYLERDMOLNP0.funshion.com
     */
    @Test
    public static void getByHostAndAddressTest() throws UnknownHostException {
        byte[] byteArray = new byte[]{-87, -2, 93, -109};
        //�����ṩ������������IP ��ַ����InetAddress ��������host����Ч�Խ�����֤��
        InetAddress myAddress = InetAddress.getByAddress("xiao", byteArray);

        System.out.println("myAddress.getClass().getName()��" + myAddress.getClass().getName());
        System.out.println("myAddress.getHostAddress()��" + myAddress.getHostAddress());
        System.out.println("myAddress.getHostName()��" + myAddress.getHostName());

    }

    /**
     * �������������������������Ƶ���Ϣ���IP��ַ��Ϣ
     */
    @Test
    public static void getHostNameTest() throws UnknownHostException {
        //ʹ��getLocalHost()����InetAddress
        // getCanonicalHostName()�ǻ�ȡ�Ĺ淶�����������磺 W5GJYLERDMOLNP0.funshion.com
        // getHostName()�ǻ�ȡ������������W5GJYLERDMOLNP0
        InetAddress address1 = InetAddress.getLocalHost();
        System.out.println("Address1 CanonicalHostName: " + address1.getCanonicalHostName());
        System.out.println("Address1 HostName: " + address1.getHostName());
        System.out.println("=================");
        //ʹ����������InetAddress
        InetAddress address2 = InetAddress.getByName("www.ibm.com");
        System.out.println("Address2 CanonicalHostName: " + address2.getCanonicalHostName());
        System.out.println("Address2 HostName: " + address2.getHostName());
        System.out.println("=================");
        //ʹ��IP��ַ����InetAddress
        InetAddress address3 = InetAddress.getByName("192.168.1.6");
        System.out.println("Address3 CanonicalHostName: " + address3.getCanonicalHostName());
        System.out.println("Address3 HostName: " + address3.getHostName());
        System.out.println("=================");
    }


}

