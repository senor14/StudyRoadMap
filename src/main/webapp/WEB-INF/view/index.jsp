<%@ page import="domain.StudyMindData" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.StudyRoadData" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    List<StudyRoadData> roadDataInfo = (List<StudyRoadData>) request.getAttribute("roadDataInfo");

    String SS_USER_UUID = (String)session.getAttribute("SS_USER_UUID");

%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Erase - Free Bootstrap 4 Template by Colorlib</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/study_mindMap/modal.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">  
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Abril+Fatface&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/open-iconic-bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/animate.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/magnific-popup.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/aos.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ionicons.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery.timepicker.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/flaticon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/icomoon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tab.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/add_button.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/next_button.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/input.css">
</head>

<body>
    <div class="page">
        <nav id="colorlib-main-nav" role="navigation">
            <a href="#" class="js-colorlib-nav-toggle colorlib-nav-toggle active"><i></i></a>
            <div class="js-fullheight colorlib-table">
                <div class="img" style="background-image: url(${pageContext.request.contextPath}/resources/images/bg_3.jpg);"></div>
                <div class="colorlib-table-cell js-fullheight">
                    <div class="row no-gutters">
                        <div class="col-md-12 text-center">
                            <h1 class="mb-4"><a href="/index" class="logo">Portfolio</a></h1>
                            <ul>
                                <li class="active"><a href="/index"><span>Home</span></a></li>
                                <li class="active"><a onclick="fnOpenModal('#m2-o');" style="cursor: pointer"><span >비밀번호 변경</span></a></li>
                                <li class="active"><a onclick="fnOpenModal('#m3-o');" style="cursor: pointer"><span >회원 탈퇴</span></a></li>
                                <li class="active"><a href="/RoadMap/Logout"><span>로그아웃</span></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
            <%-- modal 기본 --%>
            <div class="modal-container" id="m2-o" style="--m-background: hsla(0, 0%, 0%, .4);">
                <div class="modal">
                    <div class="text-center">
                        <span class="modal__title" id="modal__title">비밀번호 변경</span>
                    </div>
                        <form action="/RoadMap/PassWordChangeProc" method="post" id="pwd_chg_form" onsubmit="return check();">
                            <div class="input_body">
                                <label for="newPassWord" class="inp">
                                    <input type="password" name="pwd" id="newPassWord"  placeholder="&nbsp;">
                                    <span class="label">PASSWORD</span>
                                    <svg width="120px" height="26px" viewBox="0 0 120 26">
                                        <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                                    </svg>
                                    <span class="border"></span>
                                </label>
                            </div>
                            <div class="input_body">
                                <label for="passWordCheck" class="inp">
                                    <input type="password" name="pwd2" id="passWordCheck" placeholder="&nbsp;">
                                    <span class="label">PASSWORD_CHECK</span>
                                    <svg width="120px" height="26px" viewBox="0 0 120 26">
                                        <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                                    </svg>
                                    <span class="border"></span>
                                </label>
                            </div>
<%--                            <span>PASSWORD</span>--%>
<%--                            <input type="password" name="pwd" id="newPassWord" placeholder="Type your Password" required/><br>--%>
<%--                            <span>PASSWROD CHECK</span>--%>
<%--                            <input type="password" name="pwd2" id="passWordCheck" placeholder="Type your Password Check" required/><br>--%>
<%--                            <span id="renew"></span><br>--%>
                        </form>
                    <div class="text-center" style="display:grid; grid-template-columns: repeat(5, 1fr);">
                        <button type="button" style="grid-column: 2;" class="modal__btn" onclick="pwd_chg();">확인</button>
                        <button type="button" style="grid-column: 4;" class="modal__btn" onclick="fnCloseModal('#m2-o');" >취소</button>
                    </div>
                </div>
            </div>
            <%-- modal 기본 끝 --%>

            <%-- modal 추가 --%>
            <div class="modal-container" id="m3-o" style="--m-background: hsla(0, 0%, 0%, .4);">
                <div class="modal">
                    <div class="text-center">
                        <span class="modal__title">회원 탈퇴</span>
                    </div>
                    <form action="/RoadMap/userWithdrawalProc" method="post" onsubmit="return del_check();" style="text-align: center;" id="withdrawal_form">
                        <span >회원 탈퇴를 누르면 다시 되돌릴 수 없습니다. 탈퇴를 진행하시려면 <b>Account_withdrawal</b>를 입력해주세요.</span>
                        <div class="input_body">
                            <label for="DeleteCheck" class="inp">
                                <input type="text" name="DeleteCheck" id="DeleteCheck" placeholder="&nbsp;">
                                <span class="label">Account_withdrawal</span>
                                <svg width="120px" height="26px" viewBox="0 0 120 26">
                                    <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                                </svg>
                                <span class="border"></span>
                            </label>
                        </div>
