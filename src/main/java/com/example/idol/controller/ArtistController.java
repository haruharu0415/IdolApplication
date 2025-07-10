package com.example.idol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.idol.entity.Artist;
import com.example.idol.service.ArtistService;



@Controller
public class ArtistController {

	private final ArtistService artistService;

	@Autowired
	public ArtistController(ArtistService artistService) {
		this.artistService = artistService;
	}

	@GetMapping("/artists")
	public String getArtists(Model model) {
		var artists = artistService.findAll();
		model.addAttribute("artists", artists);
		return "artists";
	}

	@DeleteMapping("/artists/{id}")
	public String delete(@PathVariable("id") Integer artistId){
		artistService.delete(artistId);
		return "redirect:/artists";
	}
	
	@GetMapping("/artists/registration")
	public String registration(Model model) {
		model.addAttribute(new Artist());
		return "registration";
	}
	
	@PostMapping("/artists")
	public String registerArtist(@ModelAttribute @Validated Artist artists,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return "registration";
		}
		artistService.save(artists);
		return "redirect:/artists";
	}
	
	@GetMapping("/artists/{id}/detail")
	public String getMembers(@PathVariable("id") Integer artistId, Model model) {
		//var detail = artistService.findMemberByArtistId(artistId);
		var artist = artistService.findById(artistId);
		model.addAttribute("artist",artist);
		return "detail";
	}
	
	
	
}