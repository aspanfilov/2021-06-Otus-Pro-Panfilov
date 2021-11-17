package ru.otus.protobuf;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.RequestMessage;
import ru.otus.protobuf.generated.ResponseMessage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8191;
    private static final long FIRST_VALUE_FROM_SERVER = 1;
    private static final long LAST_VALUE_FROM_SERVER = 10;
    private static final int COUNT = 50;

    private static long valueFromServer = 0;
    private static long valueOnClient = 0;

    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        CountDownLatch latch = new CountDownLatch(1);
        System.out.println("Client start\n");
        RemoteServiceGrpc.RemoteServiceStub stub = RemoteServiceGrpc.newStub(channel);

        stub.getSequence(
                RequestMessage.newBuilder()
                        .setFirstValue(FIRST_VALUE_FROM_SERVER)
                        .setLastValue(LAST_VALUE_FROM_SERVER)
                        .build(),
                new StreamObserver<ResponseMessage>() {
                    @Override
                    public void onNext(ResponseMessage respMsg) {
                        lock.lock();
                        valueFromServer = respMsg.getCurrentValue();
                        lock.unlock();

                        System.out.printf("Server msg: %d\n", valueFromServer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println(t);
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Finish");
                        latch.countDown();
                    }
                });

        for (int i = 0; i < COUNT; i++) {

            valueOnClient++;

            lock.lock();
            valueOnClient += valueFromServer;
            valueFromServer = 0;
            lock.unlock();

            System.out.printf("currentValue: %d\n", valueOnClient );
            Thread.sleep(1000);
        }

        latch.await();

        channel.shutdown();
    }
}
