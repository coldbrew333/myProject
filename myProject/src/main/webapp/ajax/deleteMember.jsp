<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>회원정보 한개 삭제하기</h1>
<hr>

<input type="text" placeholder="삭제할 아이디 입력" id="id">
<button type="button" id="btn">회원삭제</button>
<p id="message"></p>

<ul></ul>

<script src="/resources/js/jquery-3.6.0.js"></script>
<script>
	$('button#btn').on('click', function () {
		
		let id = $('#id').val();
		console.log('id : ' + id);
		
		// ajax 함수 호출 - 비동기 자바스크립트 통신
		$.ajax({
			url: '/api/members/' + id,
			method: 'DELETE',
			success: function (data) {
				console.log(typeof data);  // string
				console.log(data);  // success
				
				$('p#message').html(id + ' : 회원정보 삭제 성공!').css('color', 'green');
			},
			error: function (request, status, error) {
				alert('code: ' + request.status + '\n message: ' + request.responseText + '\n error: ' + error);
				
				$('p#message').html(id + ' : 회원정보 삭제 실패...').css('color', 'red');
			}
		});
	});
</script>
</body>
</html>










