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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Experience implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_experience")
	private Long id;
	
	@Column(nullable = false)
	private int position;
	
	@JsonFormat(pattern = "MM-dd-yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date startAt;
	
	@JsonFormat(pattern = "MM-dd-yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date endAt;
	
	@Column(nullable = false)
	private String companyName;
	
	@Column(nullable = true)
	private String website;
	
	@Column(nullable = false)
	private String logo;
	
	@Column(nullable = false)
	private String backgroundUrl;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(optional = false)
	private Pessoa pessoa;
	
	@OneToMany(mappedBy = "experience", cascade = CascadeType.ALL)
	private List<Internationalization> internationalizations;

	@OneToMany(mappedBy = "experience", cascade = CascadeType.ALL)
	private List<Technology> technologies;

	@OneToMany(mappedBy = "experience", cascade = CascadeType.ALL)
	private List<Media> medias;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Internationalization> getInternationalizations() {
		return internationalizations;
	}

	public void setInternationalizations(List<Internationalization> internationalizations) {
		this.internationalizations = internationalizations;
	}

	public List<Technology> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<Technology> technologies) {
		this.technologies = technologies;
	}

	public List<Media> getMedias() {
		return medias;
	}

	public void setMedias(List<Media> medias) {
		this.medias = medias;
	}

}
