package com.movie.web.pay;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PayController {
   @Autowired
   private PayService payService;

	
	@Autowired
	private PayUtil util;


	@GetMapping("/pay")
	public String pay(Model model, @RequestParam Map<String, Object> info, @RequestParam("date") String date,
			HttpSession session) {

		Integer mno = (Integer) session.getAttribute("m_no");

		if (mno != null) {

			// 영화 정보 가져오기
			Map<String, Object> tcInfo = payService.ticketInfo(info);
			String msIdx = (String) info.get("ms_idx");

			//String date2 = date.substring(0, 4) + "년 " + date.substring(4, 6) + "월 " + date.substring(6, 8) + "일";
			System.out.println(date);
			model.addAttribute("date", date);

			model.addAttribute("msIdx", msIdx);
			model.addAttribute("tcInfo", tcInfo);

			List<Map<String, Object>> couponList = payService.couponList(mno);
			model.addAttribute("couponList", couponList);

			Map<Integer, Object> havePoint = payService.havePoint(mno);
			model.addAttribute("havePoint", havePoint);

			int cardChk = payService.cardChk(mno);
			model.addAttribute("cardChk", cardChk);

			List<Map<String, Object>> cardList = payService.cardList(mno);
			model.addAttribute("cardList", cardList);

			model.addAttribute("adult", info.get("adult"));
			model.addAttribute("youth", info.get("youth"));
			model.addAttribute("special", info.get("special"));
			model.addAttribute("list", info.get("list"));

			return "/pay";
		} else {
			return "redirect:/login";
		}

	}

	@PostMapping("/pay")
	public String pay(@RequestParam("ms_idx") String ms_idx, @RequestParam Map<String, Object> info, @RequestParam("date") String date) {

		System.out.println(date);
		
		return "redirect:/ticket?ms_idx=" + ms_idx + "&adult=" + info.get("adult") + "&youth=" + info.get("youth")
				+ "&special=" + info.get("special") + "&list=" + info.get("list") + "&date=" + date;

	}
	   
   @ResponseBody
   @PostMapping("/couponChk")
   public String couponChk(@RequestParam("cCode") String cCode, HttpSession session,
         Map<String, Object> map) {
      int result = payService.couponChk(cCode);
      JSONObject json = new JSONObject();
      json.put("result", result);

      if (result == 1) {
         int mno = (int) session.getAttribute("m_no");
         map.put("mno", mno);
         map.put("cCode", cCode);
         payService.couponUpdate(map);
      }

      return json.toString();

   }

   @ResponseBody
   @PostMapping("/couDiscount")
   public int couDiscount(@RequestBody List<String> selectCouponList) {
       int totalDiscount = 0;
   
       for (String selectCoupon : selectCouponList) {       
           int discount = payService.couDiscount(selectCoupon);
  
           totalDiscount += discount;
       }
       return totalDiscount;
   }
   
   
   @ResponseBody
   @PostMapping("/admChk")
   public String admChk(@RequestParam("aCode") String aCode, HttpSession session,
         Map<String, Object> map) {
      int result = payService.admChk(aCode);
      JSONObject json4 = new JSONObject();
      json4.put("result", result);

      if (result == 1) {
         int mno = (int) session.getAttribute("m_no");
         map.put("mno", mno);
         map.put("aCode", aCode);
         payService.admUpdate(map);
         
      }

      return json4.toString();

   }

   
   @ResponseBody
   @PostMapping("/admDelete")
   public void admDelete(@RequestParam("selectAdm") List<String> selectAdm) {
  
       payService.admDelete(selectAdm);
      }
   
   @ResponseBody
   @PostMapping("/couDelete")
   public void couDelete(@RequestParam("selectCouponList") List<String> selectCouponList) {
	   
	   payService.couDelete(selectCouponList);
   }



   @ResponseBody
   @PostMapping("updatePoint")
   public void updatePoint(@RequestParam Map<String, Object> map, HttpSession session) {
	   
	   int mno = (int) session.getAttribute("m_no");
	   int newPoint = Integer.parseInt((String) map.get("newPoint"));
	   int savePoint = Integer.parseInt((String) map.get("savePoint"));
	    
	    System.out.println(newPoint);
	    System.out.println(savePoint);
	    
	   int newPoint2 = newPoint + savePoint;
	   map.put("mno", mno);
		 map.put("newPoint2", newPoint2); 

      payService.updatePoint(map);

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
      List<Map<String, Object>> cardInfoList = payService.charge(cardSelect);
      
JSONObject json2 = new JSONObject();

      // 카드 정보 비교
      for (Map<String, Object> cardInfo : cardInfoList) {
          String dbCardNum = (String) cardInfo.get("rs_cnum");
          String dbCardPw = (String) cardInfo.get("rs_cpw");
          String dbCardExp = (String) cardInfo.get("rs_cdate");
          String dbCardBir = (String) cardInfo.get("rs_cbirth");
          

      

      if (cardNum.equals(dbCardNum) && cardPw.equals(dbCardPw) && cardExp.equals(dbCardExp)
            && cardBir.equals(dbCardBir)) {

         json2.put("result", "success");

      } else {
         if (!cardNum.equals(dbCardNum)) {
            json2.put("result", "카드 번호가 잘못되었습니다");

         } else {
            if (!cardExp.equals(dbCardExp)) {
               json2.put("result", "카드 유효기간이 잘못되었습니다");

            } else {
               if (!cardPw.equals(dbCardPw)) {
                  json2.put("result", "카드 비밀번호가 잘못되었습니다");

               } else {
                  if (!cardBir.equals(dbCardBir)) {
                     json2.put("result", "생년월일이 일치하지 않습니다");
                  } else {
                     json2.put("result", "카드사를 다시 확인해주세요");
                  }
               }
            }
         }

      }
      }
      return json2.toString();
   }

   
   @ResponseBody
   @PostMapping("/cardAdd")
   public String cardAdd(@RequestParam Map<String, Object> map, @RequestParam Boolean agree, HttpSession session) {

	   int mno = (int) session.getAttribute("m_no");
	   
      // 입력한 카드 정보
      String cardSelect = (String) map.get("cardSelect");
      String cardNum = (String) map.get("cardNum");
      String cardPw = (String) map.get("cardPw");
      String cardExp = (String) map.get("cardExp");
      String cardBir = (String) map.get("cardBir");
      
      
      List<Map<String, Object>> cardInfoList = payService.charge(cardSelect);
 JSONObject json3 = new JSONObject();
 
      // 카드 정보 비교
      for (Map<String, Object> cardInfo : cardInfoList) {
          String dbCardNum = (String) cardInfo.get("rs_cnum");
          String dbCardPw = (String) cardInfo.get("rs_cpw");
          String dbCardExp = (String) cardInfo.get("rs_cdate");
          String dbCardBir = (String) cardInfo.get("rs_cbirth");
          Integer dbCardMno = (Integer) cardInfo.get("m_no");
    
     
      
      if (cardNum.equals(dbCardNum) && cardPw.equals(dbCardPw) && cardExp.equals(dbCardExp) && cardBir.equals(dbCardBir) && dbCardMno == null) {

           if (!agree) {
              json3.put("result", "약관에 동의해주세요");
           } else {
              json3.put("result", "success");
              map.put("mno", mno);
              payService.cardUpdate(map);

           }

        } else {
           if (!cardNum.equals(dbCardNum)) {
              json3.put("result", "카드 번호가 잘못되었습니다");

           } else {
              if (!cardExp.equals(dbCardExp)) {
                 json3.put("result", "카드 유효기간이 잘못되었습니다");

              } else {
                 if (!cardPw.equals(dbCardPw)) {
                    json3.put("result", "카드 비밀번호가 잘못되었습니다");

                 } else {
                    if (!cardBir.equals(dbCardBir)) {
                       json3.put("result", "생년월일이 일치하지 않습니다");
                    } else {
                       if (dbCardMno != null) {
                          json3.put("result", "이미 등록된 카드입니다");
                       } else {
                          if (dbCardMno != mno) {
                             json3.put("result", "카드 명의자가 일치하지 않습니다");
                          } else {
                             json3.put("result", "카드사를 다시 확인해주세요");
                          }
                       }
                    }
                 }
              }
           }
        }
      }
        return json3.toString();
   }
   
  
   private String RandomNumber() {
       StringBuilder sb = new StringBuilder();
       Random random = new Random();
       for (int i = 0; i < 10; i++) {
           sb.append(random.nextInt(10));
       }
       return sb.toString();
   }
	
	
   
   
   
	 @ResponseBody
		@PostMapping("/email")
		public void email(@RequestParam Map<String, Object> mailmail, HttpSession session) throws EmailException {
		 int mno = (int) session.getAttribute("m_no");

			 // 예매번호 생성
		 String rsNum = RandomNumber();
			 String peopleNum = (String) mailmail.get("peopleNum");
			 
			 mailmail.put("peopleNum", peopleNum); // 예매정보
			mailmail.put("rsNum", rsNum); // 예매정보
			 mailmail.put("mno", mno); // 예매정보
			 			 
			payService.rsData(mailmail); // 예매정보 db 업데이트
			
			 Map<String, Object> emailData = payService.email(mno); //메일
			String mName= (String) emailData.get("m_name"); //메일
			String mEmail = (String) emailData.get("m_email");	//메일
			
			mailmail.put("title", "드림박스 예매내역입니다"); //메일
			mailmail.put("name", mName); //메일
			mailmail.put("to", mEmail); //메일

		
			   util.htmlMailSender(mailmail); // 텍스트, 사진,  
		
			   
			   int newPoint = Integer.parseInt((String) mailmail.get("newPoint"));
			    int savePoint = Integer.parseInt((String) mailmail.get("savePoint"));
			 //   int mno = (int) session.getAttribute("m_no");
			    int newPoint2 = newPoint + savePoint;
			    mailmail.put("mno", mno);
			    mailmail.put("newPoint2", newPoint2); 
			    
		 payService.record(mailmail);  
			   

		}
	
	 
	 @ResponseBody
		@PostMapping("/updateSeat")
	 void updateSeat(@RequestParam Map<String, Object> map) {

		 
		payService.updateSeat(map);


	 }
   
   



}