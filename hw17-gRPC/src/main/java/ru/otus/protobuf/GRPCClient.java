package ru.otus.protobuf;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.RequestMessage;
import ru.otus.protobuf.generated.ResponseMessage;

import java.util.concurrent.CountDownLatch;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8191;
    private static final long FIRST_VALUE_FROM_SERVER = 1;
    private static final long LAST_VALUE_FROM_SERVER = 3;
    private static final int FIRST_VALUE_ON_CLIENT = 0;
    private static final int LAST_VALUE_ON_CLIENT = 10;

    private static long curValFromServer = 0;
    private static int curValOnClient = 0;
    private static boolean curValFromServerHandled = false;

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
                        curValFromServer = respMsg.getCurrentValue();
                        System.out.printf("Server msg: %d\n", curValFromServer);
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

        for (int i = FIRST_VALUE_ON_CLIENT; i < LAST_VALUE_ON_CLIENT; i++) {
            System.out.println("step " + i );
        }

        latch.await();

        channel.shutdown();

    }
}
