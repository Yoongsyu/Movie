package com.movie.web.pay;



import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Component;

@Component
public class PayUtil {



	
	public void htmlMailSender(Map<String, Object> mailmail) throws EmailException {
		String emailAddr = "-";
		String password = "-";
		String name = "DreamBox";
		String hostname = "-"; //smtp주소
		int port = -;
	
		 try {
		
	      // 메일보내기 작업하기
	      HtmlEmail mail = new HtmlEmail(); //html메일 보내기로 변경합니다.
	      mail.setCharset("UTF-8");
	      mail.setDebug(false);
	      mail.setHostName(hostname); // 고정
	      mail.setAuthentication(emailAddr, password); // 고정
	      mail.setSmtpPort(port); // 고정
	      mail.setStartTLSEnabled(true); // 고정
	      mail.setFrom(emailAddr, name); // 고정

	      
	      String mName = (String) mailmail.get("name");
	      String mvName = (String) mailmail.get("mvName");
	      String msSdate = (String) mailmail.get("msSdate");
	      String msStime = (String) mailmail.get("msStime");
	      String msEtime = (String) mailmail.get("msEtime");
	      String thCity = (String) mailmail.get("thCity");
	      String thKind = (String) mailmail.get("thKind");
	      String adultNum = (String) mailmail.get("adultNum");
	      String youthNum = (String) mailmail.get("youthNum");
	      String specialNum = (String) mailmail.get("specialNum");
	      String seat = (String) mailmail.get("seat");
	      String randomNum = (String) mailmail.get("rsNum");
	      	      
	      String sDate = msSdate.substring(0, 4) + "년 " + msSdate.substring(4, 6) + "월 " + msSdate.substring(6, 8) + "일";
	      
	      System.out.println(sDate);
	      
	      mail.addTo((String) mailmail.get("to")); // 받는 사람 email
	      mail.setSubject((String) mailmail.get("title")); // 메일 제목
	   
	     
	      //이미지 경로 잡아오기
	      String img = "static/img/logo1.png"; // 이미지 파일명
	      String img2 = "static/img/QR코드2.png"; // 이미지 파일명
	      String base64Image = getImageBase64(img);
	      String base64Image2 = getImageBase64(img2);

	      
	      String html = "<html>";

	      html += "<img alt=\"이미지\" src='data:image/png;base64," + base64Image + "'>";
	      html += "<div>"+ mName +"님 안녕하세요, 고객님의 예매내역을 전송해 드립니다.</div><br>";
	    
	      html += "<h3>예매번호 : " + randomNum + "</h3><br>";
	      html += "<div>고객님께서 예매하신 영화는 [" + mvName + "]입니다.</div><br>";
	      html += "<h3>상영일</h3>";
	      html += "<div>"+ sDate +"</div><br>";
	      html += "<h3>상영시간</h3>";
	      html += "<div>"+ msStime + "~" + msEtime +"</div><br>";
	      html += "<h3>상영관</h3>";
	      html += "<div>"+ thCity + " " + thKind +"관</div><br>";
	      html += "<h3>좌석</h3>";
	      html += "<div>"+ seat +"</div><br>";
	      html += "<div>성인 : "+ adultNum +"명, 청소년 : " + youthNum + "명, 우대 : " + specialNum + "명</div><br>";
	      
	      html += "<h3> DreamBox와 함께 즐거운 관람 되시기를 바랍니다.</h3>";
	      html += "<img alt=\"이미지\" src='data:image/png;base64," + base64Image2 + "'>";
	      html += "</html>";
	      mail.setHtmlMsg(html);
	   
	      
	      String result = mail.send(); // 메일 보내기
	      System.out.println("메일 보내기 : " + result);
	 
	 }  catch (IOException e) {
       
         e.printStackTrace(); 
     }
 }    
	
	      private String getImageBase64(String img) throws IOException {
	    	  InputStream inputStream = getClass().getClassLoader().getResourceAsStream(img);

	    	  byte[] imageBytes = inputStream.readAllBytes();
	    	    return Base64.getEncoder().encodeToString(imageBytes);
	    	}
	      
	      


}
