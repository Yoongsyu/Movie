package com.movie.web.join;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
	@Autowired
	private JoinDAO joinDAO;

	public int join(JoinDTO joinDTO) {
		//생일을 합치기
	    String birth = joinDTO.getBirthYear()+"-"+joinDTO.getBirthMonth()+"-"+joinDTO.getBirthDay();
	    joinDTO.setBirth(birth);
	    System.out.println(birth);
		
		return joinDAO.join(joinDTO);
	}

	public int overlapCheck(String value, String valueType) {
		Map<String, String> map = new HashMap<>();
		map.put("value", value);
		map.put("valueType", valueType);
		return joinDAO.overlapCheck(map);
	}
}
