package com.movie.web.pay;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TicketListDAO {

	List<Map<String, Object>> ticketList(int mno);

	
	
}
