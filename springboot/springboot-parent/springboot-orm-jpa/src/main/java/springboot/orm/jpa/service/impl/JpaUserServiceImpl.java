package springboot.orm.jpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import springboot.orm.jpa.entity.JpaUser;
import springboot.orm.jpa.repository.JpaUserRepository;
import springboot.orm.jpa.service.JpaUserService;

@Service
public class JpaUserServiceImpl implements JpaUserService {
	@Autowired
	private JpaUserRepository jpaUserRepository;

	@Override
	public JpaUser findById(Long id) {
		return jpaUserRepository.findOne(id);
	}

	@Override
	public List<JpaUser> findAll() {
		return jpaUserRepository.findAll();
	}

	@Override
	public JpaUser insert(JpaUser user) {
		return jpaUserRepository.save(user);
	}

	@Override
	public JpaUser update(JpaUser user) {
		return jpaUserRepository.save(user);
	}

	@Override
	public void delete(JpaUser user) {
		jpaUserRepository.delete(user);
	}

	@Override
	public List<JpaUser> insertList(List<JpaUser> userList) {
		return jpaUserRepository.save(userList);
	}

	@Override
	public JpaUser findJpaUser(String name) {
		return jpaUserRepository.findJpaUser(name);
	}

	@Override
	public List<JpaUser> findJpaUsersByIdIn(List<Long> ids) {
		return jpaUserRepository.findJpaUsersByIdIn(ids);
	}

	@Override
	public Page<JpaUser> findByPage(Pageable pageable) {
		return jpaUserRepository.findAll(pageable);
	}
}
