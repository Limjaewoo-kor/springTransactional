package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
@Slf4j
public class InternalCallV1Test {

    @Autowired
    CallService callService;


    @Test
    void printProxy() {
        log.info("callService class={}", callService.getClass());
    }
    @Test
    void internalCall() {
        callService.internal();
    }
    @Test
    void externalCall() {
        callService.external();
    }

    //빈등록
    @TestConfiguration
    static class InternalCallV1Config {
        @Bean
        CallService callService() {
            return new CallService();
        }
    }

    @Slf4j
    static class CallService {
        //메서드 내부호출에서는 this.internal이 실행되므로 Transactional이 적용되지않는다.
        // 적용하려면 별도의 클래스로 분리하여야한다.
        public void external() {
            log.info("call external");
            printTxInfo();
            internal();
        }

        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}", txActive);
//            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
//            log.info("tx readOnly={}",readOnly);
        }
    }

}