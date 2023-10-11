package com.movie.web.pay;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

	@Autowired
	TicketDAO ticketDAO;
	

	public Map<String, Object> ticketInfo(String rsNum) {
		return ticketDAO.ticketInfo(rsNum);
	}

	public String rsNumber(Map<String, Object> info) {
		return ticketDAO.rsNumber(info);
	}

	public void updateSeat2(Map<String, Object> map) {
		
		 String list = (String) map.get("list"); // "G5,G6,F5,F6,G7"
	        String msIdxx = (String) map.get("msIdx");
	        int msIdx = Integer.parseInt(msIdxx);
	
	        
	        //좌석 목록을 분리
	        String[] seats = list.split(",");

	        for (String seat : seats) {
	        	 ticketDAO.updateSeat2(msIdx, seat);

	}

	
	}

	public void updateRsNum(Map<String, Object> map) {
		ticketDAO.updateRsNum(map);
	}

	public Map<String, Object> saveUsePoint(String rsNum) {
		return ticketDAO.saveUsePoint(rsNum);
	}

	public void deductPoint(Map<String, Object> map) {
		ticketDAO.deductPoint(map);
		
	}

	public void deletePay(String rsNum) {
		ticketDAO.deletePay(rsNum);		
	}
	
	
}
