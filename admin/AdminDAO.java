package com.movie.web.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDAO {


	List<Map<String, Object>> memberList();

	int gradeChange(Map<String, String> map);

	Map<String, Object> userpage(int mno);

	int nicknameChange(Map<String, Object> map);

	void pointChange(Map<String, Object> map);

	Map<String, Object> adminLogin(Map<String, Object> map);

	List<Map<String, Object>> memberSearch(Map<String, Object> map);

	String totalMember();

	String totalIncome();
	
	
	List<Movielist> list();

	List<Map<String, Object>> list1();

	int mvupdate(String mv_code);

	int mvdelete(String mvcode);

	List<Map<String, Object>> list2(String mvcode);

	int mvupdate(Map<String, String> map);

	void mvupdate1(Map<String, String> map);

	int mvserch(String mvname);

	void movieinsert(Map<String, String> map);

	int movieinsert1(Map<String, String> map);

	void mvdelete1(String mvcode);

	String mvcodeck(String mvcode);

	int infoupdate(Map<String, Object> movieMap);

	String poster(String movieNm);

	String dbvod(String movieNm);

	String dbplot(String movieNm);

	void kmdbupdate(Map<String, Object> movieMap);

	void kmdbupdate1(Map<String, Object> movieMap);

	List<Map<String, Object>> mainserch(String mvname);

	void insertrate();







}
