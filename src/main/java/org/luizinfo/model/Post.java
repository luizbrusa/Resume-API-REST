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
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_post")
	private Long id;
	
	@Column(nullable = false)
	private String thumbnail;
	
	@Column(nullable = false)
	private String http;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date date;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(optional = false)
	private Pessoa pessoa;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Internationalization> internationalizations;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getHttp() {
		return http;
	}

	public void setHttp(String http) {
		this.http = http;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

}
