<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <%-- include head.jsp --%>
    <jsp:include page="/WEB-INF/views/include/head.jsp" />
    
    <style>
      time.comment-date {
        font-size: 13px;
      }
    </style>
</head>
<body>
    <%-- include topNavbar.jsp --%>
	<jsp:include page="/WEB-INF/views/include/topNavbar.jsp" />



    <!-- middle container -->
    <div class="container mt-4">
      <div class="row">

        <!-- Left Menu -->
        <div class="col-sm-3">

          <jsp:include page="/WEB-INF/views/include/left.jsp" />
        </div>
        <!-- end of Left Menu -->


        <!-- Right area -->
        <div class="col-sm-9">
          
          <!-- Contents area -->
          <div class="border border-info p-4 rounded">
            <h5>게시판 글 상세보기</h5>

            <hr class="featurette-divider">

            <!-- 글 상세보기 영역 -->
            <table class="table table-striped">
              <tr>
                <th scope="row" class="text-center">제목</th>
                <td colspan="5">${ boardVO.subject }</td>
              </tr>
              <tr>
                <th scope="row" class="text-center">작성자</th>
                <td>${ boardVO.mid }</td>
                <th scope="row" class="text-center">작성일</th>
                <td><fmt:formatDate value="${ boardVO.regDate }" pattern="yyyy.MM.dd" /></td>
                <th scope="row" class="text-center">조회수</th>
                <td>${ boardVO.readcount }</td>
              </tr>
              <tr>
                <th scope="row" class="text-center">추천</th>
                <td class="text-primary">264</td>
                <th scope="row" class="text-center">비추천</th>
                <td class="text-danger">7</td>
                <th scope="row" class="text-center">댓글</th>
                <td>70</td>
              </tr>
              <tr>
                <th scope="row" class="text-center">내용</th>
                <td colspan="5">
                  <pre>${ boardVO.content }</pre>
                </td>
              </tr>
              <tr>
                <th scope="row" class="text-center">첨부파일</th>
                <td colspan="5">
                
                <c:choose>
                	<c:when test="${ fn:length(attachList) > 0 }"><%-- 첨부파일 있으면 --%>
                		<ul>
                		
                		<c:forEach var="attach" items="${ attachList }">
                			<c:if test="${ attach.filetype eq 'O' }">
                				<li>
                					<c:set var="fileCallPath" value="${ attach.uploadpath }/${ attach.uuid }_${ attach.filename }" />
                					<a href="/download?fileName=${ fileCallPath }">
                						${ attach.filename }
                					</a>
                				</li>
                			</c:if>
                			<c:if test="${ attach.filetype eq 'I' }">
                				<c:set var="fileCallPath" value="${ attach.uploadpath }/s_${ attach.uuid }_${ attach.filename }" />
                				<c:set var="originPath" value="${ attach.uploadpath }/${ attach.uuid }_${ attach.filename }" />
                				<li>
                					<a href="/display?fileName=${ originPath }">
                						<img src="/display?fileName=${ fileCallPath }">
                					</a>
                				</li>
                			</c:if>
                		</c:forEach>
                		
                		</ul>
                	</c:when>
                	<c:otherwise><%-- 첨부파일 없으면 --%>
                		<span>첨부파일 없음</span>
                	</c:otherwise>
                </c:choose>
                
                </td>
              </tr>
            </table>

            
            <div class="text-right mt-4">
	            <%-- 로그인 사용자일때 --%>
	            <c:if test="${ not empty sessionScope.id }">
	              <%-- 로그인 아이디와 글작성자 아이디가 같을때 --%>
	              <c:if test="${ sessionScope.id eq boardVO.mid }">
	              	  <button type="button" class="btn btn-info btn-sm" onclick="location.href = '/board/modify?num=${ boardVO.num }&pageNum=${ pageNum }';">
		                <i class="material-icons align-middle">edit</i>
		                <span class="align-middle">글수정</span>
		              </button>
		              
		              <button type="button" class="btn btn-info btn-sm ml-3" onclick="remove(event);">
		                <i class="material-icons align-middle">delete</i>
		                <span class="align-middle">글삭제</span>
		              </button>
	              </c:if>
	              
	              <button type="button" class="btn btn-info btn-sm ml-3" onclick="location.href = '/board/reply?reRef=${ boardVO.reRef }&reLev=${ boardVO.reLev }&reSeq=${ boardVO.reSeq }&pageNum=${ pageNum }';">
	                <i class="material-icons align-middle">reply</i>
	                <span class="align-middle">답글쓰기</span>
	              </button>
	            </c:if>
              
	            <button type="button" class="btn btn-info btn-sm ml-3" onclick="location.href = '/board/list?pageNum=${ pageNum }';">
	              <i class="material-icons align-middle">list</i>
	              <span class="align-middle">글목록</span>
	            </button>
            </div>



            <!-- Comment -->
            <div id="comment" class="border border-secondary mt-5 p-3">
              <i class="material-icons">forum</i> 댓글

              <hr class="featurette-divider">

              <ul class="list-unstyled mt-4">
                <li class="media mb-2">
                  <img src="/resources/images/admin.png" width="50" height="50" class="mr-3 rounded-circle">
                  <div class="media-body">
                    <div class="row">
                      <div class="col-md-4">
                        <h6>관리자(user1)</h6>
                      </div>
                      <div class="col-md-8">
                        <div class="text-right text-secondary">
                          <time class="comment-date">2021-07-23 15:07:24</time>
                          | <a href="#!">삭제</a>
                          | <a href="#!">수정</a>
                          | <a href="#!">답글</a>
                        </div>
                      </div>
                    </div>
                    <p>자세히에 보시면 스케줄이 나와있습니다. 확인 부탁 드립니다.</p>
                  </div>
                </li>
                
                <li class="media mb-2">
                  <img src="/resources/images/jay1.jpeg" width="50" height="50" class="mr-3 rounded-circle">
                  <div class="media-body">
                    <div class="row">
                      <div class="col-md-4">
                        <h6>bbb(user2)</h6>
                      </div>
                      <div class="col-md-8">
                        <div class="text-right text-secondary">
                          <time class="comment-date">2021-07-23 15:07:24</time>
                          | <a href="#!">삭제</a>
                          | <a href="#!">수정</a>
                          | <a href="#!">답글</a>
                        </div>
                      </div>
                    </div>
                    <p>저는 베르테르역으로 엄기준배우나 카이 배우가 좋더라구요!</p>
                  </div>
                </li>

                <li class="media mb-2" style="margin-left: 40px;">
                  <i class="material-icons">subdirectory_arrow_right</i>
                  <img src="/resources/images/iu.jpeg" width="50" height="50" class="mr-3 rounded-circle">
                  <div class="media-body">
                    <div class="row">
                      <div class="col-md-4">
                        <h6>aaa (user3)</h6>
                      </div>
                      <div class="col-md-8">
                        <div class="text-right text-secondary">
                          <time class="comment-date">2021-07-23 15:07:24</time>
                          | <a href="#!">삭제</a>
                          | <a href="#!">수정</a>
                          | <a href="#!">답글</a>
                        </div>
                      </div>
                    </div>
                    <p>모두 답변 감사합니다 좋은 하루 되세요!</p>
                  </div>
                </li>

               

              <hr class="featurette-divider">


              <!-- write new comment -->
              <form action="" method="post">
                <div class="row my-4">
                  <div class="col-10">
                    <div class="form-group">
                      <label for="exampleFormControlTextarea1">새댓글 작성</label>
                      <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                    </div>
                  </div>
                  <div class="col-2 align-self-center">
                    <button type="submit" class="btn btn-info btn-sm">작성</button>
                  </div>
                </div>
              </form>
              <!-- end of write new comment -->
            </div>
            <!-- end of Comment -->


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
	// 글삭제 버튼을 클릭했을 때 호출되는 함수
	function remove(event) {
		// 이벤트 소스(이벤트가 발생한 오브젝트)의 기본동작을 못하게 만듬
		// 기본동작을 가진 대표적인 두 태그 : a 태그(클릭 못하게), form 태그(submit 못하게) 
		event.preventDefault();
		
		let isRemove = confirm('이 글을 정말 삭제하시겠습니까?');
		if (isRemove == true) {
			location.href = '/board/remove?num=${ boardVO.num }&pageNum=${ pageNum }';
		}
	}
	</script>
</body>
</html>











