package com.ppj.oct.login;

import java.sql.Date;

public class SqueezeUser {

	private String squeeze_id;
	private String squeeze_pw;
	private Date squeeze_date;
	private String squeeze_folder;
	private String squeeze_email;
	
	public SqueezeUser() {
	}

	public SqueezeUser(String squeeze_id, String squeeze_pw, Date squeeze_date, String squeeze_folder,
			String squeeze_email) {
		super();
		this.squeeze_id = squeeze_id;
		this.squeeze_pw = squeeze_pw;
		this.squeeze_date = squeeze_date;
		this.squeeze_folder = squeeze_folder;
		this.squeeze_email = squeeze_email;
	}
	

	public SqueezeUser(String squeeze_id, String squeeze_pw, String squeeze_email) {
		super();
		this.squeeze_id = squeeze_id;
		this.squeeze_pw = squeeze_pw;
		this.squeeze_email = squeeze_email;
	}

	public String getSqueeze_id() {
		return squeeze_id;
	}

	public void setSqueeze_id(String squeeze_id) {
		this.squeeze_id = squeeze_id;
	}

	public String getSqueeze_pw() {
		return squeeze_pw;
	}

	public void setSqueeze_pw(String squeeze_pw) {
		this.squeeze_pw = squeeze_pw;
	}

	public Date getSqueeze_date() {
		return squeeze_date;
	}

	public void setSqueeze_date(Date squeeze_date) {
		this.squeeze_date = squeeze_date;
	}

	public String getSqueeze_folder() {
		return squeeze_folder;
	}

	public void setSqueeze_folder(String squeeze_folder) {
		this.squeeze_folder = squeeze_folder;
	}

	public String getSqueeze_email() {
		return squeeze_email;
	}

	public void setSqueeze_email(String squeeze_email) {
		this.squeeze_email = squeeze_email;
	}
	
	
	
	
	
}
