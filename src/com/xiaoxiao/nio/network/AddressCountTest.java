package nio.network;

import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * 计算IPv4地址和IPv6地址的总数
 *
 * @author xiao
 * */
public class AddressCountTest {

    /**
     * IPv4地址为32位，每8位为一组（即一个字节），分为4组，每一个字节用十进制来表示；IPv4地址用"."隔开
     * 获取IPv4地址的个数
     * */
    @Test
    public static void getIPv4AddressCount(){
        double getValue = Math .pow(2 , 32) ;
        BigDecimal bigDecimal = new BigDecimal ("" + getValue) ;
        System.out.println("IPv4地址的个数： "+bigDecimal.toString()) ;

    }

    /**
     * IPv6地址由128位组成，每16位为一组，分为8组，每个字节用十六进制表示；
     * 每组之间用冒号隔开，格式如：fe80::480e:3128:a8ff:a54e/20
     *
     * 获取IPv6地址的个数
     * */
    @Test
    public static void getIPv6AddressCount(){
        double getValue = Math .pow(2 , 128) ;
        BigDecimal bigDecimal = new BigDecimal ("" + getValue) ;
        System.out.println("IPv6地址的个数: "+bigDecimal.toString());
    }

}
