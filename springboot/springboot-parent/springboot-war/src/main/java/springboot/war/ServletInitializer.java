package springboot.war;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 新建一个 ServletInitializer 类，继承 SpringBootServletInitializer
 * <p>打成 war 包修改的第 ③ 处</p>
 * @description： 新建一个 ServletInitializer 类，继承 SpringBootServletInitializer
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootDemoWarApplication.class);
	}

}
