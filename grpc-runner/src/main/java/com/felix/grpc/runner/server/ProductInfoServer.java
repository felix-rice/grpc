package com.felix.grpc.runner.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.felix.grpc.runner.rpc.ProductInfoImpl;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * @author lixin40 <lixin40@kuaishou.com>
 * Created on 2022-06-23
 */
public class ProductInfoServer {
    private static final Logger logger = Logger.getLogger(ProductInfoServer.class.getName());

    private Server server;

    /**
     * start
     * @throws IOException e
     */
    private void start() throws IOException {
        final int port = 9090;
        server = ServerBuilder.forPort(port).addService(new ProductInfoImpl()).build().start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting gRPC server since JVM is shutting down");
            try {
                ProductInfoServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }

    /**
     * stop
     * @throws InterruptedException e
     */
    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * e
     * @throws InterruptedException e
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final ProductInfoServer server = new ProductInfoServer();
        server.start();
        server.blockUntilShutdown();
    }
}
