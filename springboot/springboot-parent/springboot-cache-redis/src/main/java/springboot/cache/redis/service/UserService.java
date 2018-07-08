package springboot.cache.redis.service;

import java.util.List;

import springboot.cache.redis.entity.User;

public interface UserService {
	User save();

	User update(Long id);

	User delete(Long id);

	List<User> find();

	User find(Long id);

	User find(String name);
}
