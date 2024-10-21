package com.acnovate.kafka.consumer.AuditManager.errortemplate;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Helper class allows to apply different error processing strategies for different kinds of errors.
 * <p>
 * Implements a kind of Template Method pattern.
 *
 * @param <T> - type of the handler's result value
 */
public final class ErrorTemplate<T> {

    private Supplier<T> handler;

    private Optional<Consumer<T>> onSuccess = Optional.empty();

    private Optional<Consumer<Exception>> onFailure = Optional.empty();

    private Optional<Consumer<Exception>> onWarning = Optional.empty();

    private Optional<Runnable> onTimeout = Optional.empty();

    private ErrorTemplate(ExecutionTimeout executionTimeout, Supplier<T> handlerMethod) {
        this.handler = () -> {
            // wraps execution with timeout
            if (executionTimeout != null && executionTimeout.reached()) {
                throw new Timeout();
            } else {
                return handlerMethod.get();
            }
        };
    }

    private ErrorTemplate(ExecutionTimeout executionTimeout, Runnable handlerMethod) {
        this(executionTimeout, () -> {
            handlerMethod.run();
            return null;
        });
    }

    /**
     * Launches execution with error control and retrials
     */
    @SneakyThrows
    @SuppressWarnings({"squid:S1166", "squid:S1162", "squid:S2221"})
    public void go() {
        final AtomicReference<T> result = new AtomicReference<>();
        boolean success = false;
        try {
            result.set(handler.get());
            success = true;
        } catch (Timeout timeout) {
            onTimeout.ifPresent(Runnable::run);
        } catch (Exit exit) {
            onFailure.ifPresent(consumer -> consumer.accept(exit.getCarriedException()));
            if (!onFailure.isPresent()) {
                throw exit.getCarriedException();
            }
        } catch (Inform inform) {
            onWarning.ifPresent(
                    consumer -> consumer.accept(inform.getCarriedException()));
            if (!onWarning.isPresent()) {
                throw inform.getCarriedException();
            }
        } catch (Exception unhandled) {
            onFailure.ifPresent(consumer -> consumer.accept(unhandled));
            if (!onFailure.isPresent()) {
                throw unhandled;
            }
        }
        if (success) {
            onSuccess.ifPresent(consumer -> consumer.accept(result.get()));
        }
    }

    /**
     * Returns new instance of {@link ErrorTemplate}
     *
     * @param handlerMethod code to wrap with error control
     * @param <T>           handlerMethod's result type
     * @return {@link ErrorTemplate}
     */
    public static <T> ErrorTemplate<T> handle(Supplier<T> handlerMethod) {
        return new ErrorTemplate<>(null, handlerMethod);
    }

    /**
     * Returns new instance of {@link ErrorTemplate}
     *
     * @param handlerMethod code to wrap with error control
     * @return {@link ErrorTemplate}
     */
    public static ErrorTemplate<Void> handle(Runnable handlerMethod) {
        return new ErrorTemplate<>(null, handlerMethod);
    }

    /**
     * Returns new instance of {@link ErrorTemplate}
     *
     * @param executionTimeout execution timeout for whole retrial logic. is not precise because
     *                         checked prior to handler execution that may be delayed with error strategy sleeping time
     * @param handlerMethod    code to wrap with error control
     * @param <T>              handlerMethod's result type
     * @return {@link ErrorTemplate}
     */
    public static <T> ErrorTemplate<T> handle(ExecutionTimeout executionTimeout, Supplier<T> handlerMethod) {
        return new ErrorTemplate<>(executionTimeout, handlerMethod);
    }

    /**
     * Returns new instance of {@link ErrorTemplate}
     *
     * @param executionTimeout execution timeout for whole retrial logic. is not
     *                         precise because checked prior to handler execution
     *                         that may be delayed with error strategy sleeping time
     * @param handlerMethod    code to wrap with error control
     * @return {@link ErrorTemplate}
     */
    public static ErrorTemplate<Void> handle(ExecutionTimeout executionTimeout, Runnable handlerMethod) {
        return new ErrorTemplate<>(executionTimeout, handlerMethod);
    }

    /**
     * Adds error handling for particular error. All sequential error handlers will
     * be invoked by the order they were introduced. If more general error class
     * comes before specific one, error handling will be covered with the first
     * (innermost) strategy and will never reach outermost
     *
     * @param error         class of the {@link Exception} this strategy handles
     * @param strategy      error handling strategy, unlimitedRetry, limitedRetry so
     *                      far
     * @param errorCallback callback to be invoked when strategy catches configured
     *                      {@link Exception}
     * @param <E>           class of the exception it covers
     * @return {@link ErrorTemplate} with new handling strategy in the chain
     */
    public <E extends Exception> ErrorTemplate<T> onError(Class<E> error, HandlingStrategy strategy,
                                                          Consumer<E> errorCallback) {
        // wrap handler in strategy, and make it new handler
        strategy.setCall(this.handler);
        this.handler = strategy;

        strategy.setStrategyHandles(error);
        strategy.setErrorCallback((Consumer<Exception>) errorCallback);
        return this;
    }

