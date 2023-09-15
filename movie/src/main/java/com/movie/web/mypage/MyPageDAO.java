package com.movie.web.mypage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageDAO {

	Map<String, Object> mypage();

	List<Map<String, Object>> couponList(String mno);

	int couponChk(String cCode);

	void couponChk(Map<String, Object> map);

}
