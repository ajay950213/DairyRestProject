package com.rest_api.dairy.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "entries")
public class Entry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date entrydate;
	private String desription;
	
//	@ManyToOne(cascade = CascadeType.REMOVE)
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = true)
	private User userid;
	
	public Entry() {
		super();
	}

	public Entry(long id, Date entrydate, String desription, User userid) {
		super();
		this.id = id;
		this.entrydate = entrydate;
		this.desription = desription;
		this.userid = userid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Date entrydate) {
		this.entrydate = entrydate;
	}

	public String getDesription() {
		return desription;
	}

	public void setDesription(String desription) {
		this.desription = desription;
	}

	public User getUserid() {
		return userid;
	}

	public void setUserid(User userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "Entry [id=" + id + ", entrydate=" + entrydate + ", desription=" + desription + ", userid=" + userid
				+ "]";
	}
	
}
