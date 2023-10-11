package com.movie.web.admin;


import java.util.Date;

import lombok.Data;

@Data
public class Movielist {
	
	private String mv_code,mv_poster,mv_name,mv_maker,au_status ;
	private int au_acccnt;
	private Date mv_sdate;

	
	public Movielist() {
		
		
	}
	
	
	public Movielist(String mv_poster,String mv_code,String mv_name,Date mv_sdate,
			String mv_maker,String au_status,int au_acccnt)  {
		
	this.mv_poster=mv_poster;
	this.mv_code=mv_code;  
	this.mv_name=mv_name;  
	this.mv_sdate=mv_sdate; 
	this.mv_maker=mv_maker; 
	this.au_status=au_status;
	this.au_acccnt=au_acccnt;
		
		
		
	}
	




}
