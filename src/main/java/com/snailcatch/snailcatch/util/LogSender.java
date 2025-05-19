package com.snailcatch.snailcatch.util;

public interface LogSender {
    void send(SlowQueryLog log);
    void flush();
    void shutdown();
}