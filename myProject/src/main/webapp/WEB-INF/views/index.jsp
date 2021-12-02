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


    <!-- Carousel -->
    <div id="carouselExampleControls" class="carousel slide container" data-ride="carousel">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img src="/resources/images/werther1.png" class="d-block w-100">
        </div>
        <div class="carousel-item">
          <img src="/resources/images/werther2.jpeg" class="d-block w-100">
        </div>
        <div class="carousel-item">
          <img src="/resources/images/werther3.png" class="d-block w-100">
        </div>
        <div class="carousel-item">
          <img src="/resources/images/werther4.jpeg" class="d-block w-100">
        </div>
      </div>
      <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div>
    <!-- end of Carousel -->



    

    <div class="container mt-5">
      <!-- Card Deck -->
      <div class="card-deck">
        <div class="card">
          <img src="/resources/images/werther5.jpeg" class="card-img-top">
          <div class="card-body">
            <h5 class="card-title">창작 20주년, 30만 관객의 사랑 </h5>
            <p class="card-text">관객이 선택한 한국대표 <br>창작 뮤지컬</p>
            <p class="card-text"><small class="text-muted">세기를 넘어 꾸준히 사랑받는 순수하고도 <br>격정적인 베르테르의 사랑!
            관객들의 무한한 사랑과 지지를 바탕으로 이어온<베르테르>가 20주년을 맞아, 모두의 가슴 속에 여운을 남길 새로운 클래식이 되다.</small></p>
          </div>
        </div>
        <div class="card">
          <img src="/resources/images/werther6.jpeg" class="card-img-top">
          <div class="card-body">
            <h5 class="card-title">5년의 기다림, 그리고 20주년!</h5>
            <p class="card-text">모두가 기다려 온 완벽한 CAST</p>
            <p class="card-text"><small class="text-muted">섬세하고 감성 깊은 베르테르로 돌아 온 엄기준<br>
            감미로운 목소리로 여심을 사로 잡는 카이<br>순수하고 설레는, 지고지순한 사랑의 아이콘 유연석<br>
            풋풋하면서도 더 깊어진 감서으로 돌아 온 규현<br> tvN더블캐스팅 최종 우승, 떠오르는 신예 나현우<br>
            싱그러운 매력을 선보일 김예원<br>더욱 깊어진 감수성을 보여줄 이지혜까지<br>최고의 배우들이 보여줄 20주년공연, 완벽한 이야기</small></p>
          </div>
        </div>
        <div class="card">
          <img src="/resources/images/werther7.jpeg" class="card-img-top">
          <div class="card-body">
            <h5 class="card-title">올 가을 모두의 감동</h5>
            <p class="card-text">짙은 사랑의 여운</p>
            <p class="card-text"><small class="text-muted">한 폭의 수채화를 보는 듯한 아름다운 연출<br>
            서정적인 선율의 실내악 오케스트라, 짙은 감성과 현대적이고 은유적인 무대가 만나 20주년을 맞이한<br><베르테르>의 새로운 페이지를 열어간다.</small></p>
          </div>
        </div>
      </div>
      <!-- end of Card Deck -->
    </div>



    <!-- Jumbotron -->
    <div class="jumbotron container mt-5">
      <h1 class="display-4">Musical Werther</h1>
      <p class="lead">여전히 당신을 바랍니다.</p>
      <hr class="my-4">
      <p>어느 날 베르테르는 자석산에 대한 인형극을 하며 신비한 모험에 들뜬 롯데의 싱그러움에 단숨에 매료되고 롯데는 시에 공감하는 베르테르에게 유대감을 느낀다. 베르테르는 롯데에 대한 사랑을 확신하지만 약혼자 알베르트가 있다는 사실에 마음이 무너진다. 알베르트는 롯데와의 평화로운 삶을 지키려 하고 베르테르는 차마 그들의 행복을 지켜볼 수 없어 떠난다. 그러나 긴 여행 끝에도 롯데를 잊지 못해 발하임으로 돌아오는데…</p>
      <a class="btn btn-danger btn-lg" href="/board/detail" role="button">자세히</a>
    </div>
    <!-- end of Jumbotron -->


    
   <%--  include bottomFooter.jsp  --%>
	<jsp:include page="/WEB-INF/views/include/bottomFooter.jsp" />

    
<%--      include javascripts.jsp  --%>
    <jsp:include page="/WEB-INF/views/include/javascripts.jsp" />

</body>
</html>