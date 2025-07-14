package com.example.idol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public void save(Member members) {
		memberRepository.save(members);
		
	}

}
