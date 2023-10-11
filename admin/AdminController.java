package com.movie.web.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

   @Autowired
   private AdminService adminService;

   @Autowired
   private AdimUtil adminUtil;

   // 메인화면
   @GetMapping(value = { "/", "admin" })
   public String admin(HttpSession session) {
      /*
       * if (session.getAttribute("adminInfo") != null) { return "admin/member"; }
       */
      if (session.getAttribute("status") != null) {
         if (adminUtil.obj2Int(session.getAttribute("status")) != 0) {
            return "redirect:/login";
         } else if (adminUtil.obj2Int(session.getAttribute("status")) == 0) {
            session.setAttribute("adminInfo", session.getAttribute("id"));
            session.setAttribute("id", session.getAttribute("id"));
            return "admin/member";
         }
   

      }
      return "admin/login";
   }

   // 로그인
   @PostMapping("/login")
   public ModelAndView adminLogin(@RequestParam Map<String, Object> map, HttpSession session) {
//      System.out.println(map);

      Map<String, Object> result = adminService.adminLogin(map);
      ModelAndView mv = new ModelAndView();

//      System.out.println("나한테오는거야?" + result);

      if (adminUtil.obj2Int(result.get("count")) == 1 && adminUtil.obj2Int(result.get("m_status")) == 0) {
         // 세션올리기
//         System.out.println("여기옴?");
         session.setAttribute("id", result.get("m_id"));
         session.setAttribute("adminInfo", result);
         session.setAttribute("status", result.get("m_status"));
         // 메인으로 이동하기
         mv.setViewName("redirect:/admin/member");
      } else {
         String error = "너..관리자 아니지....";
         mv.addObject("error", error);
         mv.addObject("redirect:/admin/login");
      }
      return mv;
   }

   // 로그아웃
   @GetMapping("/logout")
   public String logout(HttpSession session) {

      session.setMaxInactiveInterval(0);
      session.invalidate();

      return "redirect:/";
   }

   // member
   @RequestMapping(value = "/member", method = RequestMethod.GET)
   public ModelAndView member(@RequestParam Map<String, Object> map, HttpSession session) {

      ModelAndView mv = new ModelAndView("redirect:/login");
      
      if(session.getAttribute("adminInfo") == null) {
         mv.setViewName("redirect:/login");
      }
      
      if (session.getAttribute("adminInfo") != null) {
         if (adminUtil.obj2Int(session.getAttribute("status")) == 0) {
            
         if (map.get("search") == null || map.get("searchV") == null) {
            mv.addObject("memberList", adminService.memberList());
            mv.addObject("totalMember", adminService.totalMember());
            mv.addObject("totalIncome", adminService.totalIncome());
         } else {
            List<Map<String, Object>> searchList = adminService.memberSearch(map);
//            System.out.println("맵에머있는데" + map);
//            System.out.println("여기에뭐가있어" + searchList);
            mv.addObject("memberList", searchList);
            mv.addObject("totalMember", adminService.totalMember());
            mv.addObject("totalIncome", adminService.totalIncome());

         }
         }mv.setViewName("admin/member");
      } else {
         mv.setViewName("redirect:/login");
      }
      return mv;
   }
      

   // gradeChange
   @RequestMapping(value = "/gradeChange", method = RequestMethod.GET)
   public String gradeChange(@RequestParam Map<String, String> map) {
      int result = adminService.gradeChange(map);
//      System.out.println(result);
      return "redirect:/admin/member";
   }

   // gradeChange 마이페이지에서
   @ResponseBody
   @RequestMapping(value = "/gradeChange2", method = RequestMethod.POST)
   public String gradeChange2(@RequestParam Map<String, String> map) {
      JSONObject json = new JSONObject();
      int result = adminService.gradeChange(map);
//      System.out.println("되나요" + result);
      json.put("result", result);
      return json.toString();
   }

   // 회원정보를 볼수있다.
   @GetMapping("/userpage")
   public String userpage(@RequestParam(value = "mno") int mno, Model model, HttpSession session) {
//      System.out.println("회원번호잡아오나" + mno);

      if (session.getAttribute("adminInfo") == null) {
         return "/login";
      } else {
         Map<String, Object> myInfo = adminService.userpage(mno);
         model.addAttribute("myInfo", myInfo);

         return "admin/userpage";
      }
   }

   // 부적절한 닉네임 변경
   @ResponseBody
   @PostMapping("/nicknameChange")
   public String nicknameChange(@RequestParam Map<String, Object> map, Model model) {
      JSONObject json = new JSONObject();
      int change = adminService.nicknameChange(map);

//      System.out.println("닉변하면 담는 것들은?" + map);
      json.put("change", change);

      return json.toString();
   }

   // 포인트변경
   @PostMapping("/pointChange")
   public String pointChange(@RequestParam Map<String, Object> map) {

      adminService.pointChange(map);

      String mno = (String) map.get("mno"); // 멤버 고유번호 가져오기
      return "redirect:/admin/userpage?mno=" + mno;

   }

   // 제재알림메일
   @ResponseBody
   @PostMapping("/email")
   public String mail(@RequestParam Map<String, Object> map, Model model) throws EmailException {
      JSONObject json = new JSONObject();
//      System.out.println("맵에 뭐가 담겨있니" + map);
      if (map != null) {
         adminUtil.htmlMailSender(map);
         json.put("send", "1");
      } else {
         json.put("send", "0");
      }
      return json.toString();
   }
