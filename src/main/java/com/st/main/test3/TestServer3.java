package com.st.main.test3;


import com.st.service.HelloServiceImpl;
import com.st.thrift.Hello;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/*
 * 使用非阻塞式IO，服务端和客户端需要指定 TFramedTransport 数据传输的方式。
 */

@Slf4j
public class TestServer3 {

    public static final int SERVER_PORT = 8090;

    public static void main(String[] args) {

        try {
            log.info("服务端开启");
            TProcessor tprocessor = new Hello.Processor<Hello.Iface>(new HelloServiceImpl());
            TNonblockingServerSocket tnbServerTransport = new TNonblockingServerSocket(SERVER_PORT);
            TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbServerTransport);
            tnbArgs.processor(tprocessor);
            tnbArgs.transportFactory(new TFramedTransport.Factory());
            tnbArgs.protocolFactory(new TCompactProtocol.Factory());
            TServer server = new TNonblockingServer(tnbArgs);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
