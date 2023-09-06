<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CGV::pay</title>
<script src="./js/jquery-3.7.0.min.js"></script>
<link rel="stylesheet" href="./bootstrapt/css/bootstrap.min.css" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>


<script type="text/javascript">

</script>

<style>
#modal, #modal2, #modal3, #modal4 {
	display: none;
	position: relative;
	width: 100%;
	height: 100%;
	z-index: 1;
}

#modal h2 {
	margin: 0;
}

#modal button, #modal2 button, #modal3 button, #modal4 button{
	display: inline-block;
	width: 100px;
	margin-left: calc(100% - 100px - 10px);
}

#modal .modal_content, #modal2 .modal_content, #modal3 .modal_content, #modal4 .modal_content {
	width: 500px;
	margin: 100px auto;
	padding: 20px 10px;
	background: #fff;
	border: 2px solid #666;
}

#modal .modal_layer, #modal2 .modal_layer, #modal3 .modal_layer, #modal4 .modal_layer {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.5);
	z-index: -1;
}
</style>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<h1>PAY</h1>

	<button type="button" id="modal_open_btn">할인쿠폰</button>

	<div id="modal">
		<div class="modal_content">
			<h2>할인쿠폰</h2>
			쿠폰 등록
<input type="text"
				name="couponCode" id="couponCode">
				<button id="couponCheck">등록</button>
				
				<c:forEach items="${couponList}" var="c">
				<c:if test="${not empty c.rs_cname}">
			<input type="checkbox" class="chk" data-coupon="${c.rs_coupon}">
					${c.rs_cname} ${c.rs_coupon}<p>
								</c:if>
				</c:forEach>
			
			
<form action="./payment" method="post">
			<button type="button" id="modal_submit_btn">적용</button>
			<button type="button" id="modal_close_btn">취소</button>
			</form>
		</div>
		<div class="modal_layer"></div>
	</div>


	<p>

		<button type="button" id="modal_open_btn2">관람권</button>
	<div id="modal2">
		<div class="modal_content">
			<h2>관람권</h2>
				<c:forEach items="${couponList}" var="a">
				<c:if test="${empty a.rs_cname}">
			<input type="checkbox" class="chk">
					${a.rs_admission}
					</c:if>
				</c:forEach>
			

			<button type="button" id="modal_submit_btn2">적용</button>
			<button type="button" id="modal_close_btn2">취소</button>
		</div>
		<div class="modal_layer"></div>
	</div>

	<p>


		<button type="button" id="modal_open_btn3">포인트 조회</button>
	<div id="modal3">
		<div class="modal_content">
			<h2>포인트</h2>

			보유 포인트 : ${havePoint.m_point}<p>
사용 포인트 :<input type="text"
				name="usepoint" id="usepoint">
					
				<button id="modal_submit_btn3">적용</button>
				<button id="modal_close_btn3">취소</button>
		
		</div>
		<div class="modal_layer"></div>
	</div>

	<br> 
	결제 금액 : <span id="pamountDisplay">${ticketPrice.tp_price}</span>원



	<br><br>
	결제방식<p>
	<button type="button" id="modal_open_btn4">일반 결제</button>
	<div id="modal4">
		<div class="modal_content">
			<h2>일반 결제</h2>

		카드사: <select id="cardSelect">
    <option value="비씨카드">비씨카드</option>
    <option value="국민카드">국민카드</option>
    <option value="신한카드">신한카드</option>
    <option value="삼성카드">삼성카드</option>
    <option value="롯데카드">롯데카드</option>
    <option value="농협카드">농협카드</option>
    <option value="하나카드">하나카드</option>
    <option value="현대카드">현대카드</option>
    <option value="우체국카드">우체국카드</option>
    <option value="우리카드">우리카드</option>
</select><p>

