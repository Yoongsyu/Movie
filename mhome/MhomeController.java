package com.movie.web.mhome;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;	
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MhomeController {

	@Autowired
	private MhomeService movieService;

	
	@GetMapping(value = { "/", "/index" })
	public String index() {

		return "mhome";
	}

	@RequestMapping("/movie/list")
	public ResponseEntity<String> list(@RequestParam(required = true, defaultValue = "0") int sort) {

		JSONObject json = new JSONObject();
		List<Movie> list = new ArrayList<Movie>();
		JSONArray jsonArray = new JSONArray();
		String mv_code = "";
		String mv_name = "";
		String mv_grade = "";
		String mv_poster = "";
		String mv_sdate = "";
		String mv_gradeimg = "";
		String au_status = "";
		String mv_rate="";

		if (sort == 0) {
			// 박스오피스 기준정렬 기본값
			list = movieService.list();

			for (Movie movie : list) {
				JSONObject movieJson = new JSONObject();
				movieJson.put("mv_code", movie.getMv_code());
				movieJson.put("mv_name", movie.getMv_name());
				movieJson.put("mv_grade", movie.getMv_grade());
				movieJson.put("mv_poster", movie.getMv_poster());
				movieJson.put("mv_sdate", movie.getMv_sdate());
				movieJson.put("mv_gradeimg", movie.getMv_gradeimg());
				movieJson.put("au_status", movie.getAu_status());
				movieJson.put("mv_rate", movie.getMv_rate());

				jsonArray.add(movieJson);
			}

		} else if (sort == 1) {
			// 예매순으로 정렬
			list = movieService.list1();
			for (Movie movie : list) {
				JSONObject movieJson = new JSONObject();
				movieJson.put("mv_code", movie.getMv_code());
				movieJson.put("mv_name", movie.getMv_name());
				movieJson.put("mv_grade", movie.getMv_grade());
				movieJson.put("mv_poster", movie.getMv_poster());
				movieJson.put("mv_sdate", movie.getMv_sdate());
				movieJson.put("mv_gradeimg", movie.getMv_gradeimg());
				movieJson.put("au_status", movie.getAu_status());
				movieJson.put("mv_rate", movie.getMv_rate());

				jsonArray.add(movieJson);
			}

		} else if (sort == 2) {
			// 누적관객순으로 정렬
			list = movieService.list2();

			for (Movie movie : list) {
				JSONObject movieJson = new JSONObject();
				movieJson.put("mv_code", movie.getMv_code());
				movieJson.put("mv_name", movie.getMv_name());
				movieJson.put("mv_grade", movie.getMv_grade());
				movieJson.put("mv_poster", movie.getMv_poster());
				movieJson.put("mv_sdate", movie.getMv_sdate());
				movieJson.put("mv_gradeimg", movie.getMv_gradeimg());
				movieJson.put("au_status", movie.getAu_status());
				movieJson.put("mv_rate", movie.getMv_rate());

				jsonArray.add(movieJson);
			}
		} else if (sort == 3) {
			// 개봉일순으로 정렬
			list = movieService.list3();
			for (Movie movie : list) {
				JSONObject movieJson = new JSONObject();
				movieJson.put("mv_code", movie.getMv_code());
				movieJson.put("mv_name", movie.getMv_name());
				movieJson.put("mv_grade", movie.getMv_grade());
				movieJson.put("mv_poster", movie.getMv_poster());
				movieJson.put("mv_sdate", movie.getMv_sdate());
				movieJson.put("mv_gradeimg", movie.getMv_gradeimg());
				movieJson.put("au_status", movie.getAu_status());
				movieJson.put("mv_rate", movie.getMv_rate());

				jsonArray.add(movieJson);
			}

		} else if (sort == 4) {
			// 개봉
			list = movieService.list4();
			for (Movie movie : list) {
				JSONObject movieJson = new JSONObject();
				movieJson.put("mv_code", movie.getMv_code());
				movieJson.put("mv_name", movie.getMv_name());
				movieJson.put("mv_grade", movie.getMv_grade());
				movieJson.put("mv_poster", movie.getMv_poster());
				movieJson.put("mv_sdate", movie.getMv_sdate());
				movieJson.put("mv_gradeimg", movie.getMv_gradeimg());
				movieJson.put("au_status", movie.getAu_status());
				movieJson.put("mv_rate", movie.getMv_rate());
				jsonArray.add(movieJson);
			}

		} else if (sort == 5) {
			// 개봉예정
			list = movieService.list5();
			for (Movie movie : list) {
				JSONObject movieJson = new JSONObject();
				movieJson.put("mv_code", movie.getMv_code());
				movieJson.put("mv_name", movie.getMv_name());
				movieJson.put("mv_grade", movie.getMv_grade());
				movieJson.put("mv_poster", movie.getMv_poster());
				movieJson.put("mv_sdate", movie.getMv_sdate());
				movieJson.put("mv_gradeimg", movie.getMv_gradeimg());
				movieJson.put("au_status", movie.getAu_status());
				movieJson.put("mv_rate", movie.getMv_rate());

				jsonArray.add(movieJson);
			}

		}

		json.put("list", jsonArray);
//		System.out.println("예매율순위확인"+json.toString());

		return ResponseEntity.ok(json.toString());
	}

	@GetMapping("/movieupdate")
	public String movie1() throws Exception {

		String key = "049940f273ce11ffde7abfb717f953a6"; // 영화진흥위원회 깃공개xx

		StringBuilder urlBuilder = new StringBuilder(
				"http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?"); // 기본
																													// url세팅
																													// 영상진흥원
//전날 박스오피스 조회 ( 전일자 기준 으로 조회)

		LocalDateTime time = LocalDateTime.now().minusDays(1);
		String targetDt = time.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		System.out.println(targetDt);
		urlBuilder.append("&" + URLEncoder.encode("targetDt", "UTF-8") + "=" + URLEncoder.encode(targetDt, "UTF-8")); // kmdb

		urlBuilder.append("&" + URLEncoder.encode("key", "UTF-8") + "=" + key); 

		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");

		conn.setRequestProperty("Content-type", "application/json");

		BufferedReader rd;

		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {

			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder sb = new StringBuilder();
		String line = rd.readLine();

		conn.disconnect();

		JSONObject result = null;
		JSONParser jsonParser = new JSONParser();

		result = (JSONObject) jsonParser.parse(line);

		StringBuilder out = new StringBuilder();
		out.append(result.get("status") + " : " + result.get("status_message") + "\n");

// JSON데이터에서 "boxOfficeResult"라는 JSONObject를 가져온다.
		JSONObject data = (JSONObject) result.get("boxOfficeResult");

// JSONObject에서 Array데이터를 get하여 JSONArray에 저장한다.

		JSONArray array = (JSONArray) data.get("dailyBoxOfficeList");

		movieService.insertMovies(data); // json데이터로 보내기

		return "update";
	}

	@GetMapping("/infoupdate")
	public void movie2() throws Exception {

		String key = "049940f273ce11ffde7abfb717f953a6"; // 영화진흥위원회

		List<String> result1 = movieService.movieCd(); // 데이터베이스에서 고유번호 가져오기

		//System.out.println(result1.toString());

		for (String movieCd : result1) {
			StringBuilder urlBuilder = new StringBuilder(
					"http://www.kobis.or.kr/" + "kobisopenapi/webservice/rest/movie/" + "searchMovieInfo.json?"); // 영화상세1

			urlBuilder.append(URLEncoder.encode("key", "UTF-8") + "=" + key);
			urlBuilder.append("&" + URLEncoder.encode("movieCd", "UTF-8") + "=" + URLEncoder.encode(movieCd, "UTF-8"));

			URL url = new URL(urlBuilder.toString());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");

			conn.setRequestProperty("Content-type", "application/json");

			System.out.println("Response code: " + conn.getResponseCode());

			BufferedReader rd;

			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {

				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}

			StringBuilder sb = new StringBuilder();

			String line = rd.readLine();

			conn.disconnect();

			System.out.println("line : " + line);
			JSONObject result = null;
			JSONParser jsonParser = new JSONParser();

			result = (JSONObject) jsonParser.parse(line);

			JSONObject data = (JSONObject) result.get("movieInfoResult");

			movieService.infoupdate(data);

		}

	}

	@GetMapping("/kmdbupdate")
	public String kmdbupdate() throws Exception {

		String key = "8C387S782DIS3443795I"; // kmdb
		String releaseDts = "20230101";

		List<String> result1 = movieService.moviename(); // 데이터베이스에서 영화제목 가져오기

		for (String movieNm : result1) {
			StringBuilder urlBuilder = new StringBuilder(
					"http://api.koreafilm.or.kr/" + "openapi-data2/wisenut/search_api/search_json2.jsp?");

			urlBuilder.append(
					"&" + URLEncoder.encode("collection", "UTF-8") + "=" + URLEncoder.encode("kmdb_new2", "UTF-8"));
			urlBuilder.append(
					"&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(movieNm, "UTF-8")); /* 통합검색 */ // kmdb
			urlBuilder.append(
					"&" + URLEncoder.encode("releaseDts", "UTF-8") + "=" + URLEncoder.encode(releaseDts, "UTF-8")); // 개봉시작일
																													// //kmdb
			urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8"));

			URL url = new URL(urlBuilder.toString());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");

			conn.setRequestProperty("Content-type", "application/json");

			System.out.println("Response code: " + conn.getResponseCode());

			BufferedReader rd;

			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {

				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}

			StringBuilder sb = new StringBuilder();
			String line = "";

			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}

			String response = sb.toString();

			conn.disconnect();

			System.out.println("response : " + response);
			JSONObject result = null;
			JSONParser jsonParser = new JSONParser();

			try {

				result = (JSONObject) jsonParser.parse(response);

				movieService.kmdbupdate(result, movieNm);

			} catch (Exception e) {

				daterepeat(movieNm);

			}

		}

		return "update";

	}

	public void daterepeat(String movieNm) throws Exception {

		String key = "8C387S782DIS3443795I"; // kmdb
		String releaseDts = "20000101";

		StringBuilder urlBuilder = new StringBuilder(
				"http://api.koreafilm.or.kr/" + "openapi-data2/wisenut/search_api/search_json2.jsp?");

		urlBuilder
				.append("&" + URLEncoder.encode("collection", "UTF-8") + "=" + URLEncoder.encode("kmdb_new2", "UTF-8"));
		urlBuilder.append(
				"&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(movieNm, "UTF-8")); /* 통합검색 */ // kmdb
		urlBuilder
				.append("&" + URLEncoder.encode("releaseDts", "UTF-8") + "=" + URLEncoder.encode(releaseDts, "UTF-8")); // 개봉시작일
																														// //kmdb
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8"));

		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");

		conn.setRequestProperty("Content-type", "application/json");

		System.out.println("Response code: " + conn.getResponseCode());

		BufferedReader rd;

		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {

			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder sb = new StringBuilder();
		String line = "";

		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}

		String response = sb.toString();

		conn.disconnect();

		System.out.println("response : " + response);
		JSONObject result = null;
		JSONParser jsonParser = new JSONParser();

		result = (JSONObject) jsonParser.parse(response);

		movieService.kmdbupdate(result, movieNm);

	}

}
