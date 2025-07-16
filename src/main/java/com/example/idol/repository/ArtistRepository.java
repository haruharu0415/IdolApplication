package com.example.idol.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.idol.entity.Artist;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Integer> {
	public List<Artist> findAll();


	
}
