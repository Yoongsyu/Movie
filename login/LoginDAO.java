package com.movie.web.login;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginDAO {

	Map<String, Object> login(Map<String, Object> map);

	Map<String, Object> naverConnectionCheck(Map<String, Object> apiJson);

	Map<String, Object> userNaverLoginPro(Map<String, Object> apiJson);

	Integer userNaverRegisterPro(Map<String, Object> paramMap);

	Map<String, Object> findId(String findid);

	Map<String, Object> findPw(Map<String, Object> map);

	int temporaryPw(Map<String, Object> result);


}
