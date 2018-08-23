package com.studycode.main.simpleServer;

import com.studycode.thrift.SumService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client {

    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;

    public static void main(String[] args) {

        TTransport transport = null;

        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            SumService.Iface client = new SumService.Client(protocol);
            transport.open();
            // 在这里，就像调用本地代码一样，其实实现逻辑在服务器
            int sum = client.getSum(10, 20);
            System.out.println(sum);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }
}
