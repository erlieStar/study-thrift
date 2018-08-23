package com.studycode.service;

import com.studycode.thrift.SumService;
import org.apache.thrift.TException;

public class SumServiceImp implements SumService.Iface {

    @Override
    public int getSum(int num1, int num2) throws TException {
        return num1 + num2;
    }
}
