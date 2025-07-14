package com.example.idol.entity;

import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "members")
@NoArgsConstructor

public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberId;
	private Integer artistId;
	@NotEmpty
	private String memberName;
	@NotEmpty
	private String memberHiraganaName;
	private LocalDate memberBirthday;
	private String memberPhoto;
	
	@ManyToOne
	@JoinColumn(name = "artistId",insertable=false, updatable=false)
	private Artist artist;

	public Integer getMemberAge() {
		return Period.between(memberBirthday, LocalDate.now()).getYears();
	}
}
