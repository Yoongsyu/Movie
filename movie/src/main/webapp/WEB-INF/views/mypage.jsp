<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CGV::mypage</title>
<script src="./js/jquery-3.7.0.min.js"></script>
<link rel="stylesheet" href="css/menu.css" />
<link rel="stylesheet" href="css/pay.css" />
</head>
<body>
	<%@ include file="menu.jsp"%>
	<h1>MYPAGE</h1>
	
		성명 : ${myInfo.m_name}
		<br>
	닉네임 : ${myInfo.m_nickname}
	<br>
	아이디 : ${myInfo.m_id}
	<br> 
	생년월일 : ${myInfo.m_birth} 
	<br> 
	이메일 : ${myInfo.m_email}
	<br> 
	보유 포인트 : ${myInfo.m_point} p
	<br>
	보유 쿠폰 :
	
	<button type="button" id="modal_open_btn">할인쿠폰</button>

	<div id="modal">
		<div class="modal_content">
			<h2>할인쿠폰</h2>
			쿠폰 등록 <input type="text" name="couponCode" id="couponCode">
			<button id="couponCheck">등록</button>

			<c:forEach items="${couponList}" var="c">
				<c:if test="${not empty c.rs_coupon}">
               ${c.rs_cindex} ${c.rs_coupon}<p>
				</c:if>
			</c:forEach>
			<button type="button" id="modal_close_btn">확인</button>

		</div>
		<div class="modal_layer"></div>
	</div>
	
	
	<br>
	보유 관람권 :
	
		<button type="button" id="modal_open_btn2">관람권</button>
	<div id="modal2">
		<div class="modal_content">
			<h2>관람권</h2>
			   <c:forEach items="${couponList}" var="a">
            <c:if test="${not empty a.rs_admission && empty a.rs_aindex }">
               [2D/3D] ${a.rs_admission}<p>
            </c:if>
            <c:if test="${not empty a.rs_admission && not empty a.rs_aindex }">
              ${a.rs_aindex} ${a.rs_admission}<p>
            </c:if>
         </c:forEach>

			<button type="button" id="modal_close_btn2">확인</button>
		</div>
		<div class="modal_layer"></div>
	</div>
	
	
	
	
	
	
	
	<script>
	
	 $("#modal_open_btn").click(function() {
		 $("#modal").show();
         
      });
	
	 $("#modal_close_btn").click(function() {
         $("#modal").hide();
      });
	 
	 $("#modal_open_btn2").click(function() {
		 $("#modal2").show();
         
      });
	
	 $("#modal_close_btn2").click(function() {
         $("#modal2").hide();
      });
	
	
	 // 쿠폰 유효성 검사, 등록 // 
     $("#couponCheck").click(function() {
         let cCode = parseInt($("#couponCode").val()); // 입력된 usepoint 값을 정수로 파싱
         let cLength = cCode.toString().length;         
         if (cLength != 16) {
              alert("16자리 숫자로 입력하세요.");
          } else{
          $.ajax({
              url: "/couponChk2", 
              type: "post",
              data: {"cCode": cCode},
              dataType:"json",
              success: function(result) {
              
                  if (result.result == 1) {
                      alert("쿠폰 등록이 완료되었습니다");
                      /* 쿠폰목록 새로고침 보류
                      $.ajax({
                          url: "/getCoupons", // 업데이트된 쿠폰 리스트를 가져오는 URL
                          type: "get",
                          dataType: "json",
                          success: function(couponList) {
                              // 업데이트된 쿠폰 리스트를 화면에 표시하는 코드
                              // updatedCouponList를 이용하여 DOM을 업데이트    
                               console.log(couponList);
                              },
                          error: function() {
                              alert("쿠폰 목록을 가져오는 데 실패했습니다.");
                          }
                      });
                      */
                  } else {
                      alert("유효하지 않는 코드입니다");
                  }
              },
              error: function() {
                  alert("에러. 다시 시도하세요");
              }
          });           
          }
      });
	
	
	
	
	
	</script>
</body>
</html>