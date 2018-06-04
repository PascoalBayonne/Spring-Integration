package com.arpit.spring.integration.contants;

public class ApplicationConstants {

	/** Don't let anyone else instantiate this class */
	private ApplicationConstants() {
		throw  new IllegalStateException("utility uses only");
	}

	public static final String CSV = "*.csv";
	public static final String DOCX = "*.docx";
	public static final String TXT = "*.txt";
}
