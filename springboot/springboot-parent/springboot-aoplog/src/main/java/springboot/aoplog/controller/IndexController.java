package springboot.aoplog.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

/**
 * IndexController
 *
 */
@RestController
public class IndexController {

	@GetMapping({"", ""})
	public String index() {
		return "index";
	}

	@SuppressWarnings("rawtypes")
	@GetMapping({"/test"})
	public Map test(@RequestParam String name) {
		ConcurrentMap<String, Object> ret = Maps.newConcurrentMap();
		ret.put("name", name);
		return ret;
	}
}
