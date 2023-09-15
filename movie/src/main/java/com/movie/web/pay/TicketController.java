package com.movie.web.pay;

import java.util.Map;

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
	public String ticket(Model model, @RequestParam Map<String, Object> info) {

		// 영화 정보 가져오기
		Map<String, Object> tcInfo = ticketService.ticketInfo(info);
		String msIdx = (String) info.get("ms_idx");
		String mvGrade = (String) tcInfo.get("mv_grade");

		model.addAttribute("tcInfo", tcInfo);
		model.addAttribute("msIdx", msIdx);
		model.addAttribute("mvGrade", mvGrade);

		model.addAttribute("adult", info.get("adult"));
		model.addAttribute("youth", info.get("youth"));
		model.addAttribute("special", info.get("special"));
		model.addAttribute("list", info.get("list"));

		//예매번호 가져오기
		String rsNum = ticketService.rsNumber();
		model.addAttribute("rsNum", rsNum);
		
		
		

		return "/ticket";
	}
	
	
	
	 @ResponseBody
		@PostMapping("/updateSeat2")
	 void updateSeat2(@RequestParam Map<String, Object> map) {

		ticketService.updateSeat2(map);
		
		ticketService.updateRsNum(map);
		
		System.out.println(map);
	 }
	 
	 
}
