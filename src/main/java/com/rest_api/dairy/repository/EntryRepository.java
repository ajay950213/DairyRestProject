package com.rest_api.dairy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rest_api.dairy.entity.Entry;

public interface EntryRepository extends JpaRepository<Entry, Long> {

//	List<Entry> findByuserid(long userid);
	
//	@Query("select e from entries e where userid=:Id")
//	List<Entry> getEntriesByUserId(@Param("Id") long id);
	
	
	@Query(value = "SELECT * FROM entries WHERE user_id=:userId", nativeQuery = true)
    List<Entry> getEntriesByUserId(@Param("userId") long userId);
	
	@Query(value = "select id from entries", nativeQuery=true)
	List<Long> findAllEntryIds();
	
}
