//package com.snailcatch.snailcatch.config;
//
//import com.snailcatch.snailcatch.aspect.SlowQueryAspectConfig;
//import com.snailcatch.snailcatch.repository.TestRepository;
//import com.snailcatch.snailcatch.util.SlowQueryLogger;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.aop.Advisor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ContextConfiguration;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.longThat;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//@ContextConfiguration(classes = {SlowQueryAspectConfigTest.MockConfig.class, SlowQueryAspectConfig.class})
//@Import(SlowQueryAspectConfigTest.MockConfig.class)
//public class SlowQueryAspectConfigTest {
//
//    @Autowired
//    private Advisor slowQueryAdvisor;
//
//    @Autowired
//    private SlowQueryLogger slowQueryLogger;
//
//    @Autowired
//    private TestRepository testRepository;
//
//    @Test
//    public void testSlowQueryAdvisor_logsSlowQuery() throws Throwable {
//        // 느린 실행을 시뮬레이션하기 위해 메서드를 600ms 이상 딜레이 시킴
//        long delayMs = 600;
//        doAnswer(invocation -> {
//            Thread.sleep(delayMs);
//            return "test-result";
//        }).when(testRepository).findSomething();
//
//        // 실제 메서드 호출 (느리게 실행됨)
//        String result = testRepository.findSomething();
//
//        // 정상 리턴 확인
//        assertEquals("test-result", result);
//
//        // 슬로우 쿼리 로거가 호출됐는지 확인
//        verify(slowQueryLogger, times(1))
//                .logSlowQuery(anyString(), anyString(), longThat(d -> d >= delayMs), anyString());
//    }
//
//    @TestConfiguration
//    static class MockConfig {
//        @Bean
//        public SlowQueryLogger slowQueryLogger() {
//            return Mockito.mock(SlowQueryLogger.class);
//        }
//
//        @Bean
//        public TestRepository testRepository() {
//            return Mockito.mock(TestRepository.class);
//        }
//    }
//}
