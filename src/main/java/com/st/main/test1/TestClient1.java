package com.st.main.test1;

import com.st.thrift.Hello;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class TestClient1 {

    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;

    public static void main(String[] args) {

        TTransport transport = null;

        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            Hello.Iface client = new Hello.Client(protocol);
            transport.open();
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