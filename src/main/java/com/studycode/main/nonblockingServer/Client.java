package com.studycode.main.nonblockingServer;

import com.studycode.thrift.SumService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client {

    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;

    public static void main(String[] args) {

        TTransport transport = null;

        try {
            transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));
            // 协议要和服务端一致
            TProtocol protocol = new TCompactProtocol(transport);
            SumService.Iface client = new SumService.Client(protocol);
            transport.open();
            int sum = client.getSum(10 ,20);
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