<%--                        <input type="text" name="DeleteCheck" id="DeleteCheck" placeholder="Account_withdrawal" required/><br>--%>
                        <span id="id_find"></span><br>
                    </form>
                    <div class="text-center" style="display:grid; grid-template-columns: repeat(3, 1fr);">
                        <button type="button" style="grid-column: 2; grid-row:1" class="modal__btn" onclick="withdrawal();">탈퇴하기</button>
                        <button type="button" style="grid-column: 3; grid-row:2" class="modal__btn" onclick="fnCloseModal('#m3-o');" >취소</button>
                    </div>
                </div>
            </div>
            <%-- modal 추가 끝 --%>
        </nav>

        <div id="colorlib-page">

            <!-- 페이지 상단 -->
            <header>
                <div class="container">
                    <div class="colorlib-navbar-brand">
                        <a class="colorlib-logo" href="index.html">Portfolio</a>
                    </div>
                    <a href="#" class="js-colorlib-nav-toggle colorlib-nav-toggle"><i></i></a>
                </div>
            </header>

            <!-- 페이지 메인 로고 -->
            <section class="hero-wrap js-fullheight">
                <div class="container-fluid px-0">
                    <div class="row no-gutters slider-text js-fullheight align-items-center justify-content-center" data-scrollax-parent="true">
                        <div class="col-md-12 ftco-animate text-center">
                            <div class="desc">
                                <span class="subheading">Study</span>
                                <h1 style="background-image: url(${pageContext.request.contextPath}/resources/images/bg_1.jpg);">R.M</h1>
                                <span class="subheading-2">Career</span>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- 탭 부분 -->
            <section class="ftco-section ftco-no-pb ftco-no-pt">
                <div class="container-fluid px-0">
                    <div class="row no-gutters">
                        <div class="col-md-12 blog-wrap">
                            <div class="row no-gutters align-items-center">
                                <section id="wrapper">
                                    <div class="content">
                                        <!-- Tab links -->
                                        <div class="tabs">
                                            <button class="tablinks active" data-country="Schedule">
                                                <p data-title="Schedule">Schedule</p>
                                            </button>
                                            <button class="tablinks" data-country="Study_MindMap">
                                                <p data-title="Study_MindMap">Study RoadMap</p>
                                            </button>
                                            <button class="tablinks" data-country="Career_RoadMap">
                                                <p data-title="Career_RoadMap">Career RoadMap</p>
                                            </button>
                                            <button class="tablinks" data-country="Community">
                                                <p data-title="Community">Community</p>
                                            </button>
                                        </div>

                                        <!-- Tab content -->
                                        <div class="wrapper_tabcontent">
                                            <div id="Schedule" class="tabcontent active">
                                                <h3>Schedule</h3>
                                                <iframe src="/calendar" style="width:100%; border: 0px; height:800px; overflow:hidden;"></iframe>
                                            </div>
                                            <div id="Study_MindMap" class="tabcontent">
                                                <h3>Study MindMap</h3>
                                                <%-- 급하게 만들어서 스타일 그대로 넣었으니 클래스로 바꿔서 써주세요 --%>
                                                <%-- 자신의 기존 스터디 마인드 맵 정보와 신규 스터디 마인드맵 버튼 들어가는 곳 --%>
                                                <div class=""  style="display:grid; grid-template-columns: repeat(2, 1fr);word-wrap: break-word; height:800px; overflow:auto;">

                                                    <% for (StudyRoadData roadData : roadDataInfo) { %>
                                                    <div style="position: relative; margin:5%; display: flex;align-items: flex-end;justify-content: center;  border-radius: 5%;height: 200px;border: 3px solid black; ">
                                                        <span class="position-absolute" style="top:2px; right: 5px; font-size: 13px;">공개여부: <input type="checkbox" class="road__publicYn" value="<%=roadData.getPublicYn()%>" onclick="checkbox_chk(this, '<%=roadData.getRoadId()%>')"></span>
                                                        <div class="cursor" onclick="location.href='/roadmaps/<%=roadData.getRoadId()%>'" style="cursor:pointer; width:100%; height:100%;border-radius: 10%; background-size: cover; background-image: url('http://localhost:9000/getRoadMapImage?roadId=<%=roadData.getRoadId()%>')">
                                                            <div style="margin-top: 150px; text-align: center;">
                                                                <span style="text-shadow: grey 5px 5px, grey 4px 4px, grey 3px 3px, grey 2px 2px, grey 1px 1px; color: white;">"<%=roadData.getRoadTitle()%>"</span>
                                                                <span class="road__title" hidden>"<%=roadData.getRoadTitle()%>"</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <%}%>

                                                    <%-- 신규 스터디 로드맵 버튼 --%>
                                                    <div style="margin:5%; display: flex;align-items: center;justify-content: center;  border-radius: 5%;height: 200px;border: 3px dashed gray;">
                                                        <div class="button-container">
                                                            <a href="javascript:fnOpenModal('#m4-o')" class="btnn"><span>+</span></a>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                            <div id="Career_RoadMap" class="tabcontent">
                                                <h3>Career RoadMap</h3>
                                                <%--  커리어 로드맵 존재하면 조회해서 접근 버튼 생성, 없을 경우 신규 생성 버튼 보여주기 --%>
                                                <div style="width:100%; border: 0px; height:800px; overflow:hidden;">
                                                    <%-- 커리어 로드맵 없을 시 --%>
                                                    <div id="no_career">
                                                        <div style="position:absolute; width:100%; text-align: center; top:35%">
                                                            <h2 style="color: gray;display: inline-block;">there's nothing here yet,<br>
                                                                add something to get started</h2>
                                                        </div>
                                                        <div class="button-container" style="position: absolute;top: 50%;left: 45%;">
                                                            <a href="javascript:fnOpenModal('#m5-o')" class="btnn"><span>+</span></a>
                                                        </div>
                                                    </div>

                                                    <%-- 커리어 로드맵 존재 할 경우 --%>
                                                    <div id="yes_career" style="position:absolute;  width:100%; top:35% ">
                                                        <div class="wrapper">
                                                            <a class="cta" id="my_career" href="/career/<%=SS_USER_UUID%>">
                                                                <span>CareerRoadMap</span>
                                                                <span>
                                                              <svg width="66px" height="43px" viewBox="0 0 66 43" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                                                <g id="arrow" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                                                  <path class="one" d="M40.1543933,3.89485454 L43.9763149,0.139296592 C44.1708311,-0.0518420739 44.4826329,-0.0518571125 44.6771675,0.139262789 L65.6916134,20.7848311 C66.0855801,21.1718824 66.0911863,21.8050225 65.704135,22.1989893 C65.7000188,22.2031791 65.6958657,22.2073326 65.6916762,22.2114492 L44.677098,42.8607841 C44.4825957,43.0519059 44.1708242,43.0519358 43.9762853,42.8608513 L40.1545186,39.1069479 C39.9575152,38.9134427 39.9546793,38.5968729 40.1481845,38.3998695 C40.1502893,38.3977268 40.1524132,38.395603 40.1545562,38.3934985 L56.9937789,21.8567812 C57.1908028,21.6632968 57.193672,21.3467273 57.0001876,21.1497035 C56.9980647,21.1475418 56.9959223,21.1453995 56.9937605,21.1432767 L40.1545208,4.60825197 C39.9574869,4.41477773 39.9546013,4.09820839 40.1480756,3.90117456 C40.1501626,3.89904911 40.1522686,3.89694235 40.1543933,3.89485454 Z" fill="#FFFFFF"></path>
                                                                  <path class="two" d="M20.1543933,3.89485454 L23.9763149,0.139296592 C24.1708311,-0.0518420739 24.4826329,-0.0518571125 24.6771675,0.139262789 L45.6916134,20.7848311 C46.0855801,21.1718824 46.0911863,21.8050225 45.704135,22.1989893 C45.7000188,22.2031791 45.6958657,22.2073326 45.6916762,22.2114492 L24.677098,42.8607841 C24.4825957,43.0519059 24.1708242,43.0519358 23.9762853,42.8608513 L20.1545186,39.1069479 C19.9575152,38.9134427 19.9546793,38.5968729 20.1481845,38.3998695 C20.1502893,38.3977268 20.1524132,38.395603 20.1545562,38.3934985 L36.9937789,21.8567812 C37.1908028,21.6632968 37.193672,21.3467273 37.0001876,21.1497035 C36.9980647,21.1475418 36.9959223,21.1453995 36.9937605,21.1432767 L20.1545208,4.60825197 C19.9574869,4.41477773 19.9546013,4.09820839 20.1480756,3.90117456 C20.1501626,3.89904911 20.1522686,3.89694235 20.1543933,3.89485454 Z" fill="#FFFFFF"></path>
                                                                  <path class="three" d="M0.154393339,3.89485454 L3.97631488,0.139296592 C4.17083111,-0.0518420739 4.48263286,-0.0518571125 4.67716753,0.139262789 L25.6916134,20.7848311 C26.0855801,21.1718824 26.0911863,21.8050225 25.704135,22.1989893 C25.7000188,22.2031791 25.6958657,22.2073326 25.6916762,22.2114492 L4.67709797,42.8607841 C4.48259567,43.0519059 4.17082418,43.0519358 3.97628526,42.8608513 L0.154518591,39.1069479 C-0.0424848215,38.9134427 -0.0453206733,38.5968729 0.148184538,38.3998695 C0.150289256,38.3977268 0.152413239,38.395603 0.154556228,38.3934985 L16.9937789,21.8567812 C17.1908028,21.6632968 17.193672,21.3467273 17.0001876,21.1497035 C16.9980647,21.1475418 16.9959223,21.1453995 16.9937605,21.1432767 L0.15452076,4.60825197 C-0.0425130651,4.41477773 -0.0453986756,4.09820839 0.148075568,3.90117456 C0.150162624,3.89904911 0.152268631,3.89694235 0.154393339,3.89485454 Z" fill="#FFFFFF"></path>
                                                                </g>
                                                              </svg>
                                                            </span>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="Community" class="tabcontent">
                                                <h3>Community</h3>
                                                <iframe src="/community" style="width:100%; border: 0px; height:800px; overflow:hidden;"></iframe>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </section>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <%-- 스터디 로드맵 신규 modal 시작 --%>
            <div class="modal-container" id="m4-o" style="--m-background: hsla(0, 0%, 0%, .4);">
                <div class="modal">
                    <div class="text-center">
                        <span class="modal__title">NEW STUDY ROADMAP</span>
                    </div>
                    <div class="" style="display:grid;">
                        <div class="input_body col_center" style="grid-column: 1/4">
                            <label for="study__title-add" class="inp">
                                <input type="text" class="modal__title" id="study__title-add" placeholder="&nbsp;">
                                <span class="label">Study_RoadMap_Title</span>
                                <svg width="120px" height="26px" viewBox="0 0 120 26">
                                    <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                                </svg>
                                <span class="border"></span>
                            </label>
                        </div>
                        <div style="grid-column: 4" class="col_center">
                            <label for="StudypublicYn">공개여부:</label>
                        </div>
                        <div class="col_center" style="grid-column: 5">
                            <select name="StudypublicYn" id="StudypublicYn">
                                <option value="Y" selected>Y</option>
                                <option value="N">N</option>
                            </select>
                        </div>
                    </div>

