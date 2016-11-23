package com.midtrans.mandiri;

import org.eclipse.jetty.server.Server;

public class App {

    public static void main(String[] args) throws Exception {
        // Starting the Server
        Server server = new Server(8080);
        server.start();
        server.dumpStdErr();
        server.join();
    }
}
