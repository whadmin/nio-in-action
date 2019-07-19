package nio.network;

import org.testng.annotations.Test;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * ͨ��ʹ��InterfaceAddresses���еķ�������ȡ������ӿڶ�Ӧ��IP��ַ����������͹㲥��ַ�������Ϣ��
 *
 * @author xiao
 **/

public class InterfaceAddressTest {

    /**
     * ��ȡ����ӿ��ж�Ӧ������IP��ַ���㲥��ַ���������Ϣ��
     */
    @Test
    public void getInterfaceAddressesTest() throws SocketException {
        //��ȡ���е�����ӿ��豸
        Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
        while (networkInterface.hasMoreElements()) {
            NetworkInterface eachNetworkInterface = networkInterface.nextElement();
            //��ȡ����ӿ��豸������
            System.out.println("getName��ȡ�����豸�����ƣ� " + eachNetworkInterface.getName());
            //��ȡ����ӿ��豸����ʾ����
            System.out.println("getDisplayName��ȡ�����豸����ʾ���ƣ� " + eachNetworkInterface.getDisplayName());
            //��ȡ����ӿ��豸�Ľӿڵ�ַ��Ϣ��Ҳ��������ӿڵ�����IP��ַ
            List<InterfaceAddress> addressList = eachNetworkInterface.getInterfaceAddresses();
            for (int i = 0; i < addressList.size(); i++) {
                //����ȡ��������ӿڵ�IP��ַ��Ϣ
                InterfaceAddress eachAddress = addressList.get(i);
                //��ȡ�������IP��ַ����192.168.1.5
                InetAddress inetAddress = eachAddress.getAddress();
                if (inetAddress != null) {
                    System.out.println("eachAddress.getAddress(): " + inetAddress.getHostAddress());
                }
                //��ȡ������Ĺ㲥��ַ����192.168.1.255
                inetAddress = eachAddress.getBroadcast();
                if (inetAddress != null) {
                    System.out.println("eachAddress.getBroadcast(): " + inetAddress.getHostAddress());
                }
                //��ȡ������������λ������24����IP��ַ��ǰ24λΪ����ţ���8λΪ������
                System.out.println("getNetworkPrefixLength: " + eachAddress.getNetworkPrefixLength());
                System.out.println();
            }
        }
    }
}
