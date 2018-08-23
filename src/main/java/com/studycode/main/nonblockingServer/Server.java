package com.studycode.main.nonblockingServer;


import com.studycode.service.SumServiceImp;
import com.studycode.thrift.SumService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

/*
 * 使用非阻塞式IO，服务端和客户端需要指定 TFramedTransport 数据传输的方式。
 */

@Slf4j
public class Server {

    public static final int SERVER_PORT = 8090;

    public static void main(String[] args) {

        try {
            log.info("服务端开启");
            TProcessor tProcessor = new SumService.Processor<SumService.Iface>(new SumServiceImp());
            TNonblockingServerSocket tnbServerTransport = new TNonblockingServerSocket(SERVER_PORT);
            TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbServerTransport);
            tnbArgs.processor(tProcessor);
            tnbArgs.transportFactory(new TFramedTransport.Factory());
            tnbArgs.protocolFactory(new TCompactProtocol.Factory());
            TServer server = new TNonblockingServer(tnbArgs);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
