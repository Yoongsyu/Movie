package com.movie.web.mselect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MselectController {
	@Autowired
	private MselectService mselectService;
	
	@GetMapping("/reservation")
	public String mselect(Model model, @RequestParam(name = "mv_code", required = true, defaultValue = "") String pmv_code) {
		
		
		model.addAttribute("pmv_code", pmv_code);
		
		// 영화 이름 뽑아내기
		List<String> movielist = mselectService.movie();
		JSONObject json = new JSONObject();
		json.put("movielist", movielist);
		model.addAttribute("movielist", movielist);// mv_name

		// 극장 뽑아내기
		List<Map<String, Object>> theaterlist = mselectService.theater();
		model.addAttribute("theaterlist", theaterlist);// seoul, daejeon, gangwon, busan, gwangju, th_area
		model.addAttribute("theaterlist_length", theaterlist.size());
		
		return "reservation";
			
	}
	
	@ResponseBody
	@PostMapping("/mv_code")
	public String mselect(@RequestParam(name = "mv_code", required = false) String mv_code) {
		Map<String, Object> j_movielist = mselectService.mv_code(mv_code);
		JSONObject json = new JSONObject();
		json.put("j_movielist", j_movielist);
		
		return json.toString();
	}
	
	//영화 선택
	@ResponseBody
	@PostMapping("/selmovie")
	public String selmovie(@RequestParam("selmovie") String selmovie) {
		// 선택한 영화에 맞는 정보 가져오기
		Map<String, Object> selmovielist = mselectService.selmovielist(selmovie); 
		// mv_idx, mv_name, mv_grade, mv_poster, mv_sdate, mv_runtime, mv_rate, mv_genre, mv_gradeimg
		
		JSONObject json = new JSONObject();
		json.put("movielist", selmovielist);
		
		return json.toString();
	}
	
	// 극장 선택
	@ResponseBody
	@PostMapping("/seltheater")
	public String seltheater() {
		List<Map<String, Object>> tlist = mselectService.theater();
//		System.out.println(tlist);//[서울, 경기/인천, 강원, 대전/충청, 부산/대구/경상, 광주/전라]
		JSONObject json = new JSONObject();
		json.put("tlist", tlist);
		return json.toString();
	}
	
	// 지역 선택
	@ResponseBody
	@PostMapping("/selarea")
	public String selarea(@RequestParam("selarea") String selarea) {
		List<Map<String, Object>> selcitylist = mselectService.selcitylist(selarea);
		JSONObject json = new JSONObject();
		json.put("selcitylist", selcitylist);//th_city
		return json.toString();
	}
	
	// 특별관 선택
	@ResponseBody
	@PostMapping("/selimax")
	public String selimax(@RequestParam("selimax") String selimax) {
		List<Map<String, Object>> imaxlist = mselectService.imaxlist(selimax);
		JSONObject json = new JSONObject();
		json.put("imaxlist", imaxlist);// th_city
		return json.toString();
	}
	
	// 특별관 지역 뽑아오기
	@ResponseBody
	@PostMapping("/selspecial")
	public String selspecial(@RequestParam("selectimax") String selectimax) {
		String selspecial = mselectService.selspecial(selectimax);
		JSONObject json = new JSONObject();
		json.put("selspecial", selspecial);
		
		return json.toString();		
	}
	
	// 시간표 뽑아오기
	@ResponseBody
	@PostMapping("/timetable")
	public String timetable(@RequestParam("selFilm") String selFilm, @RequestParam("selCity") String selCity,
		    @RequestParam(value = "selKind", required = false) String selKind) {
		
		Map<String, Object> map = new HashMap<>();
		
		// 영화 제목
		map.put("selFilm", selFilm);
		
		// 도시
		map.put("selCity", selCity);
		
		// 상영관 종류
		map.put("selKind", selKind);
		
		JSONObject json = new JSONObject();
		
		List<Map<String, Object>> movietime = mselectService.movietime(map);
		List<Map<String, Object>> specialtime = mselectService.specialtime(map);

		json.put("movietime", movietime);
		json.put("specialtime", specialtime);
		System.out.println(json.toString());
//{"movietime":[{"mv_name":"잠","ms_stime":"10:20","ms_idx":2,"ms_combine":11,"ms_etime":"11:54","th_kind":1,"countseat":68,"mv_idx":1,"th_seatcnt":88},{"mv_name":"잠","ms_stime":"11:30","ms_idx":5,"ms_combine":12,"ms_etime":"13:04","th_kind":2,"countseat":103,"mv_idx":1,"th_seatcnt":108},{"mv_name":"잠","ms_stime":"12:40","ms_idx":14,"ms_combine":14,"ms_etime":"14:14","th_kind":4,"countseat":139,"mv_idx":1,"th_seatcnt":154}]}
		return json.toString();
	}
	
	@PostMapping("/reservation")
	public String mselect(@RequestParam Map<String, Object> map) {
		//System.out.println(map);
		//{final_date=2023914, final_th_kind=4, final_ms_idx=14}
		String date = (String) map.get("final_date");
		String th_kind = (String) map.get("final_th_kind");
		String ms_idx = (String) map.get("final_ms_idx");
		
		if (th_kind.equals("1")) {
			return "redirect:/reseat1?ms_idx="+ ms_idx+"&date="+date;
		} else if (th_kind.equals("2")) {
			return "redirect:/reseat2?ms_idx="+ ms_idx+"&date="+date;
		} else if (th_kind.equals("3")) {
			return "redirect:/reseat3?ms_idx="+ ms_idx+"&date="+date;
		} else  if (th_kind.equals("4")) {
			return "redirect:/reseat4?ms_idx="+ ms_idx+"&date="+date;
		} else {
			return "redirect:/reservation";
		}
	}
	
	@ResponseBody
	@GetMapping("/menu")
	public String mlist() {
		List<Map<String, Object>> mlist = mselectService.mlist();
		JSONObject json = new JSONObject();
		json.put("mlist", mlist);
		return json.toString();
	}

}

