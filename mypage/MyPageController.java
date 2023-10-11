package com.movie.web.mypage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyPageController {
	@Autowired
	private MyPageService mypageService;
	
	@GetMapping("/mypage")
	public String mypage(Model model, HttpSession session) {
		Integer mno = (Integer) session.getAttribute("m_no");
		
		if(mno != null) {
			
			Map<String, Object> myInfo = mypageService.mypage(mno);
			model.addAttribute("myInfo", myInfo);
			
			 List<Map<String, Object>> couponList = mypageService.couponList(mno);
		      model.addAttribute("couponList", couponList);
			
		      
		      
			return "/mypage";
			
			
		} else {
		
			return "redirect:/login";
		}
	}
	
	 @ResponseBody
	   @PostMapping("/couponChk2")
	   public String couponChk2(@RequestParam("cCode") String cCode, HttpSession session,
	         Map<String, Object> map) {
	      int result = mypageService.couponChk(cCode);
	      JSONObject json = new JSONObject();
	      json.put("result", result);

	      if (result == 1) {
	    	  Integer mno = (Integer) session.getAttribute("m_no");
	         map.put("mno", mno);
	         map.put("cCode", cCode);
	         mypageService.couponUpdate(map);
	      }

	      return json.toString();

	   }
	
	 @ResponseBody
	   @PostMapping("/admChk2")
	   public String admChk2(@RequestParam("aCode") String aCode, HttpSession session,
	         Map<String, Object> map) {
	      int result = mypageService.admChk(aCode);
	      JSONObject json4 = new JSONObject();
	      json4.put("result", result);

	      if (result == 1) {
	    	  Integer mno = (Integer) session.getAttribute("m_no");
	         map.put("mno", mno);
	         map.put("aCode", aCode);
	         mypageService.admUpdate(map);
	      }

	      return json4.toString();

	   }
	 
	 
	 @ResponseBody
	 @PostMapping("/updateNickname")
	 public void updateNickname(@RequestParam("nickName") String nickName, HttpSession session) {
		 
		 Integer mno = (Integer) session.getAttribute("m_no");

		 Map<String, Object> map = new HashMap<>();
         map.put("mno", mno);
         map.put("nickName", nickName);
		mypageService.updateNickname(map);

	 }
	 
	
	
}
