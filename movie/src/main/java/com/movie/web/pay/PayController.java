package com.movie.web.pay;

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
public class PayController {
	@Autowired
	private PayService payService;

	@GetMapping("/pay")
	public String pay(Model model) {

		List<Map<String, Object>> couponList = payService.couponList();
		model.addAttribute("couponList", couponList);

		Map<Integer, Object> havePoint = payService.havePoint();
		model.addAttribute("havePoint", havePoint);

		Map<Integer, Object> ticketPrice = payService.ticketPrice();
		model.addAttribute("ticketPrice", ticketPrice);

		return "/pay";

	}

	@PostMapping("/pay")
	public String pay() {

		return "redirect:/pay";
	}

	@ResponseBody
	@PostMapping("/couponChk")
	public String couponChk(@RequestParam("cCode") String cCode, HttpSession session,
			@RequestParam Map<String, Object> map) {
		int result = payService.couponChk(cCode);
		JSONObject json = new JSONObject();
		json.put("result", result);

		if (result == 1) {
			String mno = (String) session.getAttribute("mno");
			map.put("mno", mno);
			map.put("cCode", cCode);
			payService.couponUpdate(map);
		}

		return json.toString();

	}

	@ResponseBody
	@PostMapping("/couDiscount")
	public int couDiscount(@RequestParam("sCoupon") String selectCoupon) {
		System.out.println(selectCoupon);
		int response = payService.couDiscount(selectCoupon);
		System.out.println(response);
		return response;
	}

	/*
	 * @GetMapping("/getCoupons") public SList<Map<String, Object>>() {
	 * 
	 * // 쿠폰 목록을 서비스 레이어에서 가져옵니다. List<Map<String, Object>> couponList =
	 * PayService.getCoupons();
	 * 
	 * // 가져온 쿠폰 목록을 클라이언트에 반환합니다.
	 * 
	 * 
	 * return "/couponList";
	 * 
	 * }
	 */

	@ResponseBody
	@PostMapping("updatePoint")
	public void updatePoint(@RequestParam Map<String, Object> map) {
		payService.updatePoint(map);
		System.out.println(map.get("ppoint"));

	}

	@ResponseBody
	@PostMapping("/charge")
	public String charge(@RequestParam Map<String, Object> map) {
		
		// 입력한 카드 정보
		String cardSelect = (String) map.get("cardSelect");
		String cardNum = (String) map.get("cardNum");
		String cardPw = (String) map.get("cardPw");
		String cardExp = (String) map.get("cardExp");
		String cardBir = (String) map.get("cardBir");

		// db 카드정보 가져오기
		Map<String, Object> cardInfo = payService.charge(cardSelect);

		// 카드 정보 비교
		String dbCardNum = (String) cardInfo.get("rs_cnum");
		String dbCardPw = (String) cardInfo.get("rs_cpw");
		String dbCardExp = (String) cardInfo.get("rs_cdate");
		String dbCardBir = (String) cardInfo.get("rs_cbirth");

		JSONObject json2 = new JSONObject();

		if (cardNum.equals(dbCardNum) && cardPw.equals(dbCardPw) && cardExp.equals(dbCardExp)
				&& cardBir.equals(dbCardBir)) {
			
			json2.put("result", "success");
		} else {
			if (!cardNum.equals(dbCardNum)) {
				json2.put("result", "1");
				
			} else {
				if (!cardExp.equals(dbCardExp)) {
					json2.put("result", "2");
					
				} else {
					if (!cardPw.equals(dbCardPw)) {
						json2.put("result", "3");
						
					} else {
						json2.put("result", "4");

					}
				}
			}
			
		}
		return json2.toString();
	}

}