@GetMapping("/movieupload")
   public String adminmovie(@RequestParam(defaultValue = "1") int page, Model model) {

      List<Map<String, Object>> list = adminService.list1();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 날짜포맷용
      List<Map<String, Object>> movieList = new ArrayList<>();
      DecimalFormat formatter = new DecimalFormat("#,###"); // 숫자포맷용

      int pageSize = 5; // 한 페이지에 보여줄 항목 수

      int totalItems = list.size(); // 전체 항목 수
      int totalPages = (int) Math.ceil((double) totalItems / pageSize); // 총 페이지 수

      // 현재 페이지에 해당하는 항목 추출
      int startIndex = (page - 1) * pageSize;
      int endIndex = Math.min(startIndex + pageSize, totalItems);
      List<Map<String, Object>> pagedMovieList = list.subList(startIndex, endIndex);

      for (Map<String, Object> movie : pagedMovieList) {
         Map<String, Object> movieMap = new HashMap<>();
         movieMap.put("mv_poster", movie.get("mv_poster"));

         String codeck = (String) movie.get("mv_code");

         if (movie.get("mv_code") == null || movie.get("mv_code") == "" || movie.get("mv_code") == " ") {

            break;

         } else {
            movieMap.put("mv_code", movie.get("mv_code"));
         }

         movieMap.put("mv_name", movie.get("mv_name"));

         if (movie.get("mv_sdate") == null) {

            String formattedDate = "0000-00-00";
            movieMap.put("mv_sdate", formattedDate);
         } else {

            String formattedDate = dateFormat.format(movie.get("mv_sdate"));
            movieMap.put("mv_sdate", formattedDate);
         }

         if (movie.get("mv_maker") == null) {

            String mv_maker = "";
            movieMap.put("mv_maker", mv_maker);
         } else {
            movieMap.put("mv_maker", movie.get("mv_maker"));

         }

         if (movie.get("au_status") == null) {

            String au_status = "";
            movieMap.put("au_status", au_status);
         } else {

            movieMap.put("au_status", movie.get("au_status"));
         }

         if (movie.get("au_acccnt") == null || movie.get("au_acccnt") == "") {

            int au_acccnt = 0;
            String formattedAuAccnt = formatter.format(au_acccnt);
            movieMap.put("au_acccnt", formattedAuAccnt);
         } else {

            int au_acccnt = (int) movie.get("au_acccnt");
            String formattedAuAccnt = formatter.format(au_acccnt);
            movieMap.put("au_acccnt", formattedAuAccnt);
         }

         if (movie.get("au_acccnt") == null || movie.get("au_acccnt") == "") {

            int au_acccnt = 0;
            String formattedAuAccnt = formatter.format(au_acccnt);
            movieMap.put("au_acccnt", formattedAuAccnt);
         } else {

            int au_acccnt = (int) movie.get("au_acccnt");
            String formattedAuAccnt = formatter.format(au_acccnt);
            movieMap.put("au_acccnt", formattedAuAccnt);
         }

         movieList.add(movieMap);
      }

      model.addAttribute("movieList", movieList);

      model.addAttribute("currentPage", page);
      model.addAttribute("totalPages", totalPages);

      return "admin/movieupload";
   }

   @GetMapping("/movieup")
   public ResponseEntity<String> adminmvuplode() {
      JSONObject json = new JSONObject();
      JSONArray jsonArray = new JSONArray();
      List<Movielist> list = new ArrayList<Movielist>();
      list = adminService.list();

      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      // Date 타입을 문자열로 변환

      for (Movielist movie : list) {
         JSONObject movieJson = new JSONObject();

         movieJson.put("mv_poster", movie.getMv_poster());
         movieJson.put("mv_code", movie.getMv_code());
         movieJson.put("mv_name", movie.getMv_name());
         String formattedDate = dateFormat.format(movie.getMv_sdate());
         // movieJson.put("mv_sdate", movie.getMv_sdate());
         movieJson.put("mv_sdate", formattedDate);
         movieJson.put("mv_maker", movie.getMv_maker());
         movieJson.put("au_status", movie.getAu_status());
         movieJson.put("au_acccnt", movie.getAu_acccnt());

         jsonArray.add(movieJson);

      }

      json.put("jsonlist", jsonArray);

      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "application/json; charset=utf-8");

      return new ResponseEntity<>(json.toString(), headers, HttpStatus.OK);

      // System.out.println(json.toString());

      // return ResponseEntity.ok(json.toString());
      // return ResponseEntity.ok(json.toString());

   }

   @PostMapping("mvupdate")
   public String mvupdate(@RequestParam Map<String, String> map) {

//      System.out.println("저장하기 : " + map);
      String sDate = map.get("mv_sdate");
      String mvSdate = null;
      String mvSdate1 = sDate.replace(" ", "");
      int au_acccnt = 0;
      String acccnt = map.get("au_acccnt");

      String au_status= null;
   String status=map.get("au_status");
      
      if (mvSdate1 != null) {

         

         if (mvSdate1.length() < 10) {

            mvSdate = "0000-00-00";
            map.put("mv_sdate", mvSdate);

         } else {

            map.put("mv_sdate", mvSdate1);

         }

      } else if (mvSdate1== null || mvSdate1.isEmpty()) {

         mvSdate = "0000-00-00"; // 또는 원하는 기본값 설정
         map.put("mv_sdate", mvSdate); // map에 다시 설정된 값 저장

      }

      if (acccnt == null || acccnt.isEmpty() || acccnt == "") {

          au_acccnt = 0;
         String formattedAuAccnt = String.valueOf(au_acccnt);
         map.put("au_acccnt", formattedAuAccnt);
      } else {
          acccnt = (map.get("au_acccnt").replace(",", ""));
          
         map.put("au_acccnt", acccnt);
      }
      
      if (status == null || status.isEmpty()) {

         au_status = "";
         map.put("au_status", au_status);
      }else {
         
         map.put("au_status", status);
      }
      
      
//System.out.println("update부분"+map.toString());
      int result = adminService.mvupdate(map);

      return "redirect:/admin/movieupload";
   }

   @PostMapping("mvdelete")
   public String mvdelete(@RequestParam String mvcode) {

      int result = adminService.mvdelete(mvcode);

//      System.out.println(result);
      return "redirect:/admin/movieupload";
   }

   @GetMapping("mvdetail")
   public String mvdatail(@RequestParam("mvcode") String mvcode, Model model) {

      List<Map<String, Object>> list = adminService.list2(mvcode);
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 날짜포맷용
      List<Map<String, Object>> movieList = new ArrayList<>();
      DecimalFormat formatter = new DecimalFormat("#,###"); // 숫자포맷용

      String mv_maker = "";
      String au_status = "";
      int au_acccnt = 0;

      for (Map<String, Object> movie : list) {
         Map<String, Object> movieMap = new HashMap<>();
         movieMap.put("mv_poster", movie.get("mv_poster"));
         movieMap.put("mv_code", movie.get("mv_code"));
         movieMap.put("mv_name", movie.get("mv_name"));
         movieMap.put("mv_maker", movie.get("mv_maker"));

         movieMap.put("au_status", movie.get("au_status"));

         if (movie.get("mv_sdate") == null) {

            String formattedDate = "0000-00-00";
            movieMap.put("mv_sdate", formattedDate);
         } else {

            String formattedDate = dateFormat.format(movie.get("mv_sdate"));
            movieMap.put("mv_sdate", formattedDate);
         }

         if (movie.get("mv_maker") == null) {

            mv_maker = "";
            movieMap.put("mv_maker", mv_maker);
         }

         if (movie.get("au_status") == null) {

            au_status = "";
            movieMap.put("au_status", au_status);
         }

         if (movie.get("au_acccnt") == null || movie.get("au_acccnt") == "") {

            au_acccnt = 0;
            String formattedAuAccnt = formatter.format(au_acccnt);
            movieMap.put("au_acccnt", formattedAuAccnt);
         } else {

            au_acccnt = (int) movie.get("au_acccnt");
            String formattedAuAccnt = formatter.format(au_acccnt);
            movieMap.put("au_acccnt", formattedAuAccnt);
         }

         movieList.add(movieMap);
      }

      model.addAttribute("movieList", movieList);

//      System.out.println("map" + movieList.toString());

      return "admin/mvdetail";
   }

   @GetMapping("newmovie")
   public String newmovie() {

      return "admin/newmovie";
   }

   @ResponseBody
   @GetMapping("mvserch")
   public int mvserch(@RequestParam String mvname) {

      int result = adminService.mvserch(mvname);

//      System.out.println(result);
      return result;
   }

   @ResponseBody
   @GetMapping("mvinfo")
   public String mvinfo(@RequestParam String mvname) throws Exception {

      String key = "049940f273ce11ffde7abfb717f953a6"; // 영화진흥위원회 깃공개xx

      StringBuilder urlBuilder = new StringBuilder(
            "http://www.kobis.or.kr/kobisopenapi/webservice/" + "rest/movie/searchMovieList.json?");
      // + "key=049940f273ce11ffde7abfb717f953a6&movieNm="+mvname);// 1.영화제목으로검색
      // url세팅
      // 영상진흥원
//전날 박스오피스 조회 ( 전일자 기준 으로 조회)

      urlBuilder.append("&" + URLEncoder.encode("movieNm", "UTF-8") + "=" + URLEncoder.encode(mvname, "UTF-8")); // 영상진흥원

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

      JSONObject data = (JSONObject) result.get("movieListResult");

      // String result1 = adminService.insertMovies(data); // json데이터로 보내기
      JSONArray array = (JSONArray) data.get("movieList");

      String movieCd = "";
      String prdtStatNm = "";
      String openDt = "";
      String movieNm = "";

      for (Object movieObject : array) {
         JSONObject movie = (JSONObject) movieObject;

         String openString = (String) movie.get("openDt"); // 개봉일

         if (openString == null || openString.isEmpty()) {
            openString = "0000-00-00"; // 또는 다른 기본값 설정
         }

         openDt = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(openString));

         prdtStatNm = (String) movie.get("prdtStatNm");
         movieCd = (String) movie.get("movieCd"); // 영화고유넘버
         movieNm = (String) movie.get("movieNm"); // 영화제목

      }

      JSONObject json = new JSONObject();
      json.put("movieCd", movieCd);
      json.put("prdtStatNm", prdtStatNm);
      json.put("openDt", openDt);
      json.put("movieNm", movieNm);

      return json.toString();
   }
   
   @PostMapping("movieinsert")
   public String movieinsert(@RequestParam Map<String, String> map) throws IOException, ParseException {

      String sDate = map.get("mv_sdate");
      String mvSdate = "";
      String mv_maker = "";
      String moviecode = map.get("mv_code");
      

      String au_status = null;
      int au_acccnt = 0;
      String acccnt = "";

      if (sDate != null) {

         mvSdate = sDate.replace(" ", "");

         if (mvSdate.length() < 10) {

            mvSdate = "0000-00-00";
            map.put("mv_sdate", mvSdate);

         } else {

            map.put("mv_sdate", mvSdate);

         }

      } else if (sDate == null || sDate.isEmpty()) {

         mvSdate = "0000-00-00"; // 또는 원하는 기본값 설정
         map.put("mv_sdate", mvSdate); // map에 다시 설정된 값 저장

      }

      if (map.get("mv_maker") == null) {

         mv_maker = "";
         map.put("mv_maker", mv_maker);
      }

      if (map.get("au_status") == null) {

         au_status = "";
         map.put("au_status", au_status);
      }

      if (map.get("au_acccnt") == null || map.get("au_acccnt") == "") {

         au_acccnt = 0;
         String formattedAuAccnt = String.valueOf(au_acccnt);
         map.put("au_acccnt", formattedAuAccnt);
      } else {
         acccnt = map.get("au_acccnt");

         map.put("au_acccnt", acccnt);
      }

      int result=adminService.movieinsert(map);
   
      // System.out.println(map);
      return "redirect:/admin/movieupload";
         
         
      }
      
      

      

   

   @PostMapping("mvcodeck")
   @ResponseBody
   public int adminmvcodeck(@RequestParam String mvcode) throws IOException, ParseException {
      String result = adminService.mvcodeck(mvcode);
      int result1 = Integer.parseInt(result);
//      System.out.println("result1" + result1);

      return result1;

   }


   
   
   @GetMapping("mvinfoupdate")
   public String mvinfoupdate(@RequestParam String mvcode, @RequestParam String mvname) throws Exception {


         String key = "049940f273ce11ffde7abfb717f953a6"; // 영화진흥위원회
         String moviecode = mvcode;
         
         StringBuilder urlBuilder = new StringBuilder(
               "http://www.kobis.or.kr/" + "kobisopenapi/webservice/rest/movie/" + "searchMovieInfo.json?"); // 영화상세1

         urlBuilder.append(URLEncoder.encode("key", "UTF-8") + "=" + key);
         urlBuilder.append("&" + URLEncoder.encode("movieCd", "UTF-8") + "=" + URLEncoder.encode(moviecode, "UTF-8"));

         URL url = new URL(urlBuilder.toString());

         HttpURLConnection conn = (HttpURLConnection) url.openConnection();

         conn.setRequestMethod("GET");

         conn.setRequestProperty("Content-type", "application/json");

//         System.out.println("Response code: " + conn.getResponseCode());

         BufferedReader rd;

         if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {

            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

         } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
         }

         StringBuilder sb = new StringBuilder();

         String line = rd.readLine();

         conn.disconnect();

