package com.snailcatch.snailcatch.collector.impl;

import com.snailcatch.snailcatch.collector.SlowQueryCollector;

import java.util.ArrayList;
import java.util.List;

public class ThreadLocalSlowQueryCollector implements SlowQueryCollector {
    private static final ThreadLocal<List<String>> queryHolder = ThreadLocal.withInitial(ArrayList::new);

    @Override
    public void addQuery(String query) {
        queryHolder.get().add(query);
    }

    @Override
    public List<String> getQueries() {
        return queryHolder.get();
    }

    @Override
    public void clear() {
        queryHolder.remove();
    }
}

