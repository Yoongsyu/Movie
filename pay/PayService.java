package com.movie.web.pay;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayService {
   @Autowired
   private PayDAO payDAO;

   public List<Map<String, Object>> couponList(int mno) {
      return payDAO.couponList(mno);
   }

   public Map<Integer, Object> havePoint(int mno) {
      return payDAO.havePoint(mno);
   }

   public void updatePoint(Map<String, Object> map) {
      payDAO.updatePoint(map);
   }

   //public Map<Integer, Object> ticketPrice() {
   //   return payDAO.ticketPrice();
   //}

   public int couponChk(String cCode) {
      return payDAO.couponChk(cCode);
   }

   public void couponUpdate(Map<String, Object> map) {
      payDAO.couponUpdate(map);
   }

   
   public int admChk(String cCode) {
	      return payDAO.admChk(cCode);
	   }

	   public void admUpdate(Map<String, Object> map) {
	      payDAO.admUpdate(map);
	   }
   

   public List<Map<String, Object>> charge(String cardSelect) {
      return payDAO.charge(cardSelect);
   }

    public int cardChk(int mno) {
         return payDAO.cardChk(mno);
      }

      public List<Map<String, Object>> cardList(int mno) {
            return payDAO.cardList(mno);
         }

   public void cardUpdate(Map<String, Object> map) {
      payDAO.cardUpdate(map);
   }

   public int couDiscount(String selectCoupon) {
      return payDAO.couDiscount(selectCoupon);
   }

   public Map<String, Object> ticketInfo(Map<String, Object> info) {
      return payDAO.ticketInfo(info);
   }

   public Map<String, Object> email(int mno) {
		return payDAO.email(mno);
	}

	void rsData(Map<String, Object> rsData) {
		payDAO.rsData(rsData);
		
	}

	public void updateSeat(Map<String, Object> map) {
		
		 String list = (String) map.get("list"); // "G5,G6,F5,F6,G7"
	        String msIdxx = (String) map.get("msIdx");
	        int msIdx = Integer.parseInt(msIdxx);

	        //좌석 목록을 분리
	        String[] seats = list.split(",");

	        for (String seat : seats) {
	        	 payDAO.updateSeat(msIdx, seat);

	}
   
	}


	public void couDelete(List<String> selectCouponList) {
		 payDAO.couDelete(selectCouponList);	
	}
	
	public void admDelete(List<String> selectAdm) {
		payDAO.admDelete(selectAdm);
	}

	public String rsNumber() {
		return payDAO.rsNumber();
	}

	public void record(Map<String, Object> map) {
		payDAO.record(map);
	}
}