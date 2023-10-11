package com.movie.web.mypage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {
	
	@Autowired
	private MyPageDAO mypageDAO;

	public Map<String, Object> mypage(int mno) {
		return mypageDAO.mypage(mno);
	}

	public List<Map<String, Object>> couponList(int mno) {
		return mypageDAO.couponList(mno);
	}

	public int couponChk(String cCode) {
		return mypageDAO.couponChk(cCode);
	}

	public void couponUpdate(Map<String, Object> map) {
		mypageDAO.couponUpdate(map);
	}

	  public int admChk(String cCode) {
	      return mypageDAO.admChk(cCode);
	   }

	   public void admUpdate(Map<String, Object> map) {
		   mypageDAO.admUpdate(map);
	   }

	public void updateNickname(Map<String, Object> map) {
		mypageDAO.updateNickname(map);
	}

	
	
}
