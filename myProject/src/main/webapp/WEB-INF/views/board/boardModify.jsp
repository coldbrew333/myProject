<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    
<!DOCTYPE html>
<html lang="ko">
<head>
    <%-- include head.jsp --%>
    <jsp:include page="/WEB-INF/views/include/head.jsp" />
</head>
<body>
    <%-- include topNavbar.jsp --%>
	<jsp:include page="/WEB-INF/views/include/topNavbar.jsp" />


    
    <!-- middle container -->
    <div class="container mt-4">
      <div class="row">

        <jsp:include page="/WEB-INF/views/include/left.jsp" />

        <!-- Right area -->
        <div class="col-sm-9">
          
          <!-- Contents area -->
          <div class="border border-info p-4 rounded">
            <h5>게시판 글수정</h5>

            <hr class="featurette-divider">

            <form action="/board/modify" method="POST" enctype="multipart/form-data">
              <input type="hidden" name="pageNum" value="${ pageNum }">
              <input type="hidden" name="num" value="${ board.num }">
            	
              <div class="form-group">
                <label for="id">아이디</label>
                <input type="text" class="form-control" id="id" aria-describedby="idHelp" name="mid" value="${ sessionScope.id }" readonly>
              </div>

              <div class="form-group">
                <label for="subject">제목</label>
                <input type="text" class="form-control" id="subject" name="subject" value="${ board.subject }" autofocus>
              </div>

              <div class="form-group">
                <label for="content">내용</label>
                <textarea class="form-control" id="content" rows="10" name="content">${ board.content }</textarea>
              </div>

			  
			  <button type="button" class="btn btn-primary my-3" id="btnAddFile">파일 추가</button>
			  
			  <div><span>첨부 파일</span></div>
			  
			  
			  <!-- 기존 첨부파일 영역. 삭제할 파일정보 표현 용도 -->
			  <div id="oldFileBox">
			  
			  <!-- .delete-oldfile 삭제버튼 클릭 시 hidden input 요소의 name 속성을 oldfile → delfile 로 수정함 -->
			  <!-- 서버에서는 oldfile은 찾지않고 delfile만 찾아서 파일 삭제처리 -->
			  <c:forEach var="attach" items="${ attachList }">
			  	<input type="hidden" name="oldfile" value="${ attach.uuid }">
			  	<div>
			  		<span>${ attach.filename }</span>
			  		<button type="button" class="btn btn-primary btn-sm delete-oldfile">
	                	<i class="material-icons align-middle">clear</i>
	                	<span class="align-middle">삭제</span>
	                </button>
			  	</div>
			  </c:forEach>
			  
			  </div>
			  
			  
			  <!-- 신규 첨부파일 영역. 새로 첨부될 파일 업로드 용도 -->
			  <div id="newFileBox"></div>
              

              <div class="my-4 text-center">
                <button type="submit" class="btn btn-primary">
                  <i class="material-icons align-middle">create</i>
                  <span class="align-middle">글수정</span>
                </button>
                <button type="reset" class="btn btn-primary ml-3">
                  <i class="material-icons align-middle">clear</i>
                  <span class="align-middle">초기화</span>
                </button>
                <button type="button" class="btn btn-primary ml-3" onclick="location.href = '/board/list?pageNum=${ pageNum }';">
                  <i class="material-icons align-middle">list</i>
                  <span class="align-middle">글목록</span>
                </button>
              </div>
            </form>

          </div>
          <!-- end of Contents area -->
        </div>
        <!-- end of Right area -->
      </div>
    </div>
    <!-- end of middle container -->



    <%-- include bottomFooter.jsp --%>
	<jsp:include page="/WEB-INF/views/include/bottomFooter.jsp" />

    
    <%-- include javascripts.jsp --%>
    <jsp:include page="/WEB-INF/views/include/javascripts.jsp" />


	<script>
	const MAX_FILE_COUNT = 5;
	let fileCount = ${ fn:length(attachList) };  // 화면에 보이는 file 입력상자 개수
	
	
	// 기존 첨부파일 영역에 있는 삭제버튼 클릭했을 때
	$('button.delete-oldfile').on('click', function (event) {
		//let btn = event.target  // 이벤트 소스: 이벤트가 발생한 객체
		
		// 현재 클릭이 발생한 버튼(this)을 기준으로
		// 직계부모 div 요소의 이전(prev)요소인 hidden input 요소를 참조하여
		// name 속성값을 기존 oldfile에서 delfile로 수정하기
		$(this).parent().prev().prop('name', 'delfile');
		
		$(this).parent().remove(); // 현재 클릭한 버튼의 직계부모 div 요소를 삭제하기
		fileCount--;
	});
	
	
	// jQuuery 방식 이벤트 처리
	// 정적 이벤트 연결
	$('#btnAddFile').on('click', function (event) {
		if (fileCount >= MAX_FILE_COUNT) {
			alert('첨부파일은 최대 5개 까지만 첨부할 수 있습니다.')
			return;
		}
		
		var str = `
			<div class="my-2">
                <input type="file" name="files" multiple>
                <button type="button" class="btn btn-primary btn-sm delete-addfile">
                	<i class="material-icons align-middle">clear</i>
                	<span class="align-middle">삭제</span>
                </button>
            </div>
		`;
		$('div#newFileBox').append(str);
		
		fileCount++;
	});
	
	
	// 동적 이벤트 연결 - 이벤트 등록을 이미 존재하는 요소에게 위임하는 방식
	$('div#newFileBox').on('click', 'button.delete-addfile', function (event) {
		//event.target; // 실제 이벤트가 발생한 오브젝트
		
		$(this).closest('div').remove();  // empty()와 구분 주의!
		//$(this).parent().remove();
		
		fileCount--;
	});
	</script>
</body>
</html>










