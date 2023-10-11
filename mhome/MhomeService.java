package com.movie.web.mhome;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MhomeService {

	@Autowired
	private MhomeDAO movieDAO;

	@Transactional
	public void insertMovies(JSONObject data) throws ParseException {

		JSONArray array = (JSONArray) data.get("dailyBoxOfficeList");

		for (Object movieObject : array) {
			JSONObject movie = (JSONObject) movieObject;

			String movieNm = (String) movie.get("movieNm"); // 영화이름
			String openString = ((String) movie.get("openDt")); // 개봉일
			Date openDt = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(openString);
			String rankInten = (String) movie.get("rankInten"); // 전일대비 순위증감분
			String rankOldAndNew = (String) movie.get("rankOldAndNew"); // 랭킹신규진입여부
			int audiCnt = (Integer.parseInt((String) movie.get("audiCnt"))); // 해당일 관객수
			String audiInten = (String) movie.get("audiInten"); // 전일대비 관객수 증감분

			int audiAcc = (Integer.parseInt((String) movie.get("audiAcc"))); // 누적관객수

			String movieCd = (String) movie.get("movieCd"); // 영화고유넘버

			MovieDTO movieDTO = new MovieDTO(movieNm, rankInten, rankOldAndNew, audiInten, movieCd, audiCnt, audiAcc,
					openDt);

			// 영화고유넘버가 존재하는지 DB에서 확인후 조건문 실행
			String existingMovie = movieDAO.findMovieByMovieCd(movieDTO.getMovieCd());
			String existingMovie1 = movieDAO.findMovieByMovieCd1(movieDTO.getMovieCd());

			

			if (existingMovie != null && existingMovie1 != null) {
				// 기존 영화 정보 업데이트
				movieDAO.updateMovie(movieDTO);
				movieDAO.updateAudience(movieDTO);

				System.out.println("업데이트");
			} else {
				// 새로운 영화 추가
				movieDAO.insertMovie(movieDTO);
				movieDAO.insertAudience(movieDTO);
				System.out.println("추가");
			}

		}

	}

	public void infoupdate(JSONObject data) {

		JSONObject data1 = (JSONObject) data.get("movieInfo");
		String movieCd = (String) data1.get("movieCd"); // 영화고유넘버

		int showTm = (Integer.parseInt((String) data1.get("showTm"))); // 상영시간
		String prdtStatNm = (String) data1.get("prdtStatNm"); // 개봉상태
		String genreNm = ""; // 장르명
		String nations = ""; // 국가
		String directors = ""; // 감독명
		String actors = ""; // 배우들
		String companys = ""; // 배급사
		String audits = ""; // 영화관람등급

		JSONArray gearray = (JSONArray) data1.get("genres");

		JSONArray nearray = (JSONArray) data1.get("nations");

		JSONArray diarray = (JSONArray) data1.get("directors");
		JSONArray actarray = (JSONArray) data1.get("actors");
		JSONArray comarray = (JSONArray) data1.get("companys");
		JSONArray audiarray = (JSONArray) data1.get("audits");
		
	
		
		List<String> genreList = new ArrayList<>();

		for (Object infoObject : gearray) {
			JSONObject movieinfo = (JSONObject) infoObject;
			genreNm = (String) movieinfo.get("genreNm"); // 장르명
			genreList.add(genreNm);
			genreNm = String.join(",", genreList);

		}
		genreNm = String.join(",", genreList);
		genreList.clear();

		for (Object infoObject : nearray) {
			JSONObject movieinfo = (JSONObject) infoObject;
			nations = (String) movieinfo.get("nationNm"); // 국가명

		}
		for (Object infoObject : diarray) {
			JSONObject movieinfo = (JSONObject) infoObject;
			directors = (String) movieinfo.get("peopleNm"); // 감독명

		}

		for (Object infoObject : actarray) {
			JSONObject movieinfo = (JSONObject) infoObject;
			actors = (String) movieinfo.get("peopleNm"); // 배우명
			genreList.add(actors);

		}
		// List 를 ,로연결하여 하나의 문자열로 만듬***
		actors = String.join(",", genreList);
		genreList.clear();

		for (Object infoObject : comarray) {
			JSONObject movieinfo = (JSONObject) infoObject;
			companys = (String) movieinfo.get("companyNm"); // 배급사
			genreList.add(companys);

		}
		companys = genreList.get(0);

		for (Object infoObject : audiarray) {
			JSONObject movieinfo = (JSONObject) infoObject;
			audits = (String) movieinfo.get("watchGradeNm"); // 관람등급
			
		
			if(audits== null || audits.equals("") || audits.equals(" ")) {
				audits = "전체관람가";
				
			}
		
		}

		MovieDTO movieDTO = new MovieDTO(showTm, prdtStatNm, genreNm, nations, directors, actors, companys, audits,
				movieCd);
		movieDAO.infoupdate(movieDTO);
		movieDAO.infoupdate1(movieDTO);

	

	}

	public List<String> movieCd() {

		List<String> movieInfoList = new ArrayList<>();

		movieInfoList.addAll(movieDAO.MovieCd());

		return movieInfoList;

	}

	public List<String> moviename() {

		List<String> moviename = movieDAO.moviename();

		return moviename;
	}

	public void kmdbupdate(JSONObject result, String movieNm) {

		JSONArray dataarray = (JSONArray) result.get("Data"); // Data 배열에 들어감
		JSONArray resultarray = new JSONArray();
		JSONArray plotarray = new JSONArray();
		JSONArray vodarray = new JSONArray();

		String keywords = "null";
		String posters = "null";
		String stlls = "null";
		String vod = "null";
		String plot = "null";

		for (Object infoObject : dataarray) {
			JSONObject movieinfo = (JSONObject) infoObject;

				resultarray = (JSONArray) movieinfo.get("Result"); // 배열 Result에 접근

		}

		String keyy = null;

		for (Object infoObject : resultarray) {
			JSONObject movieinfo = (JSONObject) infoObject;

			keyy = (String) movieinfo.get("keywords"); // 키워드 비어있을수도있음

			System.out.println("keywords : " + keyy);
			if (keyy != null) {

				keywords = keyy;

			}

			if (keyy == null || keyy.isEmpty()) {
				keywords = "키워드가 없습니다.";
			}

		}

		String dbposters=null;
		
		for (Object infoObject : resultarray) {
			JSONObject movieinfo = (JSONObject) infoObject;
			posters = (String) movieinfo.get("posters"); // 포스터

			 dbposters = movieDAO.poster(movieNm);
			System.out.println("dbpost" + dbposters);

			if (posters == null) {

				posters = "포스터가 없습니다.";

			} else if (posters != null && dbposters != null) {

				posters = dbposters;

			} else if (posters != null && dbposters == null) {
				posters = posters;
			}

		}

		for (Object infoObject : resultarray) {
			JSONObject movieinfo = (JSONObject) infoObject;
			stlls = (String) movieinfo.get("stlls"); // 스틸컷

			if (stlls == null) {

				stlls = "스틸컷이 없습니다.";
			}
		}

		String mainVod = null;
		String teaserVod = null;
		String trailerVod = null;
	
		
		for (Object infoObject : resultarray) {
			JSONObject movieinfo = (JSONObject) infoObject;
			movieinfo = (JSONObject) movieinfo.get("vods"); // 메인예고편만 지정해서 저장하기1
			vodarray = (JSONArray) movieinfo.get("vod");

			for (Object infoObject1 : vodarray) {
				JSONObject movieinfo1 = (JSONObject) infoObject1; // 메인예고편 저장 1-1

				String vodClass = (String) movieinfo1.get("vodClass"); // 기본값으로 빈 문자열을 설정
				String vodUrl = (String) movieinfo1.get("vodUrl"); // 기본값으로 빈 문자열을 설정

	

				if (vodClass != null) {
					if (vodClass.contains("메인") && mainVod == null) {
						mainVod = vodUrl;

					} else if (vodClass.contains("티저") && teaserVod == null) {
						teaserVod = vodUrl;
					} else if (vodClass.contains("예고편") && trailerVod == null) {
						trailerVod = vodUrl;
					}
				}
			}

			// 메인, 티저, 예고편 vod 중 하나가 있으면 루프 종료
			if (mainVod != null || teaserVod != null || trailerVod != null) {
				break;
			}
		}
		String dbvod =null;
		dbvod = movieDAO.dbvod(movieNm);
		
		// 메인, 티저, 예고편 vod 중 우선순위에 따라 하나를 선택하여 사용
		if (mainVod != null && ( dbvod == null || dbvod.isEmpty())) {
			vod = mainVod;
		} else if (teaserVod != null && ( dbvod == null || dbvod.isEmpty())) {
			vod = teaserVod;
		} else if (trailerVod != null && ( dbvod == null || dbvod.isEmpty())) {
			vod = trailerVod;
		} else if (dbvod != null) {
			vod = dbvod;
			
		} else { 
			vod = "예고편이 없습니다. 직접 입력해주세요";
			
		}

		String dbplot = null;

		for (Object infoObject : resultarray) {
			JSONObject movieinfo = (JSONObject) infoObject;
			movieinfo = (JSONObject) movieinfo.get("plots"); // plot 배열안에 배열
			// plot = (String) movieinfo.get("plot");
			plotarray = (JSONArray) movieinfo.get("plot");

			for (Object infoObject1 : plotarray) {
				JSONObject movieinfo1 = (JSONObject) infoObject1;
				String plotlang = (String) movieinfo1.get("plotLang");
				String plottext = (String) movieinfo1.get("plotText"); // 마지막
				 dbplot = movieDAO.dbplot(movieNm);
				//System.out.println(plotlang);
				
				
				if (plottext == null || plottext.isEmpty()) {

					plot = "줄거리가 없습니다.";

				} else if (plottext != null && dbplot != null) {

					plot = dbplot;

				} else if (plottext != null && dbplot == null && plotlang.contains("한국어") ) {
					plot = plottext;
				}

				
				
			}
		}


		MovieDTO movieDTO = new MovieDTO(keywords, posters, stlls, vod, plot, movieNm);

		movieDAO.kmdbupdate(movieDTO);
		movieDAO.kmdbupdate1(movieDTO);
		

	}

	public List<Movie> list() {

		return movieDAO.list();
	}

	public List<String> mvruntime() {

		return movieDAO.mvruntime();
	}

	public List<Movie> list1() {
		return movieDAO.list1();
	}

	public List<Movie> list2() {
		return movieDAO.list2();
	}

	public List<Movie> list3() {
		return movieDAO.list3();
	}

	public List<Movie> list4() {

		return movieDAO.list4();
	}

	public List<Movie> list5() {
		return movieDAO.list5();
	}

}
