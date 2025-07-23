package com.example.idol.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.idol.entity.Artist;
import com.example.idol.repository.ArtistRepository;

import jakarta.transaction.Transactional;

@Service
public class ArtistService {

	private final ArtistRepository artistRepository;

	@Autowired
	public ArtistService(ArtistRepository artistRepository) {
		this.artistRepository = artistRepository;
	}

	public List<Artist> findAll() {
		return artistRepository.findAll();
	}

	public void delete(Integer artistId) {
		artistRepository.deleteById(artistId);
	}

	public void save(Artist artist) {
		artistRepository.save(artist);
	}
	
	public Artist findById(Integer artistId){
		return artistRepository.findById(artistId).orElseGet(Artist::new);
	}

	@Transactional
	public Artist updateArtist(Artist update) {
	   Artist entity = new Artist();
	    entity.setArtistName(update.getArtistName());
	    entity.setArtistHiraganaName(update.getArtistHiraganaName());
	    entity.setArtistArtUrl(update.getArtistArtUrl());
	    return artistRepository.save(entity);
	}


	

	
}
