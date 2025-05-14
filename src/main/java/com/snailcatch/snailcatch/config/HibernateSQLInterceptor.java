package com.snailcatch.snailcatch.config;

import com.snailcatch.snailcatch.util.SlowQueryHolder;
import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Component;

@Component
public class HibernateSQLInterceptor extends EmptyInterceptor {

    @Override
    public String onPrepareStatement(String sql) {
        SlowQueryHolder.setCurrentQuery(sql); // 현재 쿼리 저장
        return super.onPrepareStatement(sql);
    }
}