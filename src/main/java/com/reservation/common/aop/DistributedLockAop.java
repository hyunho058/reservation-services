package com.reservation.common.aop;

import com.reservation.application.performance.exception.AlreadyReservedSeatException;
import com.reservation.domain.performance.repository.LockRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class DistributedLockAop {
    private final LockRepository lockRepository;
    private final AopForTransaction aopForTransaction;

    public DistributedLockAop(LockRepository lockRepository, AopForTransaction aopForTransaction) {
        this.lockRepository = lockRepository;
        this.aopForTransaction = aopForTransaction;
    }

    @Around("@annotation(com.reservation.common.aop.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        String key = "1A2";

        try {
            boolean available = lockRepository.lock(key, "lock");
            if (!available) {
                throw new AlreadyReservedSeatException();
            }

            return aopForTransaction.proceed(joinPoint);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        } finally {
            try {
                lockRepository.unlock(key);
            } catch (IllegalMonitorStateException e) {
                log.info("Already UnLock {} {}", method.getName(), key);
            }
        }
    }
}
