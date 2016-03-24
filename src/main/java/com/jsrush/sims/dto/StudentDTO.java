package com.jsrush.sims.dto;

import com.jsrush.sims.entity.Student;

public class StudentDTO extends Student {

	private static final long serialVersionUID = 8377016256036738538L;

	private String graduationDateStr;
	
	private String degreeDateStr;

	public String getGraduationDateStr() {
		return graduationDateStr;
	}

	public void setGraduationDateStr(String graduationDateStr) {
		this.graduationDateStr = graduationDateStr;
	}

	public String getDegreeDateStr() {
		return degreeDateStr;
	}

	public void setDegreeDateStr(String degreeDateStr) {
		this.degreeDateStr = degreeDateStr;
	}
	
}
