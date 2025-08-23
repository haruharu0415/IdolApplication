package com.example.idol.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.idol.entity.Member;
import com.example.idol.repository.MemberRepository;

@Service
public class MemberService {
	private final MemberRepository memberRepository;

	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	public void save(Member member, MultipartFile file) throws IOException {
        String uploadDir = "static/images/"; 

        String originalFilename = file.getOriginalFilename();
        String filename = originalFilename;

        int i = 1;
        while (Files.exists(Paths.get(uploadDir, filename))) {
            String name = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
            filename = name + "(" + i + ")" + ext;
            i++;
        }

        Files.write(Paths.get(uploadDir, filename), file.getBytes());

        member.setMemberPhoto("images/" + filename);

        memberRepository.save(member);
    }
	
	public Member findById(Integer memberId){
		return memberRepository.findById(memberId).orElseGet(Member::new);
	}
	
	@Transactional
	public Member updateMember(Member update) {

		//DBからとってくる。
	   Member entity = memberRepository.findById(update.getMemberId())
			   .orElseThrow(() -> new IllegalArgumentException("存在しないアーティスト"));
	   //上書き
	    entity.setMemberName(update.getMemberName());
	    entity.setMemberHiraganaName(update.getMemberHiraganaName());
	    entity.setMemberBirthday(update.getMemberBirthday());
	    entity.setArtistId(update.getArtistId());
	    
	    //update
	    return memberRepository.save(entity);
	}

}