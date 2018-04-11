package com.st.service;

import org.apache.thrift.TException;
import com.st.thrift.Hello;

public class HelloServiceImpl implements Hello.Iface {

    @Override
    public String helloString(String para) throws TException {
        return "result: " + para;
    }
}
