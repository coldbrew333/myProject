<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%-- include head.jsp --%>
<jsp:include page="/WEB-INF/views/include/head.jsp" />
</head>
<body>
	<%-- include topNavbar.jsp --%>
	<jsp:include page="/WEB-INF/views/include/topNavbar.jsp" />
	<div class="content">
		<br>
		<h3 class="contentTitle">공지사항</h3>
		<div class="contentDetail">
			<img alt="" style="width: 100%" src="/resources/images/note1.jpeg">
			<br> <br><img alt="" style="width: 100%"
				src="/resources/images/note2.jpeg">
		</div>
<br>
<br>
<br>
<br>
	</div>
	<div class="content description">
		<h3 class="contentTitle">공연상세 / 캐스팅일정</h3>
		<div class="contentDetail">
		<img alt="" style="width: 100%" src="/resources/images/note3.jpeg">
		<br>
		<br>
		<img alt="" style="width: 100%" src="/resources/images/note4.jpeg">
		</div>
	</div>

<br>
<br>
<br>
<br>
<div style="text-align:center">

   <a class="btn btn-danger btn-lg " href="https://tickets.interpark.com/goods/15010861" role="button">인터파크 티켓</a>
      <a class="btn btn-danger btn-lg" href="http://ticket.yes24.com/Perf/37085" role="button">yes24공연</a>
</div>




	<%--  include bottomFooter.jsp  --%>
	<jsp:include page="/WEB-INF/views/include/bottomFooter.jsp" />


	<%--      include javascripts.jsp  --%>
	<jsp:include page="/WEB-INF/views/include/javascripts.jsp" />

</body>
</html>