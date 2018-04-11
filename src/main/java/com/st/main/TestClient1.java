package com.st.main;

import com.st.thrift.Hello;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class TestClient1 {

    public static void main(String[] args) {

        TTransport transport = null;

        try {
            transport = new TSocket("127.0.0.1", 9898);
            // 协议要和服务端一致
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            Hello.Iface client = new Hello.Client(protocol);
            String result = client.helloString("hello word");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }
}
