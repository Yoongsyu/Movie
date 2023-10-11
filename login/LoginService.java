package com.movie.web.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	@Autowired
	private LoginDAO loginDAO;

	public Map<String, Object> login(Map<String, Object> map) {
		return loginDAO.login(map);
	}

	public Map<String, Object> naverConnectionCheck(Map<String, Object> apiJson) {
		return loginDAO.naverConnectionCheck(apiJson);
	}

	public Map<String, Object> userNaverLoginPro(Map<String, Object> apiJson) {
		return loginDAO.userNaverLoginPro(apiJson);
	}

	public Integer userNaverRegisterPro(Map<String, Object> paramMap) {
		System.out.println("연결확인"+paramMap);
		return loginDAO.userNaverRegisterPro(paramMap);
	}

	public Map<String, Object> findId(String findid) {
		return loginDAO.findId(findid);
	}

	public Map<String, Object> findPw(Map<String, Object> map) {
		return loginDAO.findPw(map);
	}

	public int temporaryPw(Map<String, Object> result) {
		return loginDAO.temporaryPw(result);
		
	}



}

