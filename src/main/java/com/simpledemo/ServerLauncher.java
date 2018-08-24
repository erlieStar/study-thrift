package com.simpledemo;

import com.simpledemo.config.Config;
import com.simpledemo.thrift.ThriftServer;

public class ServerLauncher {

    public static void main(String[] args) {

        ThriftServer server = new ThriftServer(Config.severHost, Config.severPort);

        if ("async".equalsIgnoreCase(Config.serverMode)) {
            server.setSync(false);
        } else {
            server.setSync(true);
        }

        server.start();
    }
}
