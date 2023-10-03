package com.movie.web.pay;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TicketController {

	@Autowired
	TicketService ticketService;

	@GetMapping("/ticket")
	public String ticket(Model model, @RequestParam Map<String, Object> info, @RequestParam("date") String date, HttpServletRequest request, HttpSession session) {

		 Integer mno = (Integer) session.getAttribute("m_no");

		 
		 if(mno != null) {
		
		// 예매번호 가져오기
		String rsNum = ticketService.rsNumber(info);
		model.addAttribute("rsNum", rsNum);

		// 영화 정보 가져오기
		Map<String, Object> tcInfo = ticketService.ticketInfo(rsNum);
		String msIdx = (String) info.get("ms_idx");
		String mvGrade = (String) tcInfo.get("mv_grade");
		
		//date = date.substring(0, 4) + "년 " + date.substring(4, 6) + "월 " + date.substring(6, 8) + "일";
		model.addAttribute("date", date);
		

		model.addAttribute("tcInfo", tcInfo);
		model.addAttribute("msIdx", msIdx);
		model.addAttribute("mvGrade", mvGrade);

		model.addAttribute("adult", info.get("adult"));
		model.addAttribute("youth", info.get("youth"));
		model.addAttribute("special", info.get("special"));
		model.addAttribute("list", info.get("list"));

		return "/ticket";
		 } else {
			   return "redirect:/login";
		   }
	}

	@ResponseBody
	@PostMapping("/updateSeat2")
	void updateSeat2(@RequestParam Map<String, Object> map) {

		String rsNum = (String) map.get("rsNum");

		// 예약 좌석 취소
		ticketService.updateSeat2(map);
		// 예약 상태 변경
		ticketService.updateRsNum(map);
		// 적립포인트, 사용포인트 갖고오기
		 Map<String, Object> pointMap = ticketService.saveUsePoint(rsNum);
		  map.putAll(pointMap);

		// 적립 포인트 차감, 기사용 포인트 적립
		ticketService.deductPoint(map);
		// 결제내역 삭제
		ticketService.deletePay(rsNum);

	}

	
}
