package com.movie.web.theaterinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TheaterInfoController {
	@Autowired 
	private TheaterInfoService theaterInfoService;
	
	@GetMapping("/theaterinfo")
	public String theaterInfo() {
		
		return "/theaterinfo";
	}
}