//         System.out.println("line : " + line);
         JSONObject resultjs = null;
         JSONParser jsonParser = new JSONParser();

         resultjs = (JSONObject) jsonParser.parse(line);

         JSONObject data = (JSONObject) resultjs.get("movieInfoResult");

         int result2 = adminService.infoupdate(data); // movieupdate 업데이트로!!
         String result3 = String.valueOf(result2);
//         System.out.println("result2" + result2);
         
         if(result2 ==1) {
            
             key = "8C387S782DIS3443795I"; // kmdb
            String releaseDts = "20230101";

            String movieNm = mvname;
             urlBuilder = new StringBuilder(
                     "http://api.koreafilm.or.kr/" + "openapi-data2/wisenut/search_api/search_json2.jsp?");

               urlBuilder.append(
                     "&" + URLEncoder.encode("collection", "UTF-8") + "=" + URLEncoder.encode("kmdb_new2", "UTF-8"));
               urlBuilder.append(
                     "&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(movieNm, "UTF-8")); /* 통합검색 */ // kmdb
               urlBuilder.append(
                     "&" + URLEncoder.encode("releaseDts", "UTF-8") + "=" + URLEncoder.encode(releaseDts, "UTF-8")); // 개봉시작일
                                                                                             // //kmdb
               urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8"));

                url = new URL(urlBuilder.toString());

                conn = (HttpURLConnection) url.openConnection();

               conn.setRequestMethod("GET");

               conn.setRequestProperty("Content-type", "application/json");

//               System.out.println("Response code: " + conn.getResponseCode());

               BufferedReader rd1;

               if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {

                  rd1 = new BufferedReader(new InputStreamReader(conn.getInputStream()));

               } else {
                  rd1 = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
               }

                sb = new StringBuilder();
                line = "";

               while ((line = rd1.readLine()) != null) {
                  sb.append(line);
               }

               String response = sb.toString();

               conn.disconnect();

//               System.out.println("response : " + response);
               JSONObject result = null;
               jsonParser = new JSONParser();

               try {

                  result = (JSONObject) jsonParser.parse(response);

                  adminService.kmdbupdate(result, movieNm);

               } catch (Exception e) {

                  daterepeat(movieNm);

               }

            }

            
      
      
      return "admin/movieupload";
   }

   
   public String daterepeat(String movieNm) throws Exception {

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

//      System.out.println("Response code: " + conn.getResponseCode());

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

//      System.out.println("response : " + response);
      JSONObject result = null;
      JSONParser jsonParser = new JSONParser();

      result = (JSONObject) jsonParser.parse(response);

      adminService.kmdbupdate(result, movieNm);

      return "admin/movieupload";
   }

