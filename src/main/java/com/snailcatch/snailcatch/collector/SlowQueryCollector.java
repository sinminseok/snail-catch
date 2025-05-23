package com.snailcatch.snailcatch.collector;

import java.util.List;

public interface SlowQueryCollector {
    void addQuery(String query);
    List<String> getQueries();
    void clear();
}