package com.audit.automator.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * @author Ayodele Oluwabusola
 * <p>
 * <br/><br/>
 * So it was noticed in some scenarios that the container does not open a new transaction when a method annotated
 * with the {@link Transactional} {@link Transactional} REQUIRES_NEW is invoked locally within
 * the same EJB. This is because the container does not intercept the method call and thus does not prepare the
 * environment accurately before the method is invoked. This class is to be used to ensure that method calls that
 * require the environment to be properly set can do so via any of the appropriate methods below
 * <p>
 * <br/><br/>
 * <strong>NB</strong>: note that if an exception is thrown while executing the method of a functional argument,
 */

@Slf4j
@Service
public class ProxyUtil {

    /**
     * @param supplier
     * @return
     */
    @Transactional
    public <T> T executeWithNewTransaction(Supplier<T> supplier) {
        return supplier.get();
    }

    /**
     * @param runnableTask
     */
    @SneakyThrows
    @Transactional
    public void executeWithNewTransaction(RunnableTask runnableTask) {
        runnableTask.run();
    }

    /**
     * @param <T>
     * @param supplier
     * @return
     */
    @Async
    public <T> Future<T> executeAsync(Supplier<T> supplier) {
        return new AsyncResult<T>(supplier.get());
    }

    /**
     * @param runnableTask
     */
    @SneakyThrows
    @Async
    public void executeAsync(RunnableTask runnableTask) {
        runnableTask.run();
    }

//    @SuppressWarnings({"PMD.AvoidCatchingGenericException"})
//    public <T extends ResponseData> T handleServiceCall(Supplier<T> serviceCallFunction, Class<T> clazz) {
//        try {
//            return serviceCallFunction.get();
//        } catch (Exception e) {
//            log.error("Error handling service call", e);
//        }
//
//        T errorResponse = getNewInstance(clazz);
//
//        errorResponse.setResponseCodeEnum(ResponseCodeEnum.ERROR);
//
//        return errorResponse;
//    }
//
//    private <T extends ResponseData> T getNewInstance(Class<T> clazz) {
//        try {
//            return clazz.newInstance();
//        } catch (InstantiationException | IllegalAccessException e) {
//            throw new IllegalArgumentException("No default no-args constructor in class : " + clazz.getName(), e);
//        }
//    }

    /**
     * This functional interface was declared and used in the methods above in order to create a {@link FunctionalInterface} equivalent
     * of {@link Runnable} to avoid any confusion since {@link Runnable} often connotes a {@link Thread} context which is not really the
     * case for the scenarios above
     */
    @FunctionalInterface
    public interface RunnableTask {
        void run() throws IOException;
    }
}
