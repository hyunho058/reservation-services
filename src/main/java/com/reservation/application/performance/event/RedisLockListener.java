package com.reservation.application.performance.event;

import com.reservation.domain.performance.repository.LockRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class RedisLockListener {
    private final LockRepository lockRepository;

    public RedisLockListener(LockRepository lockRepository) {
        this.lockRepository = lockRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void unLockEvent(String lockKey) {
        lockRepository.unlock(lockKey);
    }
}
