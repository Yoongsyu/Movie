package com.movie.web.pay;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface TicketDAO {



	//Map<String, Object> ticketInfo(int msNum);

	Map<String, Object> ticketInfo(Map<String, Object> info);

	String rsNumber();

	void updateSeat2(@Param("msIdx") int msIdx, @Param("seat") String seat);

	void updateRsNum(Map<String, Object> map);

	

	
	
}
