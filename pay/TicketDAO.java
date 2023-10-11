package com.movie.web.pay;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TicketDAO {



	Map<String, Object> ticketInfo(String rsNum);

	String rsNumber(Map<String, Object> info);

	void updateSeat2(@Param("msIdx") int msIdx, @Param("seat") String seat);

	void updateRsNum(Map<String, Object> map);

	Map<String, Object> saveUsePoint(String rsNum);

	void deductPoint(Map<String, Object> map);

	void deletePay(String rsNum);

	

	
	
}
