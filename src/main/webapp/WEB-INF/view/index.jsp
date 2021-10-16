<%@ page import="domain.StudyMindData" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.StudyRoadData" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    List<StudyRoadData> roadDataInfo = (List<StudyRoadData>) request.getAttribute("roadDataInfo");
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Erase - Free Bootstrap 4 Template by Colorlib</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/study_mindMap/modal.css">
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
                            <h1 class="mb-4"><a href="index.jsp" class="logo">Portfolio</a></h1>
                            <ul>
                                <li class="active"><a href="index.html"><span>Home</span></a></li>
                                <li class="active"><a><span onclick="fnOpenModal('#m2-o');">비밀번호 변경</span></a></li>
                                <li class="active"><a><span onclick="fnOpenModal('#m3-o');">회원 탈퇴</span></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
<%--        </nav>--%>
            <%-- modal 기본 --%>
            <div class="modal-container" id="m2-o" style="--m-background: hsla(0, 0%, 0%, .4);">
                <div class="modal">
                    <h1 class="modal__title" id="modal__title">비밀번호 변경</h1>
                    <div>
                        <form action="/RoadMap/PassWordChangeProc" method="post" id="pwd_chg_form" onsubmit="return check();">
                            <span>PASSWORD</span>
                            <input type="password" name="pwd" id="newPassWord" placeholder="Type your Password" required/><br>
                            <span>PASSWROD CHECK</span>
                            <input type="password" name="pwd2" id="passWordCheck" placeholder="Type your Password Check" required/><br>
                            <span id="renew"></span><br>
                        </form>
                        <button type="button" class="modal__btn" onclick="pwd_chg();">확인</button>
                        <button type="button" class="modal__btn" onclick="fnCloseModal('#m2-o');" >취소</button>
                    </div>
                </div>
            </div>
            <%-- modal 기본 끝 --%>

            <%-- modal 추가 --%>
            <div class="modal-container" id="m3-o" style="--m-background: hsla(0, 0%, 0%, .4);">
                <div class="modal">
                    <h1 class="modal__title">회원 탈퇴</h1>
                    <div>
                        <form action="/RoadMap/userWithdrawalProc" method="post" onsubmit="return del_check();" id="withdrawal_form">
                            <span>Withdrawal Check</span><<br>
                            <span>회원 탈퇴를 누르면 다시 되돌릴 수 없습니다. 탈퇴를 진행하시려면 Account_withdrawal를 입력해주세요.</span><br>
                            <input type="text" name="DeleteCheck" id="DeleteCheck" placeholder="Account_withdrawal" required/><br>
                            <span id="id_find"></span><br>
                        </form>
                        <button type="button" class="modal__btn" onclick="withdrawal();">탈퇴하기</button>
                        <button type="button" class="modal__btn" onclick="fnCloseModal('#m3-o');" >취소</button>
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
                                <h1 style="background-image: url(${pageContext.request.contextPath}/resources/images/bg_1.jpg);">Test</h1>
                                <span class="subheading-2">Career</span>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- 탭 부분 -->
            <section class="ftco-section ftco-no-pb ftco-no-pt">
                <div>
                    <% for (StudyRoadData roadData : roadDataInfo) { %>
                    <div style="border: 3px solid black; z-index: 10000; background-color: white;" onclick="location.href='/roadmaps/<%=roadData.getRoadId()%>'" >
                        <%--                                                    <a href="/roadmaps/<%=roadData.getRoadId()%>" style="z-index: 10000">--%>
                        "<%=roadData.getRoadTitle()%>"
                        <%--                                                    </a>--%>
                    </div>
                    <%}%>
                </div>
                <div>
                    <button id="roadmap__add" onclick="fnOpenModal('#m4-o')">바톤</button>
                </div>
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
                                                <p data-title="Study_MindMap">Study MindMap</p>
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
                                                <p>London is the capital of Great Britain. It is one of the greatest cities in the world. It is an important business and financial centre. It is an intellectual centre, too. Everywhere in London, there are open spaces: Hyde Park and Regent Park are the largest. The City is the oldest part of London. </p>
                                            </div>

                                            <div id="Study_MindMap" class="tabcontent">
                                                <h3>Study MindMap</h3>


                                            </div>

                                            <div id="Career_RoadMap" class="tabcontent">
                                                <h3>Career RoadMap</h3>
                                                <p>Barcelona has been an urban laboratory since the high Medieval Ages. A place of diversity, a backdrop for a multiplicity of social and cultural processes on multiple scales that reflect different ways of constructing the future, a city with a long experience of urban life and social innovations. </p>
                                            </div>

                                            <div id="Community" class="tabcontent">
                                                <h3>Community</h3>
                                                <p>Madrid is in the middle of Spain, in the Community of Madrid. The Community is a large area that includes the city as well as small towns and villages outside the city. 7 million people live in the Community. More than 3 million live in the city itself.
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

            <%-- modal 기본 --%>
            <div class="modal-container" id="m4-o" style="--m-background: hsla(0, 0%, 0%, .4);">
                <div class="modal">
                    <div>
                        로드맵 제목: <input type="text" class="modal__title" id="modal__title-add"/>
                    </div>
                    <div>
                        <label for="publicYn">공개여부:</label>
                        <select name="publicYn" id="publicYn">
                            <option value="Y">Y</option>
                            <option value="N">N</option>
                        </select>
                    </div>
                    <button class="modal__btn" onclick="addStudyRoadmap();">확인</button>
                    <a onclick="fnCloseModal('#m4-o');" class="link-2"></a>
                </div>
            </div>
            <%-- modal 기본 끝 --%>


            <footer class="ftco-footer ftco-section img">
                <div class="overlay"></div>
                <div class="container">
                    <div class="row mb-5">
                        <div class="col-lg-3">
                            <div class="ftco-footer-widget mb-4">
                                <h2 class="ftco-heading-2 logo"><a href="index.html">Erase</a></h2>
                                <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                <ul class="ftco-footer-social list-unstyled float-md-left float-lft mt-5">
                                    <li class="ftco-animate"><a href="#"><span class="icon-twitter"></span></a></li>
                                    <li class="ftco-animate"><a href="#"><span class="icon-facebook"></span></a></li>
                                    <li class="ftco-animate"><a href="#"><span class="icon-instagram"></span></a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="ftco-footer-widget mb-4">
                                <h2 class="ftco-heading-2">Recent Blog</h2>
                                <div class="block-21 mb-4 d-flex">
                                    <a class="blog-img mr-4" style="background-image: url(${pageContext.request.contextPath}/resources/images/image_1.jpg);"></a>
                                    <div class="text">
                                        <h3 class="heading"><a href="#">Even the all-powerful Pointing has no control about</a></h3>
                                        <div class="meta">
                                            <div><a href="#"><span class="icon-calendar"></span> July 12, 2018</a></div>
                                            <div><a href="#"><span class="icon-person"></span> Admin</a></div>
                                            <div><a href="#"><span class="icon-chat"></span> 19</a></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="block-21 mb-4 d-flex">
                                    <a class="blog-img mr-4" style="background-image: url(${pageContext.request.contextPath}/resources/images/image_2.jpg);"></a>
                                    <div class="text">
                                        <h3 class="heading"><a href="#">Even the all-powerful Pointing has no control about</a></h3>
                                        <div class="meta">
                                            <div><a href="#"><span class="icon-calendar"></span> July 12, 2018</a></div>
                                            <div><a href="#"><span class="icon-person"></span> Admin</a></div>
                                            <div><a href="#"><span class="icon-chat"></span> 19</a></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-2">
                            <div class="ftco-footer-widget mb-4 ml-md-4">
                                <h2 class="ftco-heading-2">Site Links</h2>
                                <ul class="list-unstyled">
                                    <li><a href="#" class="py-2 d-block">Home</a></li>
                                    <li><a href="#" class="py-2 d-block">About</a></li>
                                    <li><a href="#" class="py-2 d-block">Model</a></li>
                                    <li><a href="#" class="py-2 d-block">Services</a></li>
                                    <li><a href="#" class="py-2 d-block">Blog</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-lg-3">
                            <div class="ftco-footer-widget mb-4">
                                <h2 class="ftco-heading-2">Have a Questions?</h2>
                                <div class="block-23 mb-3">
                                    <ul>
                                        <li><span class="icon icon-map-marker"></span><span class="text">203 Fake St. Mountain View, San Francisco, California, USA</span></li>
                                        <li><a href="#"><span class="icon icon-phone"></span><span class="text">+2 392 3929 210</span></a></li>
                                        <li><a href="#"><span class="icon icon-envelope"></span><span class="text">info@yourdomain.com</span></a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 text-center">

                            <p>
                                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                Copyright &copy;<script>
                                    document.write(new Date().getFullYear());
                                </script> All rights reserved | This template is made with <i class="icon-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                            </p>
                        </div>
                    </div>
                </div>
            </footer>

            <!-- loader -->
            <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px">
                    <circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee" />
                    <circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" />
                </svg></div>

        </div>
    </div>

    <script>
        function addStudyRoadmap() {
            $.ajax({
                url: "/roadmaps",
                type: "post",
                data: {
                    publicYn: document.getElementById("publicYn").value,
                    roadTitle: document.getElementById("modal__title-add").value
                },
                success: function (data) {
                    if (data) {
                        console.log("생성 완료");
                        location.href = "/index"
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
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
    <script src="${pageContext.request.contextPath}/resources/js/google-map.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

</body>
</html>

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
                    $("#id_find").attr("style", "color:#00f");
                    DeleteCheck = 'Y'
                } else {
                    $("#id_find").text("탈퇴 확인 문자열이 다릅니다.");
                    $("#id_find").attr("style", "color:#f00");
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
        // $('#m2-o').css("display", "flex");
        $(id).css("display", "flex");
    }
    // 모달 종료
    function fnCloseModal(id){
        //$('#m2-o').css("display", "none");
        $(id).css("display", "none");
    }

</script>