package com.bridgelabz.LoginMongodb.model;

public class MailDto 
{
	private Long id;

	private String toMailAddress;

	private String subject;

	private String salutation;

	private String body;

	private String mailSign;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the toMailAddress
	 */
	public String getToMailAddress() {
		return toMailAddress;
	}

	/**
	 * @param toMailAddress the toMailAddress to set
	 */
	public void setToMailAddress(String toMailAddress) {
		this.toMailAddress = toMailAddress;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the salutation
	 */
	public String getSalutation() {
		return salutation;
	}

	/**
	 * @param salutation the salutation to set
	 */
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the mailSign
	 */
	public String getMailSign() {
		return mailSign;
	}

	/**
	 * @param mailSign the mailSign to set
	 */
	public void setMailSign(String mailSign) {
		this.mailSign = mailSign;
	}

}
