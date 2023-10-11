package com.movie.web.detail;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DetailController {
	
	@Autowired
	DetailService detailService;
	
	@GetMapping("/detail")
	public String detailPage(@RequestParam("mv_code") String mv_code, Model model) {
		Map<String, Object> detail = detailService.detail(mv_code); 
		String[] mc_acphoto = {(String)detail.get("mc_acphoto")};
		String[] mc_actor = {(String)detail.get("mc_actor")} ; 

		String[] au_img =((String)detail.get("au_img")).split("\\|");
		
		if (((String) detail.get("mc_acphoto")).contains("|")) {
			mc_acphoto =((String)detail.get("mc_acphoto")).split("\\|");
		}
		
		if (((String) detail.get("mc_actor")).contains(",")) {
			mc_actor =((String)detail.get("mc_actor")).split(",");
		}
		
		model.addAttribute("detail", detail);
		model.addAttribute("au_img", au_img);
		model.addAttribute("mc_acphoto", mc_acphoto);
		model.addAttribute("mc_actor", mc_actor);
	    return "detail"; 
	}
	
	@PostMapping("/search")
	public String detail(@RequestParam("mv_name") String mv_name) {
		
		System.out.println(mv_name);
		
		String mv_code = detailService.movie(mv_name);
		System.out.println(mv_code);
		
		
		return "redirect:detail?mv_code="+mv_code;
	}
	
}
