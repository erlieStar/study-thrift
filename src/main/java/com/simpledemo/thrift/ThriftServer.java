package com.simpledemo.thrift;

import com.studycode.service.SumServiceImpl;
import com.studycode.thrift.SumService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.*;

import java.net.InetSocketAddress;

@Data
@Slf4j
public class ThriftServer {

    private String host;
    private int port;
    private boolean isSync;
    private int maxWorkThreads = 20;
    // 设置消息的最大大小为100M，防止消息太大造成OOM
    public final long maxReadBufferBytes = 1024 * 1024 * 100L;

    public ThriftServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {

        TServerTransport serverTransport = null;
        TNonblockingServerTransport nbServerTransport = null;
        TThreadPoolServer.Args serverArgs = null;
        TThreadedSelectorServer.Args tsServerArgs = null;
        TServer server = null;
        String serverInfo = "";

        try {

            TProtocolFactory pf = new TCompactProtocol.Factory();

            InetSocketAddress addr = new InetSocketAddress(host, port);

            if (isSync) {

                serverTransport = new TServerSocket(addr);
                serverArgs = new TThreadPoolServer.Args(serverTransport);

                serverArgs.transportFactory(new TFramedTransport.Factory());
                serverArgs.protocolFactory(pf);
                // 这里假如接口有多个实现类时，可以指定最终的实现类，如可以更改SumServiceImpl为其他的类
                serverArgs.processorFactory(new TProcessorFactory(new SumService.Processor<SumService.Iface>(new SumServiceImpl())));
                serverArgs.maxWorkerThreads(maxWorkThreads);

                server = new TThreadPoolServer(serverArgs);

                serverInfo = "syncServer";
            } else {
                nbServerTransport = new TNonblockingServerSocket(addr);
                tsServerArgs = new TThreadedSelectorServer.Args(nbServerTransport);

                tsServerArgs.transportFactory(new TFramedTransport.Factory());
                tsServerArgs.protocolFactory(pf);
                tsServerArgs.processorFactory(new TProcessorFactory(new SumService.Processor<SumService.Iface>(new SumServiceImpl())));
                tsServerArgs.workerThreads(maxWorkThreads);
                tsServerArgs.maxReadBufferBytes = maxReadBufferBytes;

                server = new TNonblockingServer(tsServerArgs);
                serverInfo = "asyncServer";
            }

            if (null != server) {

                log.info("started {} on port {} ", serverInfo, port);
                server.serve();
            }

        } catch (TTransportException e) {
            log.error("server error", e);
        }
    }
}
