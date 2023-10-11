package com.movie.web.mhome;

import lombok.Data;

@Data
public class Movie {
	
	private String mv_code,mv_name,mv_grade,mv_poster,mv_gradeimg;
	private String mv_sdate,au_status,mv_rate;

	
	public Movie() {
		
	}
	
public Movie(String mv_code, String mv_name, String mv_grade, String mv_poster, String mv_sdate,
		String mv_gradeimg,String au_status,String mv_rate) {
	
	this.mv_code = mv_code;
	this.mv_name = mv_name;
	this.mv_grade = mv_grade;
	this.mv_poster = mv_poster;
	this.mv_sdate = mv_sdate;
	this.mv_gradeimg = mv_gradeimg;
	this.au_status = au_status;
	this.mv_rate = mv_rate;
	
		
	}
	
	

}
