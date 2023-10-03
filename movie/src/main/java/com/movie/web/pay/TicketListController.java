package com.movie.web.pay;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TicketListController {

	@Autowired
	TicketListService ticketListService;


	@GetMapping("/ticketlist")
	public String ticketlist(Model model, HttpSession session) {

		Integer mno = (Integer) session.getAttribute("m_no");
		
		 if(mno != null) {
		
		List<Map<String, Object>> ticketList = ticketListService.ticketList(mno);
		
		System.out.println(ticketList);
		
		model.addAttribute("ticketList", ticketList);

		return "/ticketlist";
		 } else {
			 return "redirect:/login";
		 }
	}

	
}
