package org.luizinfo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pessoa")
	private Long id;

	@Column(nullable = false)
	private String name;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date birth;

	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String phone;
	
	@Column(nullable = false)
	private String location;

	@Lob
	@Column(nullable = true)
	private byte[] caricature;
	
	@Lob
	@Column(nullable = true)
	private byte[] resume;

	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
	private List<Internationalization> internationalizations;
	
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
	private List<Media> medias;

	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
	private List<Hobbie> hobbies;
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public byte[] getCaricature() {
		return caricature;
	}

	public void setCaricature(byte[] caricature) {
		this.caricature = caricature;
	}

	public byte[] getResume() {
		return resume;
	}

	public void setResume(byte[] resume) {
		this.resume = resume;
	}

	public List<Internationalization> getInternationalizations() {
		return internationalizations;
	}

	public void setInternationalizations(List<Internationalization> internationalizations) {
		this.internationalizations = internationalizations;
	}

	public List<Media> getMedias() {
		return medias;
	}

	public void setMedias(List<Media> medias) {
		this.medias = medias;
	}

	public List<Hobbie> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<Hobbie> hobbies) {
		this.hobbies = hobbies;
	}

}
