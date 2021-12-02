<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko" class="h-100">
<head>
    <%-- include head.jsp --%>
    <jsp:include page="/WEB-INF/views/include/head.jsp" />
    
    <!-- Custom styles for this template -->
    <link href="/resources/css/signin.css" rel="stylesheet">
</head>
<body class="text-center">


  <form class="form-signin" action="/member/remove" id = "frm" method="POST">
    <a href="/" title="Home으로 가기">
      <i class="material-icons display-4">android</i>
    </a>
    <h1 class="h3 mb-3 font-weight-normal">회원 탈퇴</h1>
    
    <label for="inputPassword" class="sr-only">Password</label>
   	<input type="hidden" name="id" value="${ sessionScope.id }">
    <input type="password" name="passwd" id="inputPassword" class="form-control" placeholder="비밀번호" required>

    <button class="btn btn-lg btn-primary btn-block" type="submit">
      <i class="material-icons align-middle">login</i>
      <span class="align-middle">회원탈퇴</span>
    </button>

    <hr class="featurette-divider">

    
    <p class="mt-5 mb-3 text-muted">&copy; 2017-2021</p>
  </form>



  	<%-- include javascripts.jsp --%>
    <jsp:include page="/WEB-INF/views/include/javascripts.jsp" />

	<script>
		var frm = document.querySelector('#frm');
		
		frm.addEventListener('submit', function (event){
			
			event.preventDefault();
			
			var isRemove = confirm('정말 회원탈퇴 하시겠습니까?');
			
			if(!isRemove){
				return;
			}
			
			frm.submit();
			
		});
	
	</script>
</body>
</html>