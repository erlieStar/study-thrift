package com.studycode.main.threadPoolServer;


import com.studycode.service.SumServiceImp;
import com.studycode.thrift.SumService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/*
 * 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求
 */

@Slf4j
public class Server {

    public static final int SERVER_PORT = 8090;

    public static void main(String[] args) {

        try {
            System.out.println("服务端开启");
            TProcessor tProcessor = new SumService.Processor<SumService.Iface>(new SumServiceImp());
            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
            TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(serverTransport);
            ttpsArgs.processor(tProcessor);
            ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TThreadPoolServer(ttpsArgs);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
