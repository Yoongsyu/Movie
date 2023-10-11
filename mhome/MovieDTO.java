package com.movie.web.mhome;

import java.util.Date;

import lombok.Data;

@Data
public class MovieDTO {

	private String movieNm, rankInten, rankOldAndNew, audiInten, movieCd, prdtStatNm, genreNm, nations, directors,
			actors, companys, audits, keywords, posters, stlls, vod, plot;
	private int audiCnt, audiAcc, showTm;
	private Date openDt;
	private String mv_code, mv_name, mv_grade, mv_poster, mv_gradeimg;
	private Date mv_sdate;

	// 기본생성자는 기본!
	public MovieDTO() {
	}

	// 매개변수를 받는 생성자
	public MovieDTO(String movieNm, String rankInten, String rankOldAndNew, String audiInten, String movieCd,
			int audiCnt, int audiAcc, Date openDt) {

		this.movieNm = movieNm;
		this.rankInten = rankInten;
		this.rankOldAndNew = rankOldAndNew;
		this.audiInten = audiInten;
		this.movieCd = movieCd;

		this.audiCnt = audiCnt;
		this.audiAcc = audiAcc;
		this.openDt = openDt;

	}

	public MovieDTO(int showTm, String prdtStatNm, String genreNm, String nations, String directors, String actors,
			String companys, String audits, String movieCd) {

		this.showTm = showTm;
		this.prdtStatNm = prdtStatNm;
		this.genreNm = genreNm;
		this.nations = nations;
		this.directors = directors;
		this.actors = actors;
		this.companys = companys;
		this.audits = audits;
		this.movieCd = movieCd;

	}

	public MovieDTO(String keywords, String posters, String stlls, String vod, String plot, String movieNm) {

		this.keywords = keywords;
		this.posters = posters;
		this.stlls = stlls;
		this.vod = vod;
		this.plot = plot;
		this.movieNm = movieNm;

	}

	public MovieDTO(String mv_code, String mv_name, String mv_grade, String mv_poster, Date mv_sdate,
			String mv_gradeimg) {
		this.mv_code = mv_code;
		this.mv_name = mv_name;
		this.mv_grade = mv_grade;
		this.mv_poster = mv_poster;
		this.mv_sdate = mv_sdate;
		this.mv_gradeimg = mv_gradeimg;

	}

}