<%--                    <div>--%>
<%--                        로드맵 제목: <input type="text" class="modal__title" id="study__title-add"/>--%>
<%--                    </div>--%>
                    <div class="text-center">
                        <button class="modal__btn" onclick="addStudyRoadmap();">확인</button>
                    </div>
                    <a onclick="fnCloseModal('#m4-o');" class="link-2"></a>
                </div>
            </div>
            <%-- 스터디 로드맵 신규 modal 끝 --%>

            <%-- 커리어 로드맵 신규 modal 시작 --%>
            <div class="modal-container" id="m5-o" style="--m-background: hsla(0, 0%, 0%, .4);">
                <div class="modal">
                    <div class="text-center">
                        <span class="modal__title">NEW CAREER ROADMAP</span>
                    </div>
                    <div class="" style="display:grid;">
                        <div class="input_body col_center" style="grid-column: 1/4">
                            <label for="career__title-add" class="inp">
                                <input type="text" class="career__title-add" id="career__title-add" placeholder="&nbsp;">
                                <span class="label">Career_RoadMap_Title</span>
                                <svg width="120px" height="26px" viewBox="0 0 120 26">
                                    <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                                </svg>
                                <span class="border"></span>
                            </label>
                        </div>
                        <div style="grid-column: 4" class="col_center">
                            <label for="StudypublicYn">공개여부:</label>
                        </div>
                        <div style="grid-column: 5" class="col_center">
                            <select name="careerPublicYn" id="careerPublicYn">
                                <option value="Y" selected>Y</option>
                                <option value="N">N</option>
                            </select>
                        </div>
                    </div>
                    <div class="text-center">
                    <button class="modal__btn" onclick="addCareerRoadmap();">확인</button>
                    </div>
                    <a onclick="fnCloseModal('#m5-o');" class="link-2"></a>
                </div>
            </div>
            <%-- 커리어 로드맵 신규 modal 끝 --%>

            <footer class="ftco-footer ftco-section img">
                <div class="overlay"></div>
            </footer>

            <!-- loader -->
            <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px">
                    <circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee" />
                    <circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" />
            </svg></div>

        </div>
    </div>

    <script>
        <%--    스터디 로드맵 신규 생성    --%>
        function addStudyRoadmap() {
            $.ajax({
                url: "/roadmaps",
                type: "post",
                data: {
                    publicYn: document.getElementById("StudypublicYn").value,
                    roadTitle: document.getElementById("study__title-add").value
                },
                success: function (data) {
                    if (data) {
                        console.log("생성 완료");
                        location.href = data;
                    } else {
                        console.log("데이터 이상")
                    }
                }

            })
        }

        <%--    커리어 로드맵 신규 생성    --%>
        function addCareerRoadmap() {
            $.ajax({
                url: "/career/new",
                type: "post",
                data: {
                    publicYn: document.getElementById("careerPublicYn").value,
                    roadTitle: document.getElementById("career__title-add").value
                },
                success: function (data) {
                    if (data=="성공") {
                        location.href = "/career/<%=SS_USER_UUID%>"
                    } else {
                        console.log("데이터 이상")
                    }
                }

            })
        }

    </script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.easing.1.3.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.waypoints.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.stellar.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/owl.carousel.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.magnific-popup.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/aos.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/tab.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.animateNumber.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/scrollax.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

