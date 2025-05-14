package com.snailcatch.snailcatch.util;

public class SlowQueryLog {
    private final String methodName; // 감지한 메서드
    private final String sql; // 실행된 쿼리
    private final long durationMs; // 쿼리 실행 시간
    private final String explainPlan; // 실행 계획

    public SlowQueryLog(String methodName, String sql, long durationMs, String explainPlan) {
        this.methodName = methodName;
        this.sql = sql;
        this.durationMs = durationMs;
        this.explainPlan = explainPlan;
    }

    @Override
    public String toString() {
        return String.format(
                "[%s] %dms\nSQL: %s\nEXPLAIN:\n%s",
                methodName, durationMs, sql, explainPlan
        );
    }
}
