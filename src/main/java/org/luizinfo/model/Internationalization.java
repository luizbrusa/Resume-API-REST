package org.luizinfo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Internationalization implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_internationalization")
	private Long id;
	
	@Column(nullable = false)
	private String language;
		
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = true)
	private String title;

	@Column(nullable = true)
	private String city;
	
	@Column(nullable = true)
	private String country;
	
	@Column(nullable = true)
	private String role;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(optional = true)
	private Pessoa pessoa;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(optional = true)
	private Post post;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(optional = true)
	private Experience experience;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}

}