    /**
     * Sets up a callback to handle warnings that can occur during the execution of a task.
     * This callback is invoked when a warning exception is encountered.
     * Warnings are typically non-fatal issues that might require attention but do not
     * necessarily disrupt the overall execution flow.
     * <p>
     * If a consumer for handling warnings is specified, the provided consumer will receive
     * the warning exceptions. If not specified, warnings might be logged or ignored.
     *
     * @param onWarning A consumer that will receive warning exceptions.
     * @return This {@link ErrorTemplate} instance with the onWarning callback configured.
     */
    public ErrorTemplate<T> onWarning(Consumer<Exception> onWarning) {
        this.onWarning = Optional.ofNullable(onWarning);
        return this;
    }

    /**
     * Sets up a callback to handle failures that can occur during the execution of a task.
     * This callback is invoked when an unhandled exception occurs or when a handled exception
     * exceeds the configured number of retries. Timeouts are not handled by this method.
     * <p>
     * If a consumer for handling failures is specified, the provided consumer will receive
     * the exceptions. If not specified, exceptions will be propagated.
     *
     * @param onFailure A consumer that will receive exceptions in case of failures.
     * @return This {@link ErrorTemplate} instance with the onFailure callback configured.
     */
    public ErrorTemplate<T> onFailure(Consumer<Exception> onFailure) {
        this.onFailure = Optional.ofNullable(onFailure);
        return this;
    }

    /**
     * Invoked on success.
     *
     * @param onSuccess consumer that receives value returned by handler method
     * @return {@link ErrorTemplate} with onSuccess callback set up
     */
    public ErrorTemplate<T> onSuccess(Consumer<T> onSuccess) {
        this.onSuccess = Optional.ofNullable(onSuccess);
        return this;
    }

    /**
     * Invoked when execution-retry is stopped because of execution timeout.
     *
     * @param onTimeout code to invoke when timeout happens
     * @return {@link ErrorTemplate} with onTimeout callback set up
     */
    public ErrorTemplate<T> onTimeout(Runnable onTimeout) {
        this.onTimeout = Optional.ofNullable(onTimeout);
        return this;
    }

    /**
     * Allows to exit with error from any stage: handling or error callback. May be
     * useful if you catch some subclass Exception and want to decide retry or fail
     * immediately.
     *
     * @param e exception to propagate
     */
    @SneakyThrows
    public static void exit(Exception e) {
        throw new Exit(e);
    }

    @SneakyThrows
    public static void inform(Exception e) {
        throw new Inform(e);
    }

    /**
     * Defines execution timeout, reaching it retry logic terminates
     *
     * @param timeout   timeout value
     * @param delayUnit unit of timeout value
     * @return ExecutionTimeout
     */
    public static ExecutionTimeout withExecutionTimeout(int timeout, TimeUnit delayUnit) {
        return new ExecutionTimeout(timeout, delayUnit);
    }

    /**
     * Unlimited Retry strategy. Will execute until error stops to happen
     *
     * @param delay     delay between retries
     * @param delayUnit unit of delay
     * @return {@link HandlingStrategy}
     */
    public static HandlingStrategy unlimitedRetry(int delay, TimeUnit delayUnit) {
        return new UnlimitedRetry(delay, delayUnit);
    }

    /**
     * Limited Retry strategy. Will execute until error stops to happen or number of
     * retries hits maximum allowed
     *
     * @param maxTries  maximal number of retries until in passes error further on
     * @param delay     delay between retries
     * @param delayUnit unit of delay
     * @return {@link HandlingStrategy}
     */
    public static HandlingStrategy limitedRetry(int maxTries, int delay, TimeUnit delayUnit) {
        return new LimitedRetry(maxTries, delay, delayUnit);
    }

    /**
     * Fail strategy. Will execute callback and stop execution
     *
     * @return {@link HandlingStrategy}
     */
    public static HandlingStrategy fail() {
        return new Fail();
    }

    /**
     * Warn strategy. Will execute callback and stop execution
     *
     * @return {@link HandlingStrategy}
     */
    public static HandlingStrategy warn() {
        return new Warn();
    }

    @Getter
    @Setter
    private static final class ExecutionTimeout {

        private LocalDateTime expectedEndTime;

        protected ExecutionTimeout(int timeout, TimeUnit delayUnit) {
            long milliseconds = delayUnit.toMillis(timeout);
            this.expectedEndTime = LocalDateTime.now().plus(milliseconds, ChronoUnit.MILLIS);
        }

        public boolean reached() {
            return LocalDateTime.now().isAfter(expectedEndTime);
        }
    }

    /**
     * Error handling strategy
     */
    public abstract static class HandlingStrategy implements Supplier {

