package com.yjy.bitwist;

public class Bitwise {
    //不使用加减乘除做加法：位运算，分别算所有的非进位和以及进位，然后将二者相加，循环，一直加到进位为0
    //非进位和本质上就是异或运算，进位是与运算
    //相当于95+17=5+7+90+10=2（非进位和）+10（进位）+0（非进位和）+100（进位）=112+0=112
    public int encryptionCalculate(int dataA, int dataB) {
        while (dataB!=0){
            int c = (dataA&dataB) << 1;//进位：按位异或再左移一位
            dataA = dataA^dataB;//计算非进位和并赋值给a
            dataB = c;//把进位赋值给b继续进行，直到进位为0
        }
        return dataA;
    }
}
