package com.reservation.common.aop;

import com.reservation.application.performance.dto.CreateReservationValue;
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

    @Around("@annotation(com.reservation.common.aop.DistributedLock) && args(performanceId,requestValue, ..)")
    public Object lock(final ProceedingJoinPoint joinPoint,
                       final Long performanceId,
                       final CreateReservationValue requestValue) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key = performanceId + requestValue.seatLocation() + requestValue.seatNumber();

        try {
            boolean lockAvailable = lockRepository.lock(
                    key,
                    "lock",
                    distributedLock.leaseTime()
            );

            if (!lockAvailable) {
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
