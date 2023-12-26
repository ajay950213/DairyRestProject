package com.rest_api.dairy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rest_api.dairy.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
//	Optional<User> findFirstByUsername(String username);
	
//	@Query(value = "select * from users where id =:id", nativeQuery = true)
//	public User findById(long id);

//	@Query("SELECT u FROM User u WHERE u.id = :id")
//	User findById(long id);
	
	public User findById(long id);
	
	@Query(value = "select id from dairy_users", nativeQuery=true)
	List<Long> findAllUserIds();

}

