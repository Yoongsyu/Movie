package com.movie.web.join;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class JoinController {
	@Autowired
	private JoinService joinService;	
	
	@GetMapping("/join")
	public String join() {
		
		return "/join";
	}
	
	@PostMapping("/join")
	public String join(@Valid JoinDTO joinDTO, BindingResult bindingResult, Model model,RedirectAttributes redirectAttributes) {
	    // 오류 처리를 위해 모델에 errorMsg를 추가
	    Map<String, String> errorMsg = new HashMap<>();
	    if(bindingResult.hasErrors()) {
	        System.out.println("다시 시도해 주세요");
	        
	        List<FieldError> errorlist = bindingResult.getFieldErrors();
	        
	        for(int i=0; i<errorlist.size(); i++) {
	            String field = errorlist.get(i).getField();
	            String message = errorlist.get(i).getDefaultMessage();
	                    
	            System.out.println("필드 = " + field);
	            System.out.println("메세지 = " + message);
	            
	            errorMsg.put(field, message);
	        }
	        	
	        // 모델에 errorMsg를 추가
	        model.addAttribute("errorMsg", errorMsg);
	        
	        // 오류가 발생한 경우 다시 회원가입 페이지로 이동
	        return "/join";
	    }
	    joinService.join(joinDTO);
	    redirectAttributes.addFlashAttribute("successMsg", "회원가입을 축하합니다! 가입 축하 기념 1000p가 지급되셨습니다.");


	    return "redirect:/join";
	}

	@ResponseBody
	@GetMapping("/overlapCheck")
	public int overlapCheck(@RequestParam String value, @RequestParam String valueType) {
//		value = 중복체크할 값
		int count = joinService.overlapCheck(value, valueType);
		
		System.out.println(count);
		return count;
	}
	
	
	
	
	}