</body>
</html>
<script>
    let publics = document.querySelectorAll(".road__publicYn")
    console.log(publics)
    for (let i=0; i<publics.length; i++) {
        console.log("publics["+i+"].value: "+publics[i].value);
        if (publics[i].value === "Y") {
            publics[i].setAttribute("checked", "checked");
        }
    }

    function checkbox_chk(target, roadId) {
        let publicYn = 'N';
        if (target.checked) {
            publicYn = 'Y';
        }
        console.log("checked: "+publicYn);
        console.log("roadId: "+roadId)

        $.ajax({
            url: "/roadmaps/"+roadId,
            type: "put",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                "publicYn": publicYn
            }),
            success: function (data) {
                console.log(data);
            }
        })
    }
</script>
<script>

    let pwdCheck = 'N';

    $('#passWordCheck').keyup(function() {

        let pw = document.getElementById("newPassWord").value; //비밀번호
        let pw2 = document.getElementById("passWordCheck").value; // 확인 비밀번호

        if (pw != "" || pw2 != "") {
            if (pw == pw2) {
                $("#renew").text("비밀번호가 일치합니다.");
                $("#renew").css("color", "#00f");
                pwdCheck = 'Y';
            } else {
                $("#renew").text("비밀번호가 일치하지 않습니다.");
                $("#renew").css("color", "#f00");
                pwdCheck = 'N';
            }
        }

    })
    function pwd_chg() {

        document.getElementById('pwd_chg_form').submit();

    }
    function withdrawal() {

        document.getElementById('withdrawal_form').submit();

    }


    function check() {
        if(pwdCheck == "N"){
            alert("비밀번호가 일치하지 않습니다. 비밀번호를 확인해주세요.");
            return false;
        } else{
            return true;
        }
    }

    let DeleteCheck = 'N';

    $("#DeleteCheck").keyup(function() {
        let query = {
            DeleteCheck : $("#DeleteCheck").val()
        };

        $.ajax({
            url : "/RoadMap/userWithdrawalCheck",
            type : "post",
            data : query,
            success : function(data) {
                if (data == 1) {
                    $("#id_find").text("탈퇴 확인 문자열이 같습니다.");
                    $("#id_find").attr("style", "color:white");
                    DeleteCheck = 'Y'
                } else {
                    $("#id_find").text("탈퇴 확인 문자열이 다릅니다.");
                    $("#id_find").attr("style", "color:palevioletred");
                    DeleteCheck = 'N'
                }
            }
        }); // ajax 끝
    });

    function del_check() {
        if(idCheck == 'N'){
            alert("탈퇴 확인 문구가 다릅니다. 탈퇴 확인 문구를 확인해주세요.");
            return false;
        } else{
            return true;
        }
    }
</script>
<%-- 모달 조작 함수 --%>
<script>
    // 모달 오픈
    function fnOpenModal(id){
        $(id).css("display", "flex");
    }
    // 모달 종료
    function fnCloseModal(id){
        $(id).css("display", "none");
    }

</script>


<script>
    /* 커리어 로드맵 존재 체크 */
    window.onload = function() {
        $.ajax({
            url: "/career/chk",
            type: "post",
            data: {
            },
            success: function (data) {
                console.log(data);
                if (data=="Y") {
                    $('#no_career').css("display", "none");
                } else {
                    $('#yes_career').css("display", "none");
                }
            }

        })
    }
</script>

<%-- select box --%>
<script>
    $(document).ready(function (){
        /* study */
        $('#StudypublicYn').wrap('<div class="select_wrapper"></div>')
        $('#StudypublicYn').parent().prepend('<span>'+ $(this).find(':selected').text().charAt(0) +'</span>');

        $('#StudypublicYn').css('display', 'none');
        $('#StudypublicYn').parent().append('<ul class="select_inner"></ul>');
        $('#StudypublicYn').children().each(function(){
            var opttext = $(this).text();
            var optval = $(this).val();
            $('#StudypublicYn').parent().children('.select_inner').append('<li id="' + optval + '">' + opttext + '</li>');
        });



        $('#StudypublicYn').parent().find('li').on('click', function (){
            var cur = $(this).attr('id');
            $('#StudypublicYn').parent().children('span').text($(this).text());
            $('#StudypublicYn').children().removeAttr('selected');
            $('#StudypublicYn').children('[value="'+cur+'"]').attr('selected','selected');
            //console.log($('select').children('[value="'+cur+'"]').text());
            $('#StudypublicYn').parent().removeClass('openSelect');

            $('#StudypublicYn').parent().find('ul').hide();
        });
        $('#StudypublicYn').parent().find('span').on('click', function (){
            $('#StudypublicYn').parent().find('ul').slideToggle(200);

            $('#StudypublicYn').parent().toggleClass('openSelect');


        });

        /* career */
        $('#careerPublicYn').wrap('<div class="select_wrapper"></div>')
        $('#careerPublicYn').parent().prepend('<span>'+ $(this).find(':selected').text().charAt(1) +'</span>');

        $('#careerPublicYn').css('display', 'none');
        $('#careerPublicYn').parent().append('<ul class="select_inner"></ul>');
        $('#careerPublicYn').children().each(function(){
            var opttext = $(this).text();
            var optval = $(this).val();
            $('#careerPublicYn').parent().children('.select_inner').append('<li id="' + optval + '">' + opttext + '</li>');
        });



        $('#careerPublicYn').parent().find('li').on('click', function (){
            var cur = $(this).attr('id');
            $('#careerPublicYn').parent().children('span').text($(this).text());
            $('#careerPublicYn').children().removeAttr('selected');
            $('#careerPublicYn').children('[value="'+cur+'"]').attr('selected','selected');
            //console.log($('select').children('[value="'+cur+'"]').text());
            $('#careerPublicYn').parent().removeClass('openSelect');

            $('#careerPublicYn').parent().find('ul').hide();
        });
        $('#careerPublicYn').parent().find('span').on('click', function (){
            $('#careerPublicYn').parent().find('ul').slideToggle(200);

            $('#careerPublicYn').parent().toggleClass('openSelect');


        });
    });
</script>