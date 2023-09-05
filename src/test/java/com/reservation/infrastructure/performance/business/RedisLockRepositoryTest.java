package com.reservation.infrastructure.performance.business;

import com.reservation.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class RedisLockRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private RedisLockRepository lockRepository;

    @DisplayName("동일한 key 값 이 존재하면 false를 반환한다.")
    @Test
    void redisLock() {
        //given
        String lockKey = "test lock key";
        String lockValue = "lock";

        lockRepository.lock(lockKey, lockValue);
        //when
        Boolean lockStatus = lockRepository.lock(lockKey, lockValue);
        //then
        assertThat(lockStatus).isFalse();
    }

    @DisplayName("unLock 후 동일한 key 값 으로 lock를 걸면 true를 반환한다.")
    @Test
    void redisUnLock() {
        String lockKey = "test lock key";
        String lockValue = "lock";

        lockRepository.lock(lockKey, lockValue);
        String result = lockRepository.get(lockKey);

        assertThat(result).isEqualTo(lockValue);

        lockRepository.unlock(lockKey);

        String unLocked = lockRepository.get(lockKey);
        assertThat(unLocked).isNull();
    }
}
