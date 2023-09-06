package com.movie.web.pay;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayDAO {

	
	List<Map<String, Object>> couponList();

	Map<Integer, Object> havePoint();

	void updatePoint(Map<String, Object> map);

	Map<Integer, Object> ticketPrice();

	int couponChk(String cCode);

	int couDiscount(String selectCoupon);
	
	void couponUpdate(Map<String, Object> map);

	Map<String, Object> charge(String cardSelect);




}
