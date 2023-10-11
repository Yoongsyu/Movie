package com.movie.web.login;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;

import ch.qos.logback.core.util.SystemInfo;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;

	@Autowired
	private Util util;

	@Autowired
	NaverLoginBO naverloginbo;

	@GetMapping("/login")
	public String login(Model model, HttpSession session) {
		String naverAuthUrl = naverloginbo.getAuthorizationUrl(session);
		model.addAttribute("naverUrl", naverAuthUrl);

		return "/login";
	}

	@PostMapping("/login")
	public ModelAndView login(@RequestParam Map<String, Object> map, HttpSession session) {
		System.out.println(map);

		Map<String, Object> result = loginService.login(map);
		ModelAndView mv = new ModelAndView();

		//System.out.println("리설치에 담긴것은" + result);

		if (util.obj2Int(result.get("count")) == 1) {
			// 세션올리기

			if (util.obj2Int(result.get("m_status")) == 2) {
				String error = "본 계정은 정지되었습니다. 자세한 내용은 문의해주시기 바랍니다.";
				mv.addObject("error", error);
				mv.addObject("redirect:/login");
			} else {

			session.setAttribute("id", map.get("id"));
			session.setAttribute("m_no", result.get("m_no"));
			session.setAttribute("name", result.get("m_name"));
			session.setAttribute("nickName", result.get("m_nicname"));
			session.setAttribute("status", result.get("m_status"));
			if (util.obj2Int(result.get("m_status")) == 0) {
				session.setAttribute("adminInfo", result);
			} 
			// 메인으로 이동하기
			mv.setViewName("redirect:/");
			
			}
		} else {
			String error = "아이디나 비밀번호가 올바르지 않습니다.";
			mv.addObject("error", error);
			mv.addObject("redirect:/login");
		}
		return mv;

	}

	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {

		session.setMaxInactiveInterval(0);
		session.invalidate();

		return "redirect:/";
	}

	// 아이디찾기

	@GetMapping("/findid")
	public String findId() {
		return "findid";
	}

	@ResponseBody
	@PostMapping("/findid")
	public String findId(@RequestParam(value = "findid") String findid) {
//		System.out.println("여기오는지확인+" + findid);
		JSONObject json = new JSONObject();
		Map<String, Object> result = loginService.findId(findid);
		// 아이디와 이름가져옴
		//System.out.println("타입가져오냐" + result.get("m_type"));

		//System.out.println(result.get("m_id"));
		// System.out.println(result.get("m_type"));
		json.put("m_name", result.get("m_name"));
		json.put("m_id", result.get("m_id"));
		String m_type = (String) result.get("m_type");
		if (m_type != null && result.get("m_type").equals("naver")) {
			//System.out.println("타입가져오냐" + result.get("m_type"));
			json.put("naver", "네이버 연동 회원입니다.");
			return json.toString();
		} else {
			return json.toString();
		}
	}

	// 비밀번호찾기
	@GetMapping("/findpw")
	public String findPw() {
		return "findpw";
	}

	@ResponseBody
	@PostMapping("/findpw")
	public String findPw(@RequestParam Map<String, Object> map, Model model) throws Exception {
//		System.out.println("여기옴?" + map);
		// 여기옴?{email=baboyahi@naver.com, id=dy0201}
		JSONObject json = new JSONObject();
		Map<String, Object> result = loginService.findPw(map);
		// 맵퍼실행확인{m_name=최지은, m_pw=vmglgl36, m_id=dy0201, m_email=baboyahi@naver.com}
		if (result == null) {
			json.put("error", "입력하신 아이디와 이메일이 올바르지 않습니다.");
			return json.toString();
		} else {
			util.htmlMailSender(result);
			// 이메일보내고 임시비밀번호로 변경
			int success = loginService.temporaryPw(result);
			//System.out.println("여기에 뭐오냐" + success);
			json.put("m_name", result.get("m_name"));
			return json.toString();
		}

	}

	// 네이버 로그인
	@RequestMapping(value = "/login/naver", method = { RequestMethod.GET, RequestMethod.POST })
	public String userNaverLoginPro(Model model, @RequestParam Map<String, Object> paramMap, @RequestParam String code,
			@RequestParam String state, HttpSession session) throws SQLException, Exception {
//		System.out.println("paramMap:" + paramMap);
		Map<String, Object> resultMap = new HashMap<String, Object>();

		OAuth2AccessToken oauthToken;
		oauthToken = naverloginbo.getAccessToken(session, code, state);
//		System.out.println("토큰읽나요" + oauthToken);
		// 로그인 사용자 정보를 읽어온다.
		String apiResult = naverloginbo.getUserProfile(oauthToken);
//		System.out.println("apiResult여기 =>" + apiResult);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> apiJson = (Map<String, Object>) objectMapper.readValue(apiResult, Map.class)
				.get("response");
//맵으로 사용자 정보를 저장함
//		System.out.println("여기까지옵니다" + apiJson);
		// 여기까지옵니다{id=ZzIax5zVqA4-BWv_5bPGAtZ5ux7HZYrLQsxOz4IG5CM, nickname=두식,
		// gender=F, name=최지은, birthday=06-11, birthyear=1995}

		Map<String, Object> naverConnectionCheck = loginService.naverConnectionCheck(apiJson);
		System.out.println(naverConnectionCheck);

		if (naverConnectionCheck == null) {

			model.addAttribute("id", apiJson.get("id"));
			model.addAttribute("name", apiJson.get("name"));
			model.addAttribute("gender", apiJson.get("gender"));
//			System.out.println("여기까지도 오잖아요?");
			return "naverdetail";
		} else {
			// 아이디가 있는경우 자동로그인
			Map<String, Object> loginCheck = loginService.userNaverLoginPro(apiJson);
			session.setAttribute("userInfo", loginCheck);
//			System.out.println("뭐들어있지"+loginCheck);
			session.setAttribute("id", loginCheck.get("m_id"));
			session.setAttribute("m_no", loginCheck.get("m_no"));
			session.setAttribute("status", loginCheck.get("m_status"));
			//System.out.println(session.getAttribute("m_no"));
		//	System.out.println("왜 못잡냐"+session.getAttribute("id"));
			return "redirect:/";
		}
	}

	@RequestMapping(value = "userRegisterForm", method = RequestMethod.POST)
	public String userNaverRegisterPro(@RequestParam Map<String, Object> paramMap, HttpSession session, Model model) {
//		System.out.println("paramMap여기오는지:" + paramMap);
		Integer registerCheck = loginService.userNaverRegisterPro(paramMap);
//		System.out.println(registerCheck);
		if (registerCheck != null && registerCheck > 0) {
			Map<String, Object> loginCheck = loginService.userNaverLoginPro(paramMap);
			session.setAttribute("userInfo", loginCheck);
			//System.out.println("뭐들어있지"+loginCheck);
			session.setAttribute("id", loginCheck.get("m_id"));
			session.setAttribute("m_no", loginCheck.get("m_no"));
			session.setAttribute("status", loginCheck.get("m_status"));
//			System.out.println(session.getAttribute("m_no"));
//			System.out.println(session.getAttribute("id"));
			return "redirect:/";
		}
		return "login";
	}

}
