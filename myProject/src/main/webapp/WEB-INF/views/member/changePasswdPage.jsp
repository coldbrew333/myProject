<%@  page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%-- include head.jsp --%>
    <jsp:include page="/WEB-INF/views/include/head.jsp" />
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%-- include topNavbar.jsp --%>
	<jsp:include page="/WEB-INF/views/include/topNavbar.jsp" />


	
	
	<div class = "container">
	<!-- Contents area -->
	    <div class="border border-info p-4 rounded">
	      <h5>비밀번호 변경</h5>
	
	      <hr class="featurette-divider">
	
	      <form action="/member/passwd" method="POST">
	      	 <input type="hidden" name="id" value="${ sessionScope.id }">
	        <div class="form-group">
	          <label for="id">
	            <i class="material-icons align-middle">lock</i>
	            <span class="align-middle">현재 비밀번호</span>
	          </label>
	          <input type="password" class="form-control" id="password" aria-describedby="idHelp" name="password" required autofocus>
	          <small id="idHelp" class="form-text text-muted">현재 비밀번호는 필수 입력 요소입니다.</small>
	        </div>
	
	        <div class="form-group">
	          <label for="password">
	            <i class="material-icons align-middle">lock</i>
	            <span class="align-middle">새 비밀번호</span>
	          </label>
	          <input type="password" class="form-control" id="newPassword" aria-describedby="pwdHelp" name="newPassword" required>
	          <small id="pwdHelp" class="form-text text-muted">새 비밀번호는 필수 입력 요소입니다.</small>
	        </div>
	     	<div class="my-3 text-center">
                <button type="submit" class="btn btn-primary">비밀번호 변경</button>
                <button type="reset" class="btn btn-primary ml-3">초기화</button>
           </div>
	      </form>
	    </div>
	    <!-- end of Contents area -->
	</div>
	
	

 	






    <%-- include bottomFooter.jsp --%>
	<jsp:include page="/WEB-INF/views/include/bottomFooter.jsp" />

    
    <%-- include javascripts.jsp --%>
    <jsp:include page="/WEB-INF/views/include/javascripts.jsp" />

</body>
</html>