카드 번호 :<input type="text" id="cardNum"><p>
카드 유효기간 :<input type="text" id="cardExp"><p>
비밀번호 앞 2자리 :<input type="text" id="cardPw"><p>
생년월일 6자리 :<input type="text" id="cardBir"><p>
								
			<input type="checkbox" class="chk2">결제대행서비스 약관에 모두 동의
		
				<button id="modal_submit_btn4">결제하기</button>
				<button id="modal_close_btn4">취소</button>

		</div>
		<div class="modal_layer"></div>
	</div>
	
	
	<p>
	<button type="button" id="modal_open_btn5">간편 결제</button>
	<div id="modal5">
		<div class="modal_content">
			<h2>카드 등록하기</h2>

		카드사: <select>
    <option value="bc">비씨카드</option>
    <option value="kb">국민카드</option>
    <option value="sh">신한카드</option>
    <option value="ss">삼성카드</option>
    <option value="lt">롯데카드</option>
    <option value="nh">농협카드</option>
    <option value="hn">하나나드</option>
    <option value="hd">현대카드</option>
    <option value="post">우체국카드</option>
    <option value="wr">우리카드카드</option>
</select><p>

카드 번호 :<input type="text" id="cardNum2"><p>
카드 유효기간 :<input type="text" id="cardExp2"><p>
비밀번호 앞 2자리 :<input type="text" id="cardPw2"><p>
생년월일 6자리 :<input type="text" id="cardBir2"><p>
					
			<input type="checkbox" class="chk3">전체약관 동의하기
					
				<button id="modal_submit_btn5">결제하기</button>
				<button id="modal_close_btn5">취소</button>
		</div>
		<div class="modal_layer"></div>
	</div>
	







	
	<script>
	
	$(document).ready(function() {
	
	
		
		$("#modal_open_btn").click(function() {
			$("#modal").attr("style", "display:block");
		});

		$("#modal_close_btn").click(function() {
			$("#modal").attr("style", "display:none");
		});
		
		
		$("#modal_open_btn2").click(function() {
			$("#modal2").attr("style", "display:block");
		});

		$("#modal_close_btn2").click(function() {
			$("#modal2").attr("style", "display:none");
		});
		
		
		$("#modal_open_btn3").click(function() {
			$("#modal3").attr("style", "display:block");
		});

		$("#modal_close_btn3").click(function() {
			$("#modal3").attr("style", "display:none");
		});
		
		
		$("#modal_open_btn4").click(function() {
			$("#modal4").attr("style", "display:block");
		});

		$("#modal_close_btn4").click(function() {
			$("#modal4").attr("style", "display:none");
		});
		
		$("#modal_open_btn5").click(function() {
			$("#modal5").attr("style", "display:block");
		});

		$("#modal_close_btn5").click(function() {
			$("#modal5").attr("style", "display:none");
		});
		
		
/* 		// 사용 포인트 결제액에 바로 반영 // 
		$("#usepoint").on("input", function() {
		    var usepoint = parseInt($(this).val()); // 입력된 usepoint 값을 정수로 파싱
		    var newPamount = 20000 - usepoint;
		    
		    // newPamount 값을 화면에 업데이트
		    $("#pamountDisplay").text(newPamount);
		});
		 */
		
		// 쿠폰, 관람권 체크 박스 //
		const checkboxes = document.querySelectorAll('.chk');

		checkboxes.forEach(checkbox => {
		    checkbox.addEventListener('change', function() {
		        // 현재 체크된 체크박스만 남기고 나머지 체크박스는 선택을 해제합니다.
		        checkboxes.forEach(cb => {
		            if (cb !== this) {
		                cb.checked = false;
		            }
		        });
		    });
		});
		
		
		// 쿠폰 유효성 검사, 등록 // 
		$("#couponCheck").click(function() {
		    var cCode = parseInt($("#couponCode").val()); // 입력된 usepoint 값을 정수로 파싱
		    var cLength = cCode.toString().length;		   

		    if (cLength != 16) {
	            alert("16자리 숫자로 입력하세요.");
	        } else{
	        $.ajax({
	            url: "/couponChk", 
	            type: "post",
	            data: {"cCode": cCode},
	            dataType:"json",
	            success: function(result) {
	            
	                if (result.result == 1) {
	                    alert("쿠폰 등록 완료");
	                    /*
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
		
		
		var newAmount;
		
		//쿠폰 사용 버튼//
		$("#modal_submit_btn").click(function() {
    	var selectCoupon; // 선택된 쿠폰 코드를 저장할 변수

    // 선택된 체크박스를 찾아서 해당 쿠폰 코드를 가져옵니다.
    $(".chk:checked").each(function() {
        selectCoupon = $(this).data("coupon");
    });

    if (selectCoupon) {
        // 선택된 쿠폰 코드를 서버에 전달하여 discount 값을 가져옵니다.
        $.ajax({
            type: "POST",
            url: "/couDiscount",
            data: { "sCoupon": selectCoupon },
            dataType: "json",
            success: function(response) {
            	var cDiscount = response;// 서버에서 반환된 discount 값

                // 계산 등 필요한 작업 수행
                newAmount = ${ticketPrice.tp_price} - cDiscount;

                // 결과 표시
                
                $("#pamountDisplay").text(newAmount);
                $("#modal").attr("style", "display:none");
            },
            error: function() {
                alert("에러. 다시 시도하세요");
            }
        });
    } else {
        alert("쿠폰을 선택해주세요");
    }
});
		
		 newAmount = ${ticketPrice.tp_price} - cDiscount; // 할인쿠폰, 관람권 적용 후 결재엑 갖고오기
		

		// 포인트 적용버튼 //
		$("#modal_submit_btn3").click(function() {
		   
			var usePoint = parseInt($("#usepoint").val()); // 입력된 usepoint 값을 정수로 파싱
		    //var newPoint = ${havePoint.m_point} - usePoint; // db에 넣을값 (point)
		     
			
		    if (usePoint <= ${havePoint.m_point}) { 	
		    // var pamountFix = parseInt($("#pamountDisplay").text())
		    var newPamount2 = newAmount - usePoint;
		    	 $("#pamountDisplay").text(newPamount2);
		    	 $("#modal3").attr("style", "display:none");
		    	 
		    } else if(isNaN(usePoint)){
		        alert("사용하실 포인트 금액을 입력해주세요");
		       	return false;
		    } else {
		    	alert("보유하신 포인트 금액을 초과했습니다");
		    	return false;
		    }
		});
		 
		 
	});
		 
		 //일반 결제 승인 버튼//
		 $("#modal_submit_btn4").click(function(){
			 var cardSelect = $("#cardSelect option:selected").val();
			 var cardNum = $("#cardNum").val();
			 var cardExp = $("#cardExp").val();
			 var cardPw = $("#cardPw").val();
			 var cardBir = $("#cardBir").val();
			 
			 $.ajax({
		            url: "/charge", 
		            type: "post",
		            data: {"cardSelect": cardSelect,
		            	"cardNum": cardNum,
		            	"cardExp": cardExp,
		            	"cardPw": cardPw,
		            	"cardBir": cardBir},
		            dataType:"json",
		            success: function(result){
		            	 if (result.result == "success") {
		                     alert("카드 정보가 일치합니다.");
		                 } else if(result.result == "1") {
		                     alert("카드 번호가 잘못되었습니다 ");
		                 }else if(result.result == "2") {
		                     alert("카드 유효기간이 잘못되었습니다 ");
		                 }else if(result.result == "3") {
		                     alert("카드 비밀번호가 잘못되었습니다 ");
		                 } else{
		                	 alert("생년월일이 일치하지 않습니다 ");
		                 }
		            },
		            error: function() {
		                alert("에러. 다시 시도하세요");
		            }
		 });
		 
	});
	
	</script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>