package com.movie.web.mselect;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MselectDAO {

	List<String> movie();

	List<Map<String, Object>> theater();

	Map<String, Object> selmovielist(String selmovie);

	List<Map<String, Object>> selcitylist(String selarea);

	List<Map<String, Object>> imaxlist(String selimax);

	String selspecial(String selectimax);

	List<Map<String, Object>> movietime(Map<String, Object> map);

	List<Map<String, Object>> specialtime(Map<String, Object> map);

	List<Map<String, Object>> mlist();

	Map<String, Object> mv_code(String mv_code);

}
