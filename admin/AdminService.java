package com.movie.web.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

	@Autowired
	AdminDAO adminDAO;
	


	public List<Map<String, Object>> memberList() {
		return adminDAO.memberList();
	}

	public int gradeChange(Map<String, String> map) {
		return adminDAO.gradeChange(map);
	}

	public Map<String, Object> userpage(int mno) {
		return adminDAO.userpage(mno);
	}

	public int nicknameChange(Map<String, Object> map) {
		return adminDAO.nicknameChange(map);
	}

	public void pointChange(Map<String, Object> map) {
		adminDAO.pointChange(map);

	}

	public Map<String, Object> adminLogin(Map<String, Object> map) {
		return adminDAO.adminLogin(map);
	}

	public List<Map<String, Object>> memberSearch(Map<String, Object> map) {
		return adminDAO.memberSearch(map);
	}

	public String totalMember() {
		return adminDAO.totalMember();
	}

	public String totalIncome() {
		return adminDAO.totalIncome();
	}
	
	
	
	
	


	public List<Movielist> list() {

		return adminDAO.list();
	}

	public List<Map<String, Object>> list1() {

		return adminDAO.list1();
	}

	public int mvdelete(String mvcode) {
		adminDAO.mvdelete1(mvcode);
		return adminDAO.mvdelete(mvcode);

	}

	public List<Map<String, Object>> list2(String mvcode) {

		return adminDAO.list2(mvcode);
	}

	public int mvupdate(Map<String, String> map) {
		adminDAO.mvupdate1(map);
		return adminDAO.mvupdate(map);
	}

	public int mvserch(String mvname) {

		return adminDAO.mvserch(mvname);
	}
/*
	public String insertMovies(JSONObject data) throws ParseException {

		JSONArray array = (JSONArray) data.get("MovieList");

		for (Object movieObject : array) {
			JSONObject movie = (JSONObject) movieObject;

			String openString = (String) movie.get("openDt"); // 개봉일
			Date openDt = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(openString);
			String prdtStatNm = (String) movie.get("prdtStatNm");
			String movieCd = (String) movie.get("movieCd"); // 영화고유넘버

		}

		return "";

	}
*/

	public int movieinsert(Map<String, String> map) {
		
		
		adminDAO.movieinsert(map);
		
		return adminDAO.movieinsert1(map);
	}

	public String mvcodeck(String mvcode) {
		
		return adminDAO.mvcodeck(mvcode);
	}

	public int mvinfoupdate(String mvcode) {
	
		return 0;
	}

	public int infoupdate(JSONObject data) {
		
		JSONObject data1 = (JSONObject) data.get("movieInfo");
		

		int showTm = (Integer.parseInt((String) data1.get("showTm"))); // 상영시간
		String movieCd = (String) data1.get("movieCd"); // 영화고유넘버
		String genreNm = ""; // 장르명
		String nations = ""; // 국가
		String audits = ""; // 영화관람등급

		JSONArray gearray = (JSONArray) data1.get("genres");
		JSONArray nearray = (JSONArray) data1.get("nations");
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
		
		for (Object infoObject : audiarray) {
			JSONObject movieinfo = (JSONObject) infoObject;
			audits = (String) movieinfo.get("watchGradeNm"); // 관람등급
			
		
			if(audits== null || audits.equals("") || audits.equals(" ")) {
				audits = "전체관람가";
				
			}
		
		}

		
		Map<String, Object> movieMap = new HashMap<>();
	
		movieMap.put("showTm", showTm);  //상영시간
		movieMap.put("mv_code", movieCd); //고유코드
		movieMap.put("genreNm", genreNm); //장르
		movieMap.put("nations", nations); // 국가 
		movieMap.put("audits", audits); //영화관람등급
		
		System.out.println("movieMap" + movieMap.toString());
		return adminDAO.infoupdate(movieMap);
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
		String company = "null";

		Map<String, Object> movieMap = new HashMap<>();
		
		for (Object infoObject : dataarray) {
			JSONObject movieinfo = (JSONObject) infoObject;

				resultarray = (JSONArray) movieinfo.get("Result"); // 배열 Result에 접근

		}

		String keyy = null;

		for (Object infoObject : resultarray) {
			JSONObject movieinfo = (JSONObject) infoObject;

			keyy = (String) movieinfo.get("keywords"); // 키워드 비어있을수도있음

			
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

			 dbposters = adminDAO.poster(movieNm);
				//String[] posterParts = posters.split("\\|");
				//System.out.println(posterParts[0]);
					
			if (posters == null) {

				posters = "포스터가 없습니다.";
			
			} else if (posters != null && !dbposters.isEmpty()) {

				posters = dbposters;
				
			} else {
				String[] posterParts = posters.split("\\|");

				    if (posterParts.length > 0) {
				        // "|"로 나뉜 포스터 이미지가 있을 경우 첫 번째 포스터 이미지 선택
				        String firstPoster = posterParts[0];

				        posters = firstPoster;
				
						movieMap.put("posters", posters); // 포스터
				
				System.out.println("poster : " + firstPoster );
				System.out.println("poster11 : " + posters );
			}

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
		dbvod = adminDAO.dbvod(movieNm);
		
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
				 dbplot = adminDAO.dbplot(movieNm);
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
		
		for (Object infoObject : resultarray) {
			JSONObject movieinfo = (JSONObject) infoObject;
			company = (String) movieinfo.get("company"); // 스틸컷

			if (company == null || company =="") {

				company= "제작사를 등록해주세요.";
			} else if( company != null ) {
				
				movieMap.put("company", company); // 스틸컷
			
			}
		}

		
	
		
		movieMap.put("keywords", keywords);  //키워드
		movieMap.put("movieNm", movieNm); //영화이름
		
		movieMap.put("stlls", stlls); // 스틸컷
		movieMap.put("vod", vod); //예고편
		movieMap.put("plot", plot); //줄거리

	
		adminDAO.kmdbupdate(movieMap);
		adminDAO.kmdbupdate1(movieMap);
		adminDAO.insertrate();
	
		
	}

	public List<Map<String, Object>> mainserch(String mvname) {
		// TODO Auto-generated method stub
		return adminDAO.mainserch(mvname);
	}


}
