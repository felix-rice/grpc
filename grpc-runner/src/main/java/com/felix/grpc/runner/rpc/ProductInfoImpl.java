package com.felix.grpc.runner.rpc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.felix.grpc.sdk.ecommerce.Product;
import com.felix.grpc.sdk.ecommerce.ProductId;
import com.felix.grpc.sdk.ecommerce.ProductInfoGrpc;

import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

/**
 * @author lixin40 <lixin40@kuaishou.com>
 * Created on 2022-06-23
 */
public class ProductInfoImpl extends ProductInfoGrpc.ProductInfoImplBase {

    private final Map<String, Product> productMap = new HashMap<>();

    /**
     * 添加产品
     * @param request 请求
     * @param responseObserver 流式监听者，普通模式可用可不用，流式模式使用监听者获取数据
     */
    @Override
    public void addProduct(Product request, StreamObserver<ProductId> responseObserver) {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        productMap.put(randomUUIDString, request);
        ProductId id = ProductId.newBuilder().setValue(randomUUIDString).build();
        responseObserver.onNext(id);
        responseObserver.onCompleted();
    }

    /**
     * 通过产品id获取产品
     * @param request 产品id
     * @param responseObserver 流式监听者，用于关闭服务
     */
    @Override
    public void getProduct(ProductId request, StreamObserver<Product> responseObserver) {
        String id = request.getValue();
        if (productMap.containsKey(id)) {
            responseObserver.onNext(productMap.get(id));
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
        }
    }
}
