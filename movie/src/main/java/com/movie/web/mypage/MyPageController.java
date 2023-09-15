package com.movie.web.mypage;

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
		 String mno = (String) session.getAttribute("mno");
		
		Map<String, Object> myInfo = mypageService.mypage();
		model.addAttribute("myInfo", myInfo);
		
		 List<Map<String, Object>> couponList = mypageService.couponList(mno);
	      model.addAttribute("couponList", couponList);
		
		return "/mypage";
	}
	
	 @ResponseBody
	   @PostMapping("/couponChk2")
	   public String couponChk2(@RequestParam("cCode") String cCode, HttpSession session,
	         @RequestParam Map<String, Object> map) {
	      int result = mypageService.couponChk(cCode);
	      JSONObject json = new JSONObject();
	      json.put("result", result);

	      if (result == 1) {
	         String mno = (String) session.getAttribute("mno");
	         map.put("mno", mno);
	         map.put("cCode", cCode);
	         mypageService.couponUpdate(map);
	      }

	      return json.toString();

	   }
	
	
	
}
