package com.movie.web.schedule;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
	@Autowired
	private ScheduleDAO schedulelDAO; 

	public List<Map<String, Object>> schedule1() {
		return schedulelDAO.schedule1(); 
	}
	public List<Map<String, Object>> schedule2() {
		return schedulelDAO.schedule2(); 
	}
	public List<Map<String, Object>> schedule3() {
		return schedulelDAO.schedule3(); 
	}
	public List<Map<String, Object>> schedule4() {
		return schedulelDAO.schedule4(); 
	}
	public List<Map<String, Object>> schedule5() {
		return schedulelDAO.schedule5(); 
	}
	public List<Map<String, Object>> schedule6() {
		return schedulelDAO.schedule6(); 
	}
	public List<Map<String, Object>> schedule7() {
		return schedulelDAO.schedule7(); 
	}
	public List<Map<String, Object>> schedule8() {
		return schedulelDAO.schedule8(); 
	}
	public List<Map<String, Object>> schedule9() {
		return schedulelDAO.schedule9(); 
	}
	public List<Map<String, Object>> schedule10() {
		return schedulelDAO.schedule10(); 
	}

	public List<Map<String, Object>> scheduleinfo() {
		return schedulelDAO.scheduleinfo();
	}


	
}
