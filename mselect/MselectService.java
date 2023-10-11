package com.movie.web.mselect;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MselectService {
	@Autowired
	private MselectDAO mselectDAO;

	public List<String> movie() {
		return mselectDAO.movie();
	}

	public List<Map<String, Object>> theater() {
		return mselectDAO.theater();
	}

	public Map<String, Object> selmovielist(String selmovie) {
		return mselectDAO.selmovielist(selmovie);
	}
	
	public List<Map<String, Object>> selcitylist(String selarea) {
		return mselectDAO.selcitylist(selarea);
	}

	public List<Map<String, Object>> imaxlist(String selimax) {
		return mselectDAO.imaxlist(selimax);
	}

	public String selspecial(String selectimax) {
		return mselectDAO.selspecial(selectimax);
	}

	public List<Map<String, Object>> movietime(Map<String, Object> map) {
		return mselectDAO.movietime(map);
	}

	public List<Map<String, Object>> specialtime(Map<String, Object> map) {
		return mselectDAO.specialtime(map);
	}

	public List<Map<String, Object>> mlist() {
		return mselectDAO.mlist();
	}

	public Map<String, Object> mv_code(String mv_code) {
		return mselectDAO.mv_code(mv_code);
	}

	
}
