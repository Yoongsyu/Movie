package com.movie.web.reseat;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReseatDAO {

	List<Map<String, Object>> seatnum(String ms_idx);

	int reserve(Map<String, Object> val);

	int reservetwo(Map<String, Object> val);

	Map<String, Object> movieschedule(String ms_idx);

	void finreservation(Map<String, Object> params);









}
