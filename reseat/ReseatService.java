package com.movie.web.reseat;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReseatService {
	@Autowired
	private ReseatDAO reseatDAO;

	public List<Map<String, Object>> seatnum(String ms_idx) {
		return reseatDAO.seatnum(ms_idx);
	}

	public int reserve(Map<String, Object> val) {
		return reseatDAO.reserve(val);
	}
	public int reservetwo(Map<String, Object> val) {
		return reseatDAO.reservetwo(val);
	}


	public Map<String, Object> movieschedule(String ms_idx) {
		return reseatDAO.movieschedule(ms_idx);
	}

	public void finreservation(Map<String, Object> params) {
		reseatDAO.finreservation(params);
		
	}











	

 
}
