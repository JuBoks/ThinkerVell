<%@page import="vo.FavorBean"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="vo.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%
	UserBean myPage = (UserBean)request.getAttribute("userBean");
	
	// 주소 가져와서 주소와 상세주소로 나누기
	StringTokenizer st = new StringTokenizer(myPage.getUser_address(), ":");
	String user_id=myPage.getUser_id();
	
	FavorBean fb = (FavorBean)request.getAttribute("favorBean");
	System.out.println("acidity"+fb.getUser_favor_acidity());
	
	String id = session.getAttribute("id").toString();
	%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>My Page ─ Café TinkerVell</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Josefin+Sans:400,700"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Great+Vibes"
	rel="stylesheet">

<link rel="stylesheet" href="./css/open-iconic-bootstrap.min.css">
<link rel="stylesheet" href="./css/animate.css">

<link rel="stylesheet" href="./css/owl.carousel.min.css">
<link rel="stylesheet" href="./css/owl.theme.default.min.css">
<link rel="stylesheet" href="./css/magnific-popup.css">

<link rel="stylesheet" href="./css/aos.css">

<link rel="stylesheet" href="./css/ionicons.min.css">

<link rel="stylesheet" href="./css/bootstrap-datepicker.css">
<link rel="stylesheet" href="./css/jquery.timepicker.css">


<link rel="stylesheet" href="./css/flaticon.css">
<link rel="stylesheet" href="./css/icomoon.css">
<link rel="stylesheet" href="./css/style.css">
<link rel="stylesheet" href="./css/shop.css">
<link rel="stylesheet" href="./css/kakaoTalkChat.css">

<style type="text/css">
 .btn btn-primary py-3 px-4 { 
 	text-align: right 
 } 
 .col-lg-3 { 
     flex: 0 0 20% !important; 
     max-width: 20% !important; 
     } 
 .col-md-10 { 
     flex: 0 0 100%; 
     max-width: 100%; 
     } 
   #section-counter{  
   	padding:50px;  
   }  
  #h0528{display:inline-block;} 
  #div0528{display:inline-block; 
  		margin-left:80px;} 
</style>
</head>
<body>
	<header>
		<jsp:include page="../inc/header.jsp" />
	</header>


	<section class="home-slider owl-carousel">

		<div class="slider-item"
			style="background-image: url(./images/coffeecup.jpg);">
			<div class="overlay"></div>
			<div class="container">
				<div
					class="row slider-text justify-content-center align-items-center">

					<div class="col-md-7 col-sm-12 text-center ftco-animate">
						<h1 class="mb-3 mt-5 bread">MY PAGE</h1>
					</div>

				</div>
			</div>
		</div>
	</section>

<!-- 카카오톡 상담 -->
<jsp:include page="../inc/kakaoChat.jsp"/>
<!-- 카카오톡 상담 End -->

	<section class="ftco-section" id="information">
		<div class="container">
			<div class="row">
				<div class="col-xl-8 ftco-animate" style="margin: auto;">
					<form action="UpdateMemberFormAction.us"
						class="billing-form ftco-bg-dark p-3 p-md-5" id="frm">
						<h3 class="mb-4 billing-heading">회원 정보</h3>
						<div class="row align-items-end">
							<div class="col-md-6">
								<div class="form-group">
									<label for="firstname">아이디</label>
									<p><%=myPage.getUser_id() %></p>
								</div>
							</div>
							<div class="w-100"></div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="firstname">이름</label>
									<p><%=myPage.getUser_name() %></p>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="firstname">나이</label>
									<p><%=myPage.getUser_age() %></p>
								</div>
							</div>
							<div class="w-100"></div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="phone">연락처</label>
									<p><%=myPage.getUser_phone() %></p>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="emailaddress">Email</label>
									<p><%=myPage.getUser_email() %></p>
								</div>
							</div>
							<div class="w-100"></div>

							<div class="col-md-6">
								<div class="form-group">
									<label for="postcodezip">우편번호</label>
									<p><%=myPage.getUser_post() %></p>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
								</div>
							</div>
							<div class="w-100"></div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="streetaddress">주소</label>
									<p><%=st.nextToken() %></p>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="streetaddress">상세주소</label>
									<p><%=st.nextToken() %></p>
								</div>
							</div>
							

							<div class="w-100"></div>
							<div class="col-md-12">
								<div class="form-group mt-4">

									<a href="#"
										class="btn btn-primary p-3 px-xl-4 py-xl-3" onclick="document.getElementById('frm').submit();">정보수정</a>

