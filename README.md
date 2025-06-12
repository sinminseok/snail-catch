# 🐌 Snail Catch SQL Formatter SDK

`SnailCatch SQL Formatter SDK`는 Java 기반의 SQL 로그 포맷터로, 복잡하고 가독성이 낮은 SQL 문과 실행 계획(EXPLAIN 결과)을 사람이 읽기 좋은 형식으로 변환해주는 유틸리티 SDK입니다.



---

##  제공 기능

1. 바인딩된 SQL 쿼리 콘솔로그에 자동 출력 
2. 수집된 쿼리 로그를 확인하는 View 제공  

---

## 이 SDK는 어떤 개발자에게 유용한가요?

- 쿼리 디버깅을 자주 하거나, SQL 로그를 정리해서 보고 싶은 **백엔드 개발자**
- 서비스 성능 개선을 위해 **EXPLAIN 결과를 로그로 남기고 분석**하고자 하는 개발자
- 내부 **SQL 분석 도구 혹은 APM 시스템**을 만들거나 연동하려는 팀


## 콘솔 출력 예시

----

```text
==================== Snail Catch ====================
Method         : UserRepository.findByEmail
Execution Time : 13 ms
SQL Queries:
SELECT
  id, email, name
FROM
  users
WHERE
  email = 'test@example.com'

Execution Plans:
| id | select_type | table | partitions | type | possible_keys | key | key_len | ref | rows | filtered | Extra           |
|----|-------------|-------|------------|------|----------------|-----|---------|-----|------|----------|------------------|
| 1  | SIMPLE      | users | -          | ref  | email_index    | email_index | 767 | const | 1    | 100.0    | Using where     |
=====================================================

```

## 사용 방법

----

1. build.gradle SDK 라이브러리 추가 
2. REST API KEY 발급 (http://localhost:8081/settings/api-key) 접속 후 발급 

3. application.yml 설정 방법 (발급한 REST API KEY && JPA,QueryDSL 을 사용하는 포인트컷 추가)
4. 수집된 쿼리 로그는 (http://localhost:8081/query-logs) 에서 모니터링 가능 

```
snail-catch:
   api-key : {REST API KEY}
   repository-pointcut: execution(* com.myapp.repository..*(..))
```


