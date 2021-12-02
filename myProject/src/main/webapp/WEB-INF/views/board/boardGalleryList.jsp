<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    
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
            <h5>갤러리 이미지 (글개수: ${ pageMaker.totalCount })</h5>

            <hr class="featurette-divider">
              
            <div class="clearfix"></div>

            <!-- 글목록 테이블 -->
    		
              <c:choose>
              	<c:when test="${ pageMaker.totalCount gt 0 }">
         		<div class="container" align="center"> 
           			<div class = "row">
              		<c:forEach var="attach" items="${ attachList }">
	            		<c:set var="fileCallPath" value="${ attach.uploadpath }/s_${ attach.uuid }_${ attach.filename }" />
               			<c:set var="originPath" value="${ attach.uploadpath }/${ attach.uuid }_${ attach.filename }" />
            				<div class = "col-sm-3">
           					<a href="/display?fileName=${ originPath }">
           						<img src="/display?fileName=${ fileCallPath }" class="img-thumbnail" style="width: 200px; height: 200px;">
           					</a>
            				</div>
              		</c:forEach>
              		</div>
                </div>
              	</c:when>
              	<c:otherwise>
              	  <h4> 갤러리 이미지가 없습니다.</h4>
	               
              	</c:otherwise>
              </c:choose>
  


            <!-- pagination area -->
            <nav aria-label="Page navigation example">
              <ul class="pagination justify-content-center my-4">
              
              <%-- 이전 --%>
              <li class="page-item ${ (pageMaker.prev) ? '' : 'disabled' }">
              	<a class="page-link" href="${ (pageMaker.prev) ? '/board/gallery?pageNum=' += (pageMaker.startPage - 1) += '&type=' += pageMaker.cri.type += '&keyword=' += pageMaker.cri.keyword : '' }#board">이전</a>
              </li>
              
              <%-- 시작페이지 번호 ~ 끝페이지 번호 --%>
              <c:forEach var="i" begin="${ pageMaker.startPage }" end="${ pageMaker.endPage}" step="1">
              	<li class="page-item ${ (pageMaker.cri.pageNum eq i) ? 'active' : '' }">
              		<a class="page-link" href="/board/gallery?pageNum=${ i }&type=${ pageMaker.cri.type }&keyword=${ pageMaker.cri.keyword }#board">${ i }</a>
              	</li>
              </c:forEach>
              
              <%-- 다음 --%>
              <li class="page-item ${ (pageMaker.next) ? '' : 'disabled' }">
              	<a class="page-link" href="${ (pageMaker.next) ? '/board/gallery?pageNum=' += (pageMaker.endPage + 1) += '&type=' += pageMaker.cri.type += '&keyword=' += pageMaker.cri.keyword : '' }#board">다음</a>
              </li>

              </ul>
            </nav>
            <!-- end of pagination area -->

            <hr class="featurette-divider">

            <!-- end of Search area -->
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

</body>
</html>