package com.movie.web.pay;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketListService {

	@Autowired
	TicketListDAO ticketListDAO;
	

	public List<Map<String, Object>> ticketList(int mno) {
		return ticketListDAO.ticketList(mno);
	}

	
}
