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
	
	@JsonFormat(pattern = "MM/dd/yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date startAt;
	
	@JsonFormat(pattern = "MM/dd/yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date endAt;
	
	@Column(nullable = false)
	private String companyName;
	
	@Column(nullable = true)
	private String website;
	
	@Lob
	@Column(nullable = true)
	private byte[] logo;
	
	@Column(nullable = true)
	private String logoTypeFile;
	
	@Column(nullable = true)
	private String logoName;
	
	@Lob
	@Column(nullable = true)
	private byte[] backgroundUrl;
	
	@Column(nullable = true)
	private String backgroundUrlTypeFile;

	@Column(nullable = true)
	private String backgroundUrlName;
	
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

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public String getLogoTypeFile() {
		return logoTypeFile;
	}

	public void setLogoTypeFile(String logoTypeFile) {
		this.logoTypeFile = logoTypeFile;
	}

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public byte[] getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(byte[] backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	public String getBackgroundUrlTypeFile() {
		return backgroundUrlTypeFile;
	}

	public void setBackgroundUrlTypeFile(String backgroundUrlTypeFile) {
		this.backgroundUrlTypeFile = backgroundUrlTypeFile;
	}

	public String getBackgroundUrlName() {
		return backgroundUrlName;
	}

	public void setBackgroundUrlName(String backgroundUrlName) {
		this.backgroundUrlName = backgroundUrlName;
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
