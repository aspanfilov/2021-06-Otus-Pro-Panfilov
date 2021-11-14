package ru.otus.protobuf.service;

import java.util.List;

public interface RealService {
    List<Long> getSequence(long firstValue, long lastValue);
}
