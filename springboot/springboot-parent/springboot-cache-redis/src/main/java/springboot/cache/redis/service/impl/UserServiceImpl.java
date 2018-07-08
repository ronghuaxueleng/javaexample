package springboot.cache.redis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.cache.redis.dao.UserDao;
import springboot.cache.redis.entity.User;
import springboot.cache.redis.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	public User save() {
		return userDao.save();
	}

	public User update(Long id) {
		return userDao.update(id);
	}

	public User delete(Long id) {
		return userDao.delete(id);
	}

	public List<User> find() {
		return userDao.find();
	}

	public User find(Long id) {
		return userDao.find(id);
	}

	public User find(String name) {
		return userDao.find(name);
	}
}