@SuppressWarnings("unchecked")
   @GetMapping("mainserch")
   @ResponseBody
   public String mainserch(@RequestParam String movieNm) {
      JSONObject json = new JSONObject();
      if (movieNm.isEmpty()) {
         // 검색어가 비어있을 때 원하는 처리를 수행하거나 에러 메시지를 반환합니다.
         json.put("error", "검색어를 입력하세요");

      } else {

         String mvname = movieNm.replace(" ", "").toLowerCase();
         List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

         try {

            results = adminService.mainserch(mvname);
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

            for (Map<String, Object> result : results) {
               Object sdateObject = result.get("mv_sdate");
               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
               Object acccnt= result.get("au_acccnt");
               DecimalFormat formatter = new DecimalFormat("#,###"); // 숫자포맷용
               String formattedAuAccnt = formatter.format(acccnt);
               // 날짜를 문자열로 변환
               String sdateString = dateFormat.format(sdateObject);
               result.put("mv_sdate", sdateString);
               result.put("au_acccnt", formattedAuAccnt);
               resultList.add(result);
            }

            json.put("results", resultList);

         } catch (Exception e) {

//            System.out.println("오류발생");
            json.put("error1", "오류 발생");
         }

      

//         System.out.println(json.toString());

         // if (sdateObject instanceof String) {

      }

      return json.toString();
   }

   
}