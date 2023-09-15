<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CGV::ticket</title>
<script src="./js/jquery-3.7.0.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<link rel="stylesheet" href="./bootstrapt/css/bootstrap.min.css" />
<link rel="stylesheet" href="css/menu.css" />
<link rel="stylesheet" href="css/ticket.css" />
</head>
<body>
	<%@ include file="menu.jsp"%>






	<h1>TICKET</h1>


	<div id="msIdx">${msIdx}</div>
	<br> 예매번호
	<br>${rsNum}
	<br>
	<br> 영화제목
	<br>
	<div id="movieName">${tcInfo.mv_name}</div>
	<br>
	<br> 포맷
	<c:if test="${tcInfo.th_kind == 1 || tcInfo.th_kind == 2}">
		<div>2D</div>
	</c:if>
	<c:if test="${tcInfo.th_kind == 3}">
		<div>3D</div>
	</c:if>
	<c:if test="${tcInfo.th_kind == 4}">
		<div>아이맥스</div>
	</c:if>
	<br> 관람등급
	<br> ${mvGrade}
	<br>
	<br> 상영관
	<br>
	<div id="theaterName">${tcInfo.th_city}${tcInfo.th_idx}관</div>
	<br> 상영일
	<br>${tcInfo.ms_sdate}
	<br>
	<br> 상영시간
	<br>${tcInfo.ms_stime} ~ ${tcInfo.ms_etime}
	<br>
	<br> 좌석
	<br> ${list}
	<br>
	<br>
	<div id="people">성인 ${adult}명,청소년 ${youth}명, 우대 ${special}명</div>

	<button id="cancel">예매 취소하기</button>
	<button id="okay">확인</button>


	<script>
	$(document).ready(function() {
		
		$("#msIdx").hide();
		
		 var adultNum = parseInt("${adult}");
		   var youthNum = parseInt("${youth}");
		   var specialNum = parseInt("${special}");
		   
            // 현재 날짜와 시간을 표시할 요소 가져오기
            //var currentDateElement = document.getElementById('currentDate');
            //var currentDateTimeElement = document.getElementById('currentDateTime');
            
            // 페이지에 현재 날짜와 시간 표시
            //currentDateElement.innerText = "현재 날짜: " + currentTime.toLocaleDateString();
            //currentDateTimeElement.innerText = "현재 시간: " + currentTime.toLocaleTimeString();
			
            var currentTime = new Date();
              
            // 페이지에 적힌 날짜와 시간 가져오기
            var msSdate = "${tcInfo.ms_sdate}";
            var msStime = "${tcInfo.ms_stime}";
            
         // 문자열을 Date 객체로 변환
            var msSdateDate = new Date(msSdate + "T" + msStime);
   
	 
	
	
	$("#cancel").click(function() {
	
		
		
		  var canConfirm = confirm('해당 예매를 취소하시겠습니까?');

		   if (canConfirm) {
			
	            if (currentTime <= msSdateDate) {
	                alert("예매가 취소되었습니다");
	                
	                $.ajax({
	                     type: "POST",
	                     url: "/updateSeat2",
	                     data: {
	                    	 list:"${list}",
	                    	 msIdx:"${msIdx}",
	                    		 rsNum:"${rsNum}"},
	                     success: function() {
	                         
	                    	 alert("db삭제");
	                        
	                     },
	                     error: function() {
	                         alert("에러. 다시 시도하세요");
	                     }
	                 });
	                
	                
	                
	                
	            } else {
	                alert("상영시간이 지나 취소할 수 없습니다");
	            }
		   }
		
	});
	});
	
	</script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>