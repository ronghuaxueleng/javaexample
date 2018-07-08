package springboot.orm.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import springboot.orm.jpa.entity.JpaUser;

public interface JpaUserRepository extends JpaRepository<JpaUser, Long> {

	/**
	 * 自定义一个查询，HQL，根据姓名查询
	 *
	 * @param name 名称
	 * @return JpaUser
	 */
	@Query("from JpaUser u where u.name like :name")
	JpaUser findJpaUser(@Param("name") String name);

	JpaUser findJpaUserByName(String name);

	List<JpaUser> findJpaUsersByIdIn(List<Long> ids);

}
