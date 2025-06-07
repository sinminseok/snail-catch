package com.snailcatch.snailcatch.global.collector;

import java.util.List;

public interface QueryCollector {
    void addQuery(String query);
    List<String> getQueries();
    void clear();
}