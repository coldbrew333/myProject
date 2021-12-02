<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
    <div class="container-fluid bg-light pt-3 pb-1">
    	<div class="container text-right text-secondary" style="font-size: 13px;">
    	<c:choose>
    		<c:when test="${ not empty sessionScope.id }"><%-- 로그인 했을때 --%>
    		<c:set var="fileCallPath" value="${ profilePicVO.uploadpath }/${profilePicVO.mid}/s_${ profilePicVO.uuid }_${ profilePicVO.filename }" />
    		<div class="box" style="background: #BDBDBD; margin-left: 75%;">
    		<img class="profile" src="/display?fileName=${fileCallPath}"  class="img-thumbnail">
			</div>
				<span>${ sessionScope.id } 님</span>
    			<a href="/member/logout" class="mx-2">로그아웃</a>
    		</c:when>
    		<c:otherwise><%-- 로그인 안했을때 --%>
    			<a href="/member/login" class="mx-2">로그인</a> | 
    			<a href="/member/join" class="mx-2">회원가입</a>
    		</c:otherwise>
    	</c:choose>
    	</div>
    </div>
    
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container">

        <a class="navbar-brand" href="/">
          <i> <img src="/resources/images/logo.png" class="logo" width="50" height="50"></i>
          Werther
        </a>
        
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <a class="nav-link" href="/">Home</a>
            </li>

			<c:if test="${ not empty sessionScope.id }"><%-- 로그인 했을때 --%>
				<li class="nav-item dropdown">
	              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                내정보 관리
	              </a>
	              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
	                <a class="dropdown-item" href="/member/passwd">비밀번호 변경</a>
	                <a class="dropdown-item" href="/member/modify?id=${sessionScope.id}">내정보 수정</a>
	                <div class="dropdown-divider"></div>
	                <a class="dropdown-item" href="/member/remove">회원탈퇴</a>
	              </div>
	            </li>
			</c:if>

            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                게시판
              </a>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="/board/list">게시판</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/board/gallery">갤러리</a>
              </div>
            </li>

           <!--  <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                채팅 서비스
              </a>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="#">심플 채팅</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">채팅방 목록</a>
              </div>
            </li> -->

           <!--  <li class="nav-item">
              <a class="nav-link disabled" href="/note/note">Disabled</a>
            </li> -->
          </ul>
          
        </div>
      </div>
    </nav>
    <!-- end of Navbar -->
    