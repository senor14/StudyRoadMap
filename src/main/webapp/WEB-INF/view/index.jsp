<%@ page import="domain.StudyMindData" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.StudyRoadData" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    List<StudyRoadData> roadDataInfo = (List<StudyRoadData>) request.getAttribute("roadDataInfo");
    String userUuid = (String)session.getAttribute("SS_USER_UUID");
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/add_button.css">
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
                                                <iframe src="/calendar" style="width:100%; border: 0px; height:620px; overflow:hidden;"></iframe>
                                            </div>
                                            <div id="Study_MindMap" class="tabcontent">
                                                <h3>Study MindMap</h3>
                                                <%-- 급하게 만들어서 스타일 그대로 넣었으니 클래스로 바꿔서 써주세요 --%>
                                                <%-- 자신의 기존 스터디 마인드 맵 정보와 신규 스터디 마인드맵 버튼 들어가는 곳 --%>
                                                <div class=""  style="display:grid; grid-template-columns: repeat(2, 1fr);word-wrap: break-word; height:620px; overflow:auto;">

                                                    <%-- 기존 마인드맵 접속 버튼 --%>
                                                    <% for (StudyRoadData roadData : roadDataInfo) { %>
                                                        <div onclick="location.href='/roadmaps/<%=roadData.getRoadId()%>'" style="margin:5%; display: flex;align-items: flex-end;justify-content: center;  border-radius: 5%;height: 200px;border: 3px solid black; background-size: cover; background-image: url('http://localhost:9000/getRoadMapImage?roadId=<%=roadData.getRoadId()%>')">
                                                            <div >
                                                                <span style="text-shadow: grey 5px 5px, grey 4px 4px, grey 3px 3px, grey 2px 2px, grey 1px 1px; color: white;">"<%=roadData.getRoadTitle()%>"</span>
                                                                <span class="road__title" hidden>"<%=roadData.getRoadTitle()%>"</span>
                                                            </div>
                                                        </div>
                                                        <span class="">공개여부: <input type="checkbox" class="road__publicYn" value="<%=roadData.getPublicYn()%>" onclick="checkbox_chk(this, '<%=roadData.getRoadId()%>')"></span>
                                                    <%}%>

                                                    <%-- 기존거 조회 후 마지막에 이 버튼 추가해 주면 될거 같음 --%>
                                                    <%-- 신규 마인드맵 버튼 --%>
                                                    <div style="margin:5%; display: flex;align-items: center;justify-content: center;  border-radius: 5%;height: 200px;border: 3px dashed gray;">
                                                        <div class="button-container">
                                                            <a href="javascript:fnOpenModal('#m4-o')" class="btnn"><span>+</span></a>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                            <div id="Career_RoadMap" class="tabcontent">
                                                <h3>Career RoadMap</h3>
                                                <p>Barcelona has been an urban laboratory since the high Medieval Ages. A place of diversity, a backdrop for a multiplicity of social and cultural processes on multiple scales that reflect different ways of constructing the future, a city with a long experience of urban life and social innovations. </p>
                                            </div>
                                            <div id="Community" class="tabcontent">
                                                <h3>Community</h3>
                                                <iframe src="/community" style="width:100%; border: 0px; height:620px; overflow:hidden;"></iframe>
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

            <%-- modal 로드맵 추가 --%>
            <div class="modal-container" id="m4-o" style="--m-background: hsla(0, 0%, 0%, .4);">
                <div class="modal">
                    <div>
                        로드맵 제목: <input type="text" class="modal__title" id="modal__title-add"/>
                    </div>
                    <div>
                        <label for="publicYn">공개여부:</label>
                        <select name="publicYn" id="publicYn">
                            <option value="Y" selected>Y</option>
                            <option value="N">N</option>
                        </select>
                    </div>
                    <button class="modal__btn" onclick="addStudyRoadmap();">확인</button>
                    <a onclick="fnCloseModal('#m4-o');" class="link-2"></a>
                </div>
            </div>
            <%-- modal 로드맵 추가 끝 --%>

            <%-- modal 로드맵 정보조회 --%>
            <div class="modal-container" id="m5-o" style="--m-background: hsla(0, 0%, 0%, .4);">
                <div class="modal">
                    <div>
                        로드맵 제목: <h1 class="modal__title" id="modal__title-info"> </h1>
                    </div>
                    <div>
                        <label for="publicYn">공개여부:</label>
                        <select name="publicYn" id="publicYn-info" disabled>
                            <option value="Y">Y</option>
                            <option value="N">N</option>
                        </select>
                    </div>
                    <button class="modal__btn" onclick="fnOpenModal('#m5-o');">수정</button>
                    <button class="modal__btn" onclick="fnCloseModal('#m5-o');">취소</button>
                    <a onclick="fnCloseModal('#m5-o');" class="link-2"></a>
                </div>
            </div>
            <%-- modal 로드맵 정보조회 끝 --%>

            <%-- modal 로드맵 정보수정 --%>
            <div class="modal-container" id="m6-o" style="--m-background: hsla(0, 0%, 0%, .4);">
                <div class="modal">
                    <div>
                        로드맵 제목: <input type="text" class="modal__title" id="modal__title-mod"/>
                    </div>
                    <div>
                        <label for="publicYn">공개여부:</label>
                        <select name="publicYn" id="publicYn-mod">
                            <option value="Y">Y</option>
                            <option value="N">N</option>
                        </select>
                    </div>
<%--                    <button class="modal__btn" onclick="modStudyRoadmap();">수정</button>--%>
                    <button class="modal__btn" onclick="fnCloseModal('#m6-o');">취소</button>
                    <a onclick="fnCloseModal('#m6-o');" class="link-2"></a>
                </div>
            </div>
            <%-- modal 로드맵 정보수정 끝 --%>

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
                        location.href = data;
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