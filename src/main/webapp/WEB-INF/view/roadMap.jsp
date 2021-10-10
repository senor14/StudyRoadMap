<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    /* 커리어 로드맵 이름 */
    String CRM_name = "Career_RoadMap"; /* (String)request.getAttribute("") */
    /* 사용자 영문 이름 */
    String en_name = "SeungBeom Kim"; /* (String)request.getAttribute("") */
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>redirect</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/career_roadMap/career_roadMap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/career_roadMap/modal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/career_roadMap/career_card.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>

<div class="timeline-container" id="timeline-1">
    <div class="timeline-header">
        <h2 class="timeline-header__title"><%=CRM_name %></h2>
        <h3 class="timeline-header__subtitle"><%=en_name %></h3>
    </div>
    <div class="timeline">

        <div class="timeline-item" data-text="Career Card">
            <a href="#m1-o" <%--id="m1-c"--%>>
                <div class="timeline__content"><img class="timeline__img" src="https://coolessay.s3.amazonaws.com/summarizing.jpg" />
                    <h2 class="timeline__content-title">Career Card</h2>
                </div>
            </a>
        </div>


        <div class="timeline-item" data-text="Education">
            <a onclick="fnOpenModal('education');">
            <div class="timeline__content"><img class="timeline__img" src="https://api.time.com/wp-content/uploads/2015/11/151109_col_coverdell.jpg" />
                <h2 class="timeline__content-title">Education</h2>
            </div>
            </a>
        </div>
        <div class="timeline-item" data-text="Work Experience" >
            <div class="timeline__content">
                <a onclick="fnOpenModal('work_experience');">
                <img class="timeline__img" src="https://d3kqdc25i4tl0t.cloudfront.net/articles/content/414_629145_190919resumegraphics_TopResume_hero.jpg" />
                <h2 class="timeline__content-title">Work Experience</h2>
                </a>
            </div>
        </div>
        <div class="timeline-item" data-text="Certificate">
            <a onclick="fnOpenModal('certificate');">
                <div class="timeline__content"><img class="timeline__img" src="https://www.worldofpumps.com/certificates/certificate-wall-golden.jpg" />
                <h2 class="timeline__content-title">Certificate</h2>
            </div>
            </a>
        </div>
        <div class="timeline-item" data-text="Awards">
<%--            <a href="#m2-o" &lt;%&ndash;id="m4-c"&ndash;%&gt;>--%>
            <a onclick="fnOpenModal('awards');">
            <div class="timeline__content"><img class="timeline__img" src="https://olc-wordpress-assets.s3.amazonaws.com/uploads/2021/04/OLC-Awards-Thumbnail-1200x800.jpg" />
                <h2 class="timeline__content-title">Awards</h2>
            </div>
            </a>
        </div>
    </div>
</div>

<div class="demo-footer"><a href="http://www.turkishnews.com/Ataturk/life.htm" target="_blank">Source/Kaynak</a></div>


<%-- modal --%>
<div class="modal-container" id="m2-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <h1 class="modal__title">Modal 2 Title</h1>
        <p class="modal__text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Facilis ex dicta maiores libero minus obcaecati iste optio, eius labore repellendus.</p>
        <button class="modal__btn">Button &rarr;</button>
        <button class="modal__btn">Button &rarr;</button>
        <a onclick="fnCloseModal();" class="link-2"></a>
    </div>
</div>

<div class="modal-container" id="m1-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <a href="#" class="link-2"></a>
    <div class="team-boxed">
        <div class="item">
            <div class="box">
                    <div class="user">
                        <img class="rounded-circle" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKHt5PvtslCa0tCgJoB3gMxjLR51dYf7exhA&usqp=CAU">
                        <h3 class="name">홍기현</h3>
                        <p class="email">Email : 홍기현@google.com</p>
                    </div>
                    <div class="info">
                        <p class="description">
                            2012.08.10 : 졸업
                        </p>
                        <p class="description">
                            2012.08.10 : 졸업
                        </p>
                    </div>
            </div>
        </div>
    </div>
</div>

</body>
<script src="${pageContext.request.contextPath}/resources/js/career_roadMap/career_roadMap.js"></script>

<%-- 모달 조작 함수 --%>
<script>
    // 모달 오픈
    function fnOpenModal(type){
        console.log(type);
        $('#m2-o').css("display", "flex");
    }

    // 모달 종료
    function fnCloseModal(){
        $('#m2-o').css("display", "none");
    }
</script>
</html>