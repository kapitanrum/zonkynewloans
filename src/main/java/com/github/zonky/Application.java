package com.github.zonky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Main class for run application
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableScheduling
public class Application {

    private static final int TASK_SCHEDULER_POOLSIZE = 5;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Extending poolSize for taskScheduler.
     *
     * @return TaskScheduler bean.
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(TASK_SCHEDULER_POOLSIZE);
        return taskScheduler;
    }

}
