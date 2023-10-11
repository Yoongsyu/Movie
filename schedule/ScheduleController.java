package com.movie.web.schedule;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	@GetMapping("/schedule")
	public String schedule(Model model) {
		List<Map<String, Object>> schedule1 = scheduleService.schedule1(); 
		List<Map<String, Object>> schedule2 = scheduleService.schedule2(); 
		List<Map<String, Object>> schedule3 = scheduleService.schedule3(); 
		List<Map<String, Object>> schedule4 = scheduleService.schedule4(); 
		List<Map<String, Object>> schedule5 = scheduleService.schedule5(); 
		List<Map<String, Object>> schedule6 = scheduleService.schedule6(); 
		List<Map<String, Object>> schedule7 = scheduleService.schedule7(); 
		List<Map<String, Object>> schedule8 = scheduleService.schedule8(); 
		List<Map<String, Object>> schedule9 = scheduleService.schedule9(); 
		List<Map<String, Object>> schedule10 = scheduleService.schedule10(); 
		List<Map<String, Object>> scheduleinfo = scheduleService.scheduleinfo(); 
		System.out.println(schedule1);     
		System.out.println(scheduleinfo); 
		model.addAttribute("schedule1", schedule1);
		model.addAttribute("schedule2", schedule2);
		model.addAttribute("schedule3", schedule3);
		model.addAttribute("schedule4", schedule4);
		model.addAttribute("schedule5", schedule5);
		model.addAttribute("schedule6", schedule6);
		model.addAttribute("schedule7", schedule7);
		model.addAttribute("schedule8", schedule8);
		model.addAttribute("schedule9", schedule9);
		model.addAttribute("schedule10", schedule10);
		model.addAttribute("scheduleinfo", scheduleinfo); 
	 
	    return "/schedule"; 
	}
	
	@PostMapping("schedule")
	public String schedule(@RequestParam("mv_name") String mv_name) {
		return "/schedule";
		
	}
	
}
