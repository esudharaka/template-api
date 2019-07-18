package com.programming;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class JettyServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(JettyServer.class);
    private static Server server;

    public static void main(String[] args) {
        LOGGER.info("============ Starting the Server =============");
        int maxThreads = 100;
        int minThreads = 10;
        int idleTimeout = 120;

        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);

        server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[] { connector });

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(RestServer.class, "/status");

        try {
            server.start();
        } catch (Exception e) {
            LOGGER.error("Error while starting the server", e);
        }
    }



    void stop() throws Exception {
        server.stop();
    }
}
