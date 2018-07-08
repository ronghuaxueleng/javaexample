package springboot.task.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * <p>
 * 定时任务配置，配置线程池，使用不同线程执行任务，提升效率
 * </p>
 *
 * @description： 定时任务配置，配置线程池，使用不同线程执行任务，提升效率
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackages = "springboot.task.schedule.task")
public class TaskConfig implements SchedulingConfigurer {

	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
	}

	@Bean
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(100);
	}

}
