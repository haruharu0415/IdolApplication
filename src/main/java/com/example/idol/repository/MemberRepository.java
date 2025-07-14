package com.example.idol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.idol.entity.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, Integer> {
	
	@Query("SELECT m FROM Member m LEFT JOIN FETCH m.artist")
	public List<Member> findAll();

}
