package com.yjy.adapter;

public class VoltageAdapter extends Voltage20V implements IVoltage5V{
    @Override
    public int outPut5V() {
        int srcV = outPut220V();
        int dst = srcV/44;
        return dst;
    }
}
