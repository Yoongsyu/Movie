package com.movie.web.pay;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PayDAO {


   List<Map<String, Object>> couponList(int mno);

   Map<Integer, Object> havePoint(int mno);

   void updatePoint(Map<String, Object> map);

   int couponChk(String cCode);

   void couponUpdate(Map<String, Object> map);

   int admChk(String cCode);

   void admUpdate(Map<String, Object> map);

   
   List<Map<String, Object>> charge(String cardSelect);

     int cardChk(int mno);

     List<Map<String, Object>> cardList(int mno);

   void cardUpdate(Map<String, Object> map);

   int couDiscount(String selectCoupon);

   Map<String, Object> ticketInfo(Map<String, Object> info);

	Map<String, Object> email(int mno);

	void rsData(Map<String, Object> rsData);

	void updateSeat(@Param("msIdx") int msIdx, @Param("seat") String seat);

	void admDelete(List<String> selectAdm);

	void couDelete(List<String> selectCouponList);

	String rsNumber();

	void record(Map<String, Object> map);   


     
}