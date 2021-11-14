package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.RequestMessage;
import ru.otus.protobuf.generated.ResponseMessage;

import java.util.List;

public class RemoteServiceImpl extends RemoteServiceGrpc.RemoteServiceImplBase {
    private final RealService realService;

    public RemoteServiceImpl(RealService realService) {
        this.realService = realService;
    }

    @Override
    public void getSequence(RequestMessage request, StreamObserver<ResponseMessage> responseObserver) {
        List<Long> sequence = this.realService.getSequence(request.getFirstValue(), request.getLastValue());
        sequence.forEach(value -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(responseMessage(value));
        });
        responseObserver.onCompleted();
    }

    private ResponseMessage responseMessage(long value) {
        return ResponseMessage.newBuilder()
                .setCurrentValue(value)
                .build();
    }
}
