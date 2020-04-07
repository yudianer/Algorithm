package personal.leetcode;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Java 中位移操作>><<有符号；>>>无符号（没有<<<）
 * 位运算：byte/short/char都是按照int进行的，先转换成int在进行操作。
 * 0xff>>7：1，0xff=00000000000000000000000011111111
 * (byte)0xff>>7: -1;   (byte)0xff=11111111=-1, 0xff>int:11111111 > 11111…..111111111111111(32bit)
 */
public class BitOperation {
    public static void main(String[] args) {
        System.out.println(((byte)(0xff>>7)));
    }
    //todo 191：number of 1 bit，求 2进制中1的个数
    @Test
    public int hammingWeight(int n) {
        int count =0;
        while(n!=0){
            count ++;
            n=n&(n-1);
        }
        return count;
    }
}
