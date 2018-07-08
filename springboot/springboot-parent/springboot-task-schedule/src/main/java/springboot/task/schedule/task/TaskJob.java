package springboot.task.schedule.task;

import com.xiaoleilu.hutool.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * 定时任务
 * </p>
 *
 * @description： 定时任务
 */
@Component
@Slf4j
public class TaskJob {

	/**
	 * 每隔 10s 执行一次
	 */
	@Scheduled(cron = "0/10 * * * * ?")
	public void job1(){
		log.info("【job1】开始执行：{}",DateUtil.formatDateTime(new Date()));
	}

	/**
	 * 间隔 2s 执行
	 * 固定间隔时间
	 */
	@Scheduled(fixedRate = 2000)
	public void job2(){
		log.info("【job2】开始执行：{}",DateUtil.formatDateTime(new Date()));
	}

	/**
	 * 延迟 5s 后间隔 4s 执行
	 * 固定等待时间
	 */
	@Scheduled(fixedDelay = 4000,initialDelay = 5000)
	public void job3(){
		log.info("【job3】开始执行：{}",DateUtil.formatDateTime(new Date()));
	}
}
