package com.snailcatch.snailcatch.collector;

import java.util.List;

public interface QueryCollector {
    void addQuery(String query);
    List<String> getQueries();
    void clear();
}