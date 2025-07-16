package com.example.idol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.idol.entity.Member;
import com.example.idol.service.ArtistService;
import com.example.idol.service.MemberService;

@Controller
public class MemberController {

	private final MemberService memberService;
	
	private final ArtistService artistService;
	
	@Autowired
	public MemberController(MemberService memberService,ArtistService artistService) {
		this.memberService = memberService;
		this.artistService = artistService;
	}
	
	@GetMapping("/members")
	public String getMembers(Model model) {
		var members = memberService.findAll();
		model.addAttribute("members",members);
		return "members";
	}
	
	
	
	@PostMapping("/members")
	public String postMember(@ModelAttribute @Validated Member member,BindingResult result,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("artists",artistService.findAll());
			return "membersPost";
		}
		memberService.save(member);
		return "redirect:/members";
	}
	
	@GetMapping("/members/membersPost")
	  public String post(Model model){
	    model.addAttribute("member", new Member());  
	    var artists = artistService.findAll();
	    model.addAttribute("artists",artists);
	    return "membersPost";
	  }
	
}
