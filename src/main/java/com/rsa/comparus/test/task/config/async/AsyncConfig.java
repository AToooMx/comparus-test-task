package com.rsa.comparus.test.task.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {
    public static final String VIRTUAL_THREAD_POOL_TASK_EXECUTOR = "virtualThreadPoolTaskExecutor";

    @Bean(VIRTUAL_THREAD_POOL_TASK_EXECUTOR)
    public Executor executor() {
        var executor = new SimpleAsyncTaskExecutor();
        executor.setVirtualThreads(true);
        executor.setThreadNamePrefix("virtualThread-");
        return executor;
    }
}
