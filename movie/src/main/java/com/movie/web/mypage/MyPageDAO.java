package com.movie.web.mypage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageDAO {

	Map<String, Object> mypage(int mno);

	List<Map<String, Object>> couponList(int mno);

	int couponChk(String cCode);

	void couponUpdate(Map<String, Object> map);
	
	 int admChk(String cCode);

	   void admUpdate(Map<String, Object> map);

	void updateNickname(Map<String, Object> map);



}