        @Getter
        @Setter
        private Class<? extends Exception> strategyHandles;

        @Setter
        @Getter
        private Supplier call;

        @Setter
        @Getter
        private Consumer<Exception> errorCallback;

        protected HandlingStrategy() {
            call = () -> {
                throw new RuntimeException("ErrorStrategy handler method is not initialized");
            };
            errorCallback = (e) -> {
                throw new RuntimeException("ErrorStrategy error callback is not initialized");
            };
        }

        protected boolean handles(Exception e) {
            if (strategyHandles == null) {
                throw new RuntimeException("ErrorStrategy exception class is not initialized");
            }
            return strategyHandles.isAssignableFrom(e.getClass());
        }

    }

    /**
     * A strategy for immediate execution failure handling. The callback is executed in case
     * of exceptions, but the exception is propagated unless it's handled by the callback.
     */
    private static final class Fail extends HandlingStrategy {

        private Fail() {
            super();
        }

        @SneakyThrows
        @SuppressWarnings({"squid:S1162", "squid:S2221"})
        @Override
        public Object get() {
            try {
                return getCall().get();
            } catch (Exit | Timeout exit) {
                throw exit;
            } catch (Exception e) {
                if (handles(e)) {
                    getErrorCallback().accept(e);
                    exit(e);
                    // return will not be executed, just make compiler happy
                    // because it does not know about exception hidden behind the @SneakyThrows
                    return null;
                } else {
                    throw e;
                }
            }
        }

    }

    /**
     * A strategy for handling execution issues by providing warnings. The callback is executed
     * for exceptions that are considered warnings, and an informative action is taken.
     * Warnings are typically non-fatal issues that require attention but do not necessarily
     * disrupt the overall execution flow.
     */
    private static final class Warn extends HandlingStrategy {

        Warn() {
            super();
        }

        @SneakyThrows
        @SuppressWarnings({"squid:S1162", "squid:S2221"})
        @Override
        public Object get() {
            try {
                return getCall().get();
            } catch (ErrorTemplate.Inform | ErrorTemplate.Timeout exit) {
                throw exit;
            } catch (Exception e) {
                if (handles(e)) {
                    getErrorCallback().accept(e);
                    ErrorTemplate.inform(e);
                    // return will not be executed, just make compiler happy
                    // because it does not know about exception hidden behind the @SneakyThrows
                    return null;
                } else {
                    throw e;
                }
            }
        }

    }

    /**
     * Defines strategy for unlimited retrial
     */
    private static final class UnlimitedRetry extends HandlingStrategy {

        private final int delayBetween;

        private final TimeUnit delayUnit;

        private UnlimitedRetry(int delayBetween, TimeUnit delayUnit) {
            super();
            this.delayBetween = delayBetween;
            this.delayUnit = delayUnit;
        }

        @SneakyThrows
        @Override
        public Object get() {
            do {
                try {
                    return getCall().get();
                } catch (Exit | Timeout exit) {
                    throw exit;
                } catch (Exception e) {
                    if (handles(e)) {
                        getErrorCallback().accept(e);
                        delayUnit.sleep(delayBetween);
                    } else {
                        throw e;
                    }
                }
            } while (true);
        }

    }

    /**
     * Defines strategy for retrial for limited number of times
     */
    private static final class LimitedRetry extends HandlingStrategy {

        private final int maxTries;

        private final int delayBetween;

        private final TimeUnit delayUnit;

        private int triesPassed;

        LimitedRetry(int maxTries, int delayBetween, TimeUnit delayUnit) {
            super();
            this.maxTries = maxTries;
            this.delayBetween = delayBetween;
            this.delayUnit = delayUnit;
            this.triesPassed = 0;
        }

        @SneakyThrows
        @Override
        public Object get() {
            do {
                try {
                    return getCall().get();
                } catch (Exit | Timeout exit) {
                    throw exit;
                } catch (Exception e) {
                    if (handles(e)) {
                        getErrorCallback().accept(e);
                        if (triesPassed >= maxTries) {
                            exit(e);
                        } else {
                            delayUnit.sleep(delayBetween);
                        }
                        triesPassed++;
                    } else {
                        throw e;
                    }
                }
            } while (true);
        }

    }

    /**
     * Carries the Exit command
     */
    public static class Exit extends RuntimeException {

        final Exception carriedException;

        Exit(Exception carriedException) {
            this.carriedException = carriedException;
        }

        public Exception getCarriedException() {
            return carriedException;
        }
    }

    /**
     * Carries the Inform command
     */
    public static class Inform extends RuntimeException {

        final Exception carriedException;

        Inform(Exception carriedException) {
            this.carriedException = carriedException;
        }

        public Exception getCarriedException() {
            return carriedException;
        }
    }

    /**
     * Carries the Timeout exit command
     */
    public static class Timeout extends RuntimeException {
        public Timeout() {
        }
    }

}
