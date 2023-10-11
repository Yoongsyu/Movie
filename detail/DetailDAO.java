package com.movie.web.detail;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetailDAO {

	Map<String, Object> detail(String mv_code);
	String movie(String mv_name);
}
