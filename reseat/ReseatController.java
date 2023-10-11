package com.movie.web.reseat;

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
public class ReseatController {
	@Autowired
	private ReseatService reseatService;
	
	
	
	@GetMapping("/reseat1")
	public String reseat1(Model model, @RequestParam("ms_idx") String ms_idx, @RequestParam("date") String date) {
		List<Map<String, Object>> slist = reseatService.seatnum(ms_idx);
		Map<String, Object> movieschedule = reseatService.movieschedule(ms_idx);
		model.addAttribute("slist", slist);
		model.addAttribute("movieschedule", movieschedule);
		model.addAttribute("ms_idx", ms_idx);
		model.addAttribute("date", date);
		return "/reseat1";
	
	}
	@PostMapping("/reseat1")
	public String reseat1(@RequestParam("list") List<String> list, @RequestParam("ms_idx") String ms_idx, @RequestParam Map<String, Object> info) {
		System.out.println(info);
		Map<String, Object> params = new HashMap<>();
	    params.put("list", list);
	    params.put("ms_idx", ms_idx);
	    reseatService.finreservation(params);
	    
	    return "redirect:/pay?ms_idx=" + ms_idx + "&adult="+ info.get("adult") + "&youth=" + info.get("youth")+ "&special=" + info.get("special")+"&list=" + info.get("list")+"&date="+info.get("date");
	}

	
	@GetMapping("/reseat2")
	public String reseat2(Model model, @RequestParam("ms_idx") String ms_idx, @RequestParam("date") String date) {
		List<Map<String, Object>> slist = reseatService.seatnum(ms_idx);
		Map<String, Object> movieschedule = reseatService.movieschedule(ms_idx);
		
		model.addAttribute("slist", slist);
		model.addAttribute("movieschedule", movieschedule);
		model.addAttribute("ms_idx", ms_idx);
		model.addAttribute("date", date);
		return "/reseat2";
	}
	
	@PostMapping("/reseat2")
	public String reseat2(@RequestParam("list") List<String> list, @RequestParam("ms_idx") String ms_idx, @RequestParam Map<String, Object> info) {
		Map<String, Object> params = new HashMap<>();
	    params.put("list", list);
	    params.put("ms_idx", ms_idx);
	    reseatService.finreservation(params);
	    
	    return "redirect:/pay?ms_idx=" + ms_idx + "&adult="+ info.get("adult") + "&youth=" + info.get("youth")+ "&special=" + info.get("special")+"&list=" + info.get("list")+"&date="+info.get("date");
	}

	
	@GetMapping("/reseat3")
	public String reseat3(Model model, @RequestParam("ms_idx") String ms_idx, @RequestParam("date") String date) {
		List<Map<String, Object>> slist = reseatService.seatnum(ms_idx);
		Map<String, Object> movieschedule = reseatService.movieschedule(ms_idx);
		
		model.addAttribute("slist", slist);
		model.addAttribute("movieschedule", movieschedule);
		model.addAttribute("ms_idx", ms_idx);
		model.addAttribute("date", date);
		return "/reseat3";
		
	}
	@PostMapping("/reseat3")
	public String reseat3(@RequestParam("list") List<String> list, @RequestParam("ms_idx") String ms_idx, @RequestParam Map<String, Object> info) {
		Map<String, Object> params = new HashMap<>();
	    params.put("list", list);
	    params.put("ms_idx", ms_idx);
	    reseatService.finreservation(params);
	    
	    return "redirect:/pay?ms_idx=" + ms_idx + "&adult="+ info.get("adult") + "&youth=" + info.get("youth")+ "&special=" + info.get("special")+"&list=" + info.get("list")+"&date="+info.get("date");
	}
	
	@GetMapping("/reseat4")
	public String reseat4(Model model, @RequestParam("ms_idx") String ms_idx, @RequestParam("date") String date) {
		List<Map<String, Object>> slist = reseatService.seatnum(ms_idx);
		Map<String, Object> movieschedule = reseatService.movieschedule(ms_idx);
		
		model.addAttribute("slist", slist);
		model.addAttribute("movieschedule", movieschedule);
		model.addAttribute("ms_idx", ms_idx);
		model.addAttribute("date", date);
		return "/reseat4";
		
	}
	@PostMapping("/reseat4")
	public String reseat4(@RequestParam("list") List<String> list, @RequestParam("ms_idx") String ms_idx, @RequestParam Map<String, Object> info) {
		Map<String, Object> params = new HashMap<>();
	    params.put("list", list);
	    params.put("ms_idx", ms_idx);
	    reseatService.finreservation(params);
	    
	    return "redirect:/pay?ms_idx=" + ms_idx + "&adult="+ info.get("adult") + "&youth=" + info.get("youth")+ "&special=" + info.get("special")+"&list=" + info.get("list")+"&date="+info.get("date");

	}
	
	
	
	@ResponseBody
	@GetMapping("/occupied")
	public String seatreservation(@RequestParam Map<String, Object> val) {
		
		
		int reserved = reseatService.reserve(val);
		
		
		
		int reservedtwo = reseatService.reservetwo(val);
		
		
		JSONObject json = new JSONObject();
		json.put("reserved", reserved);
		json.put("reservedtwo", reservedtwo);
		
		return json.toString();
	}
	
}
