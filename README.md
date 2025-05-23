## Snail Catch 

#### DB 쿼리 성능 및 슬로우 쿼리 로깅 분석 SDK 서비스

---

<p align="center">
  <img src="./icon.png" alt="로고" width="200"/>
</p>


본 프로젝트는 JPA 또는 QueryDSL 을 사용하는 사용자에게 느린 쿼리에 대해 이벤트를 발생시키고  
쿼리, 실행 계획, 소요 시간등을 로그로 남겨 쿼리 개선 방향에 대한 개발 생산성을 높이는 것을 목표로 하고 있습니다.  

  

### 시스템 아키 텍처

----

![로고](./architecture.png)



### 기능 명세서

----

1. 슬로우 쿼리 로깅   
쿼리 실행 시간을 측정하고, 설정된 기준 이상의 시간이 소요된 쿼리를 로그로 기록한다.
   - QueryExcutionListener 를 통해 쿼리 실행 시간을 측정한다.
   - 쿼리 실행 시간이 설정한 시간(ex 500ms) 이상일 경우 로그에 기록한다.
   - SQL, 실행 시간, 발생 시점, 관련 테이블 등을 로그에 포함한다.
   - 실행 계획도 함께 추출해 로그로 저장한다.


2. 쿼리 성능 측정  
각 쿼리의 성능을 실시간으로 측정하고 기록한다.
   - JPA/Hibernate 쿼리의 실행 시간을 측정한다.
   - SQL 실행 전에 시작 시간 기록, 종류 후 종료 시간을 기록해 소요된 시간을 계산한다.
   - 쿼리별로 평균 성능, 최대/최소 실행 시간 등을 추적한다.


3. SnailCatch Server
- SDK 를 설치한 사용자로부터 로그를 수신한다.  
- 슬로우 쿼리에 대한 정보(시간, 메서드 네이밍, 실행 계획)을 NOSQL 형식으로 저장한다.
- 사용자별 로그를 모니터링 하는 기능을 제공한다.


4. 사용자 식별 
SDK 를 사용자를 식별하기 위한 인증/인가 방식이 필요하다.
회원가입, 로그인이 필요하고 API KEY 를 발급해준다.
사용자는 발급한 API KEY 를 application.yml 에서 관리해 자동으로 슬로우 쿼리 로그를 Snail Catch 서버에 수신한다.




Log Receiver (Spring 서버)
- API 키 인증
- 요청 유효성 검증 
- NOSQL 에 로그 저장 (MongoDB?)


사용 방법 

1. application.yml JPA or QueryDSL 포인트 컷 위치 추가

# (SDK 사용자가 자신의 서비스에서 작성)
slowquery:
repository-pointcut: execution(* com.myapp.repository..*(..))

