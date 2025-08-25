package com.example.idol.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.idol.entity.Member;
import com.example.idol.service.ArtistService;
import com.example.idol.service.MemberService;

@Controller
public class MemberController {

    private final MemberService memberService;
    private final ArtistService artistService;

    @Autowired
    public MemberController(MemberService memberService, ArtistService artistService) {
        this.memberService = memberService;
        this.artistService = artistService;
    }

    @GetMapping("/members")
    public String getMembers(Model model) {
        var members = memberService.findAll();
        model.addAttribute("members", members);
        return "members";
    }

    @PostMapping("/members")
    public String postMember(@ModelAttribute @Validated Member member, BindingResult result,@RequestParam("member_cover") MultipartFile cover, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("artists", artistService.findAll());
            return "membersPost";
        }
        memberService.save(member, cover);
        return "redirect:/members";
    }

    @GetMapping("/members/membersPost")
    public String post(Model model) {
        model.addAttribute("member", new Member());
        var artists = artistService.findAll();
        model.addAttribute("artists", artists);
        return "membersPost";
    }

    @GetMapping("/members/{id}/membersUpdate")
    public String updateMember(@PathVariable("id") Integer memberid, Model model) {
        var member = memberService.findById(memberid);
        model.addAttribute("member", member);
        model.addAttribute("artists", artistService.findAll());  
        return "membersUpdate";
    }

    @PostMapping("/members/{id}/membersUpdate")
    public String update(@PathVariable("id") Integer id, 
    					@ModelAttribute("member") @Validated Member member,
                         BindingResult result,
                         @RequestParam("member_cover") MultipartFile cover,
                         Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("member", memberService.findAll());
            return "membersUpdate";
        }

        member.setMemberId(id);
        member.getMemberAge();
        memberService.updateMember(member,cover);
        

        return "redirect:/members";
    }
}
