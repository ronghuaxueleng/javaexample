package springboot.properties.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import springboot.properties.config.ApplicationConfig;
import springboot.properties.config.AuthorConfig;

@RestController
@RequestMapping("/config")
public class ConfigController {
	@Autowired
	private ApplicationConfig applicationConfig;
	@Autowired
	private AuthorConfig authorConfig;

	@SuppressWarnings("rawtypes")
	@GetMapping({"", "/", "/index"})
	public Map index() {
		HashMap<Object, Object> ret = Maps.newHashMap();
		ret.put("applicationConfig", applicationConfig);
		ret.put("authorConfig", authorConfig);
		return ret;
	}
}
