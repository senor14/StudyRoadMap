<%--
  Created by IntelliJ IDEA.
  User: 2chan
  Date: 2021-10-02
  Time: 오후 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css"/>
    <link rel="stylesheet" href="../../../resources/css/community.css">
    <script src="../../../resources/js/jquery.min.js"></script>
    <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
    <script src="../../../resources/js/community.js"></script>
</head>
<body>
<div class="top">
    <div class="setting">
        <select class="type" id="searchType">
            <option value="title">타이틀검색</option>
            <option value="category">카테고리검색</option>
        </select>
        <div class="category">
            RoadMap:
            <label><input type="radio" name="category" value="s" onclick="getStudyMap()" checked>Study</label>
<!--             <label><input type="radio" name="category" value="c" onclick="getStudyMap()">Career</label> -->
        </div>
    </div>
    <div class="searchArea">
        <!--             <div class="test">
                        <input type="search" class="testinput" name="test" placeholder="Search" onclick="">
                        <div class="icon"></div>
                    </div> -->
<%--        <input type="search" class="search" name="keyWord" placeholder="Search" onKeyDown="javascript: if (event.keyCode == 13) {findRoadMap()}">--%>
        <input type="search" id="keyword" class="search" name="keyWord" placeholder="Search" onKeyDown="javascript: if (event.keyCode == 13) {getStudyMap()}">
    </div>
</div>
<div class="middle">
    <div class="swiper mySwiper">
        <div class="swiper-wrapper">
        </div>
        <div class="swiper-button-next"></div>
        <div class="swiper-button-prev"></div>
        <div class="swiper-pagination"></div>
    </div>
</div>
</body>
</html>
