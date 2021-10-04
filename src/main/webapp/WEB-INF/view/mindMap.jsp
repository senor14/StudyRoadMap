<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    /* 사용자 영문 이름 */
    String en_name = " senorKim"; /* (String)request.getAttribute("") */
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>redirect</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/study_mindMap/study_mindMap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/study_mindMap/modal.css">

</head>
<body>

<div class="timeline-container" id="timeline-1">
    <div class="timeline-header">
        <h2 class="timeline-header__title">네트워크</h2>
        <h3 class="timeline-header__subtitle"><%=en_name %></h3>
    </div>
    <div id="MindMap">
        <div class="timeline-item" data-text="Education">
            <a href="#m2-o" <%--id="m1-c"--%>>
            <div class="timeline__content"><img class="timeline__img" src="https://api.time.com/wp-content/uploads/2015/11/151109_col_coverdell.jpg" />
                <h2 class="timeline__content-title">Education</h2>
            </div>
            </a>
        </div>
        <div class="map_box">
            <div id="cy"></div>
        </div>
    </div>
    <div class="comment">
        <input type="text" id="comment_text" name="comment_text" minlength="4" maxlength="8" size="10">
        <input type="button" id="comment_button" name="comment_button" minlength="4" value="입력">
    </div>
</div>

<div class="demo-footer"><a href="http://www.turkishnews.com/Ataturk/life.htm" target="_blank">Source/Kaynak</a></div>

<%-- modal --%>
<div class="modal-container" id="m2-o" style="                                                                                                                                                                                                                                                                                                                                                                                                                                              --m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <h1 class="modal__title">Modal 2 Title</h1>
        <p class="modal__text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Facilis ex dicta maiores libero minus obcaecati iste optio, eius labore repellendus.</p>
        <button class="modal__btn">Button &rarr;</button>
        <button class="modal__btn">Button &rarr;</button>
        <a href="#" class="link-2"></a>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/study_mindMap/cytoscape.min.js"></script>
<%--    <script src="https://unpkg.com/webcola@3.4.0/WebCola/cola.min.js"></script>--%>
<script src="http://marvl.infotech.monash.edu/webcola/cola.min.js"></script>
<%--    <script src="https://unpkg.com/cytoscape-cola@2.3.0/cytoscape-cola.js"></script>--%>
<script src="https://cdn.jsdelivr.net/npm/cytoscape-cola@2.3.0/cytoscape-cola.js"></script>
<script type="module" src="${pageContext.request.contextPath}/resources/js/study_mindMap/study_mindMap.js"></script>
</body>
</html>