package com.movie.web.schedule;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleDAO {

	List<Map<String, Object>> schedule1();
	List<Map<String, Object>> schedule2();
	List<Map<String, Object>> schedule3();
	List<Map<String, Object>> schedule4();
	List<Map<String, Object>> schedule5();
	List<Map<String, Object>> schedule6();
	List<Map<String, Object>> schedule7();
	List<Map<String, Object>> schedule8();
	List<Map<String, Object>> schedule9();
	List<Map<String, Object>> schedule10();

	List<Map<String, Object>> scheduleinfo();

} 
