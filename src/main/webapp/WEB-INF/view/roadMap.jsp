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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/career_roadMap/moving_button.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/career_roadMap/plus_input.css">
    <%-- font-awesome(아이콘) --%>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
    <%-- JQUERY --%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <%-- BOOTSTRAP --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <style>
        .modal .year_info{
            max-height: 200px;
            overflow: auto;
        }

        .modal .year_info::-webkit-scrollbar {
            width: 10px;
        }
        .modal .year_info::-webkit-scrollbar-thumb {
            background-color: #2f3542;
            border-radius: 10px;
            background-clip: padding-box;
            border: 2px solid transparent;
        }
        .modal .year_info::-webkit-scrollbar-track {
            background-color: grey;
            border-radius: 10px;
            box-shadow: inset 0px 0px 5px white;
        }

        /*  그리드 연도 정리  */
        .grid_row {
            display: grid;
            grid-template-columns: repeat(16, 1fr);
            grid-gap: 10px;
            word-wrap: break-word;
        }
        .col_center{
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .grid_head{

        }
        .col_year{
            grid-column: 1/3;
        }
        .col_month{
            grid-column: 3;
        }
        .col_day{
            grid-column: 4;
        }
        .col_content{
            grid-column: 5/13;
        }
        .col_check{
            grid-column: 13;
        }
        .col_del{
            grid-column: 14/17;
            padding:1px;
        }
    </style>
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
            <a onclick="fnOpenModal('show','education');">
            <div class="timeline__content"><img class="timeline__img" src="https://api.time.com/wp-content/uploads/2015/11/151109_col_coverdell.jpg" />
                <h2 class="timeline__content-title">Education</h2>
            </div>
            </a>
        </div>
        <div class="timeline-item" data-text="Work Experience" >
            <div class="timeline__content">
                <a onclick="fnOpenModal('show','work_experience');">
                <img class="timeline__img" src="https://d3kqdc25i4tl0t.cloudfront.net/articles/content/414_629145_190919resumegraphics_TopResume_hero.jpg" />
                <h2 class="timeline__content-title">Work Experience</h2>
                </a>
            </div>
        </div>
        <div class="timeline-item" data-text="Certificate">
            <a onclick="fnOpenModal('show','certificate');">
                <div class="timeline__content"><img class="timeline__img" src="https://www.worldofpumps.com/certificates/certificate-wall-golden.jpg" />
                <h2 class="timeline__content-title">Certificate</h2>
            </div>
            </a>
        </div>
        <div class="timeline-item" data-text="Awards">
<%--            <a href="#m2-o" &lt;%&ndash;id="m4-c"&ndash;%&gt;>--%>
            <a onclick="fnOpenModal('show','awards');">
            <div class="timeline__content"><img class="timeline__img" src="https://olc-wordpress-assets.s3.amazonaws.com/uploads/2021/04/OLC-Awards-Thumbnail-1200x800.jpg" />
                <h2 class="timeline__content-title">Awards</h2>
            </div>
            </a>
        </div>
    </div>
</div>

<div class="demo-footer"><a href="http://www.turkishnews.com/Ataturk/life.htm" target="_blank">Source/Kaynak</a></div>


<%-- modal --%>
<div class="modal-container" id="show" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <a onclick="fnCloseModal('show');" class="link-2"></a>
        <%-- title --%>
        <h1 class="modal__title text-center">Education</h1>

        <div class="grid_row gird_head">
            <div class="col_year col_center">year</div>
            <div class="col_month col_center">month</div>
            <div class="col_day col_center">day</div>
            <div class="col_content col_center">contents</div>
            <div class="col_check col_center"><i class="fas fa-star"></i></div>
            <div class="col_del col_center">delete</div>
        </div>
        <%-- data --%>
        <div class="year_info">
            <h3 class="year">2020년</h3>
            <div class="grid_row">
                <div class="col_month col_center">8월</div>
                <div class="col_day col_center">20일</div>
                <div class="col_content col_center">토익</div>
                <div class="col_check col_center"><input type="checkbox"> </div>
                <div class="col_del col_center">
                    <button class="btn btn-danger btn-lg" style="font-size: 1rem;" data-event="delete:animate" id="a"><i class="fa fa-trash"></i> Delete</button>
                </div>
                <%-- <p class="modal__text"></p>--%>
            </div>

        </div>

        <div class="input_container" id="input_container"><span class="input_button"><i class="fas fa-times"></i></span>
            <div class="content">

                <div class="body">
                    <div class="row">
                        <div class="col-3 col_center">
                            <input type="date">
                        </div>
                        <div class="col-6 col_center">
                            <input type="text">
                        </div>
                        <div class="col-1 col_center">
                            <input type="checkbox">
                        </div>
                        <div class="col-2 col_center">
                            <button class="btn btn-success btn-lg" style="font-size: 1rem;" data-btn-interaction="true" id="btn-save"><i class="fa fa-check"></i> Save</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal-container" id="m1-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="team-boxed">
        <a href="#" class="link-2"></a>
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
<%-- 버튼 관련 --%>
<script src="${pageContext.request.contextPath}/resources/js/career_roadMap/moving_button.js"></script>

<%-- 모달 조작 함수 --%>
<script>
    // 모달 오픈
    function fnOpenModal(id,type){
        console.log(type);
        $('#'+id).css("display", "flex");
    }

    // 모달 종료
    function fnCloseModal(id){
        $('#'+id).css("display", "none");
    }
</script>

<%-- 버튼 동작 --%>
<script>
    $('.input_button').click(function () {
        $(this).parent().toggleClass('expand');
    });
</script>
</html>