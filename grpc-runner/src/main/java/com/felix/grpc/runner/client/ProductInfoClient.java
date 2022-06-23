package com.felix.grpc.runner.client;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.felix.grpc.sdk.ecommerce.Product;
import com.felix.grpc.sdk.ecommerce.ProductId;
import com.felix.grpc.sdk.ecommerce.ProductInfoGrpc;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author lixin40 <lixin40@kuaishou.com>
 * Created on 2022-06-23
 */
public class ProductInfoClient {
    private static final Logger logger = Logger.getLogger(ProductInfoClient.class.getName());

    private final ProductInfoGrpc.ProductInfoBlockingStub blockingStub;

    public ProductInfoClient(Channel channel) {
        this.blockingStub = ProductInfoGrpc.newBlockingStub(channel);
    }

    public void addProduct() {
        final float pr = 5000.f;
        logger.info("will try to add a Product");
        ProductId productId = blockingStub.addProduct(Product.newBuilder()
                .setName("Apple iPhone 13")
                .setPrice(pr)
                .setDescription("256g")
                .build());
        logger.info(productId.getValue());
        Product product = blockingStub.getProduct(productId);
        logger.info(product.toString());
    }

    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:9090";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        try {
            ProductInfoClient client = new ProductInfoClient(channel);
            client.addProduct();
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
