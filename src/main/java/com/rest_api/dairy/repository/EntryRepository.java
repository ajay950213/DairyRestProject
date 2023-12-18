package com.rest_api.dairy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rest_api.dairy.entity.Entry;

public interface EntryRepository extends JpaRepository<Entry, Long> {

	List<Entry> findByuserid(long userid);
	
//	@Query(value = "select * from entries where userid =:id")
//	public List<Entry> findByUserId(long id);

	
	@Query("SELECT e FROM Entry e WHERE e.userid.id = :userId")
    List<Entry> findByUserId(@Param("userId") long userId);	
}