<!-- 								<a href="delete_member.us" -->
<!-- 									class="btn btn-white btn-outline-white p-3 px-xl-4 py-xl-3">회원 -->
<!-- 									탈퇴</a> -->
									<a href="delete_member.us"
									class="btn btn-primary p-3 px-xl-4 py-xl-3">회원
									탈퇴</a>
									
									<a href="confirmCheckoutList.sh"
									class="btn btn-primary p-3 px-xl-4 py-xl-3" >주문 조회
								</a>

<!-- 									<a href="#" class="btn btn-primary p-3 px-xl-4 py-xl-3" onclick="document.getElementById('frm').submit();">정보수정</a> -->
<!-- 								    <a href="delete_member.us" class="btn btn-white btn-outline-white p-3 px-xl-4 py-xl-3">회원탈퇴</a> -->
<!-- 									<a href="confirmCheckoutList.sh" class="btn btn-white btn-outline-white p-3 px-xl-4 py-xl-3" >주문 조회</a> -->
									<%if(id.equals("admin")){%>
									<a href="adminPage.us" class="btn btn-white btn-outline-white p-3 px-xl-4 py-xl-3">관리자 페이지로 이동</a>
									<%}	%>

								
							</div>
						</div>
				</div>
				
				</form>
				<!-- END -->
				
<!-- 				<br> -->
				
				<section class="ftco-counter ftco-bg-dark img" id="section-counter"  data-stellar-background-ratio="0.5" style="border-top: solid 2px gray;">
				
			<div class="overlay"></div>
      <div class="container">
				<h3 class="mb-4 billing-heading" id="h0528">취향 정보</h3>


				<div class="col-md-8" id="div0528">
				<a href="taste.us" class="btn btn-primary btn-outline-primary" style="float: right;">취향 정보 수정</a>
				</div>
			<div class="row justify-content-center">
        	<div class="col-md-10">
        		<div class="row">
		          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate">
		            <div class="block-18 text-center">
		              <div class="text">
		              		<span>aroma</span>
		              	<strong class="number" data-number="<%=fb.getUser_favor_aroma() %>">0</strong>
		              <div class="icon"><span class="flaticon-coffee-cup"></span></div>
		              </div>
		            </div>
		          </div>
		          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate">
		            <div class="block-18 text-center">
		              <div class="text">
		              <span>acidity</span>	
		              	<strong class="number" data-number="<%=fb.getUser_favor_acidity() %>">0</strong>
		              	<div class="icon"><span class="flaticon-coffee-cup"></span></div>
		              </div>
		            </div>
		          </div>
		          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate">
		            <div class="block-18 text-center">
		              <div class="text">
		              	<span>sweet</span>
		              	<strong class="number" data-number="<%=fb.getUser_favor_sweetness() %>">0</strong>
		              	<div class="icon"><span class="flaticon-coffee-cup"></span></div>
		              </div>
		            </div>
		          </div>
		          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate">
		            <div class="block-18 text-center">
		              <div class="text">
		              	<span>bitter</span>
		              	<strong class="number" data-number="<%=fb.getUser_favor_bitterness() %>">0</strong>
		              	<div class="icon"><span class="flaticon-coffee-cup"></span></div>
		              </div>
		            </div>
		          </div>
		          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate">
		            <div class="block-18 text-center">
		              <div class="text">
		              	<span>body</span>
		              	<strong class="number" data-number="<%=fb.getUser_favor_body() %>">0</strong>
		              	<div class="icon"><span class="flaticon-coffee-cup"></span></div>
		              </div>
		            </div>
		          </div>
		        </div>
		      </div>
        </div>
        </div>
        </section>
        
			</div>
		</div>
	</div>	
	</section>

	<footer class="ftco-footer ftco-section img">
		<jsp:include page="../inc/footer.jsp"></jsp:include>
	</footer>



	<!-- loader -->
	<div id="ftco-loader" class="show fullscreen">
		<svg class="circular" width="48px" height="48px">
			<circle class="path-bg" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke="#eeeeee" />
			<circle class="path" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg>
	</div>


	<script src="./js/jquery.min.js"></script>
	<script src="./js/jquery-migrate-3.0.1.min.js"></script>
	<script src="./js/popper.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/jquery.easing.1.3.js"></script>
	<script src="./js/jquery.waypoints.min.js"></script>
	<script src="./js/jquery.stellar.min.js"></script>
	<script src="./js/owl.carousel.min.js"></script>
	<script src="./js/jquery.magnific-popup.min.js"></script>
	<script src="./js/aos.js"></script>
	<script src="./js/jquery.animateNumber.min.js"></script>
	<script src="./js/bootstrap-datepicker.js"></script>
	<script src="./js/jquery.timepicker.min.js"></script>
	<script src="./js/scrollax.min.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
	<script src="./js/google-map.js"></script>
	<script src="./js/main.js"></script>



</body>
</html>
