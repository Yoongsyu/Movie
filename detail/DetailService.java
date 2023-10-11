package com.movie.web.detail;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailService {
	@Autowired
	private DetailDAO detailDAO;

	public Map<String, Object> detail(String mv_code) {
		return detailDAO.detail(mv_code);
	}
	
	public String movie(String mv_name) {
		return detailDAO.movie(mv_name);
	}
}
