package com.xw.english.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfiguration {

    private final static int CORE_THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    private final static int MAX_THREAD_COUNT = 2 * CORE_THREAD_COUNT;


    @Bean
    public ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(CORE_THREAD_COUNT, MAX_THREAD_COUNT, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(64));
    }
}
