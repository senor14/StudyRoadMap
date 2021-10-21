<%@ page import="java.util.List" %>
<%@ page import="domain.StudyMindData" %>
<%@ page import="domain.StudyMindNodeData" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
    
<%
    /* 사용자 영문 이름 */
    String en_name = " senorKim"; /* (String)request.getAttribute("") */

    List<StudyMindData> mindMapInfo = (List<StudyMindData>)request.getAttribute("mindMapInfo");
    List<StudyMindNodeData> mindMapNode = (List<StudyMindNodeData>)request.getAttribute("mindMapNode");
    String userUuid = (String)session.getAttribute("SS_USER_UUID");
    String userId = (String) request.getAttribute("userId");
    String title = (String) request.getAttribute("title");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>redirect</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/study_mindMap/study_mindMap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/study_mindMap/modal.css">
</head>
<body>

<div style="position: fixed; background-color: ghostwhite; width: 100%; z-index: 3000; height:50px">
    <a href="/index"><i class="fas fa-home" style="position: fixed; margin-left: 10px; margin-top:10px; font-size: 30px; color:black"></i></a>
    <a href="javascript:history.back();"><i class="far fa-arrow-alt-circle-left" style="position: fixed; margin-left: 50px; margin-top:10px; font-size: 30px; color:black"></i></a>
</div>

<div class="timeline-container" id="timeline-1">
    <div class="timeline-header">
        <h2 class="timeline-header__title"><%=title%></h2>
        <h3 class="timeline-header__subtitle"><%=userId%></h3>
    </div>
    <div id="mind_map">
        <div class="map_box">
            <div id="cy"></div>
        </div>
    </div>
</div>

<div class="demo-footer"><a href="http://www.turkishnews.com/Ataturk/life.htm" target="_blank">Source/Kaynak</a></div>

<%-- modal 기본 --%>
<div class="modal-container" id="m2-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <div style="text-align: center;">
            <h1 class="modal__title" id="modal__title">네트워크</h1>
        </div>
        <div style="display: grid; column-gap: 5px; margin-top: 5em;">
            <div style="grid-column: 1; grid-row:1;  max-width:25rem; word-wrap:break-word">
                링크: <a id="modal__link-a"></a>
            </div>
            <div class="input_body" style="grid-column: 1; grid-row:2;">
                <label for="modal__book__title" class="inp">
                    <input type="text" class="modal__book__title" id="modal__book__title" disabled placeholder="&nbsp;">
                    <span class="label">참고서적 Title</span>
                    <svg width="120px" height="26px" viewBox="0 0 120 26">
                        <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                    </svg>
                    <span class="border"></span>
                </label>

            </div>
            <div style="grid-column: 1; grid-row: 3; max-width:25rem; word-wrap:break-word">
                참고서적 Link: <a id="modal__book__link-a"></a>
            </div>
            <div class="color_body" style="grid-column: 2/4; grid-row:1/4">
                <textarea rows="5" cols="33" class="modal__content" id="modal__content" disabled></textarea>
            </div>
        </div>
        <%--        <div>링크: <a id="modal__link-a"></a></div>--%>
        <%--        <div>참고서적 제목:--%>
        <%--            <input type="text" class="modal__book__title" id="modal__book__title" disabled>--%>
        <%--        </div>--%>
        <%--        <div>--%>
        <%--            참고서적 링크: <a id="modal__book__link-a"></a>--%>
        <%--        </div>--%>
        <%--        <div>내용: <textarea rows="5" cols="33" class="modal__content" id="modal__content" disabled></textarea></div>--%>

        <button class="modal__btn" style="float: right" onclick="fnCloseModal('#m2-o');" >확인</button>
        <a onclick="fnCloseModal('#m2-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 기본 끝 --%>

<div id="hidden__box" >
    <span hidden id="modal__mindId" ></span>
    <span hidden id="modal__key" ></span>
    <span hidden id="modal__roadId" ></span>
    <span hidden id="modal__nodeId" ></span>
    <span hidden id="modal__group" ></span>
    <span hidden id="modal__mindLabel" ></span>
    <span hidden id="modal__x" ></span>
    <span hidden id="modal__y" ></span>
    <span hidden id="modal__source" ></span>
    <span hidden id="modal__target" ></span>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/study_mindMap/cytoscape.min.js"></script>
<script src="http://marvl.infotech.monash.edu/webcola/cola.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/cytoscape-cola@2.3.0/cytoscape-cola.js"></script>
<script src="https://html2canvas.hertzen.com/dist/html2canvas.min.js"></script>

<%-- 마인드맵 화면 --%>
<script>
    // cytoscape.use( cola );

    // 노드 데이터 가져올 껍데기
    let node_data = {
        nodes: [],
        edges: []
    }

    // 껍데기에 노드 정보 넣는 로직
    <%
        for (StudyMindNodeData node : mindMapNode) {
            if (node.getGroup().equals("nodes")) {
    %>
            node_data.nodes.push(
                {
                    data:
                        {
                            "mindId": "<%=node.getMindId()%>",
                            "id": "<%=node.getKey()%>",
                            "roadId": "<%=node.getStudyRoadId()%>",
                            "nodeId": "<%=node.getStudyRoadNodeId()%>",
                            "group": "<%=node.getGroup()%>",
                            "label": "<%=node.getMindLabel()%>"
                        },
                    renderedPosition:
                        {
                            "x": "<%=node.getX()%>",
                            "y": "<%=node.getY()%>",
                        }
                }
            );
            console.log(node_data);
    <%
            } else if (node.getGroup().equals("edges")) {
    %>
            node_data.edges.push(
                    {
                        data:
                            {
                                "id": "<%=node.getMindId()%>",
                                "roadId": "<%=node.getStudyRoadId()%>",
                                "nodeId": "<%=node.getStudyRoadNodeId()%>",
                                "group": "<%=node.getGroup()%>",
                                "source": "<%=node.getSource()%>",
                                "target": "<%=node.getTarget()%>"
                            }
                    }
            );
    <%
            }
        }
    %>


    // Rank
    let cy_for_rank = cytoscape({
      elements: node_data
    });

    const nodeMaxSize = 200;
    const nodeMinSize = 40;
    const nodeActiveSize = 120;
    const fontMaxSize = 70;
    const fontMinSize = 40;
    const fontActiveSize = 60;

    const edgeWidth = '8px';
    var edgeActiveWidth = '8px';
    const arrowScale = 1;
    const arrowActiveScale = 1.2;
    // edge & arrow 크기값

    const dimColor = '#dfe4ea';
    const edgeColor = '#ffaaaa';
    const nodeColor = '#57606f';
    const nodeActiveColor = '#ffa502';

    const successorColor = '#ff6348';
    // 상위 node & edge color
    const predecessorsColor = '#1e90ff';
    // 하위 node & edge color

    const cy = cytoscape({
        container: document.getElementById('cy'),
        elements: node_data,
        style: [ // the stylesheet for the graph
            {
                selector: 'node',
                style: {
                    'background-color': nodeColor,
                    'color': nodeColor,
                    // 'width': 80,
                    'width': function (ele) {
                        return nodeMaxSize * cytoscape({
                            elements: node_data
                        }).elements().pageRank().rank('#' + ele.id()) + nodeMinSize;
                    },
                    // 'height': 80,
                    'height': function (ele) {
                        return nodeMaxSize * cytoscape({
                            elements: node_data
                        }).elements().pageRank().rank('#' + ele.id()) + nodeMinSize;
                    },
                    'font-size': function (ele) {
                        return fontMaxSize * cytoscape({
                            elements: node_data
                        }).elements().pageRank().rank('#' + ele.id()) + fontMinSize;
                    },
                    'label': 'data(label)',
                    'border-color': '#000',
                    'border-width': 3,
                    'border-opacity': 0.5
                }
            },
            {
                selector: 'edge',
                style: {
                    'width': 8,
                    'curve-style': 'bezier',
                    'line-color': '#ffaaaa',
                    'source-arrow-color': '#ffaaaa',
                    'source-arrow-shape': 'triangle'
                }
            }
        ],
        layout: {
            name: 'preset',
            directed: true,
            animate: true,
            // gravityRangeCompound: 1.5,
            fit: true,
            tile: true,
            avoidOverlap: true
        },
        wheelSensitivity: 0.25
    }); // cy init

    // 노드 레이아웃 설정
    const layoutConfig = {
        name: "preset",
        directed: true,
        // handleDisconnected: true,
        animate: false,
        avoidOverlap: true,
        fit: true,
        tile: true,
    }

    // 클릭시 반응
    cy.on('tap', 'node', evt => {

        cy.nodes().forEach(node => {
            node.lock();
        });

        const target = evt.target._private;

        clearNodeInfo();
        getMindDataByAjax(target);

        fnOpenModal('#m2-o');

        const layout = cy.makeLayout(layoutConfig);
        layout.run();

        cy.nodes().forEach(node => {
            node.unlock();
        })
    });

    cy.on('tapstart mouseover', 'node', function (e) {
        setDimStyle(cy, {
            'background-color': dimColor,
            'line-color': dimColor,
            'source-arrow-color': dimColor,
            'color': dimColor
        });

        setFocus(e.target, successorColor, predecessorsColor, edgeActiveWidth, arrowActiveScale);

    });

    cy.on('tapend mouseout', 'node', function (e) {
        setResetFocus(e.cy);
    });


    function setDimStyle(target_cy, style) {
        target_cy.nodes().forEach(function (target) {
            target.style(style);
        });
        target_cy.edges().forEach(function (target) {
            target.style(style);
        });
    }

    function setFocus(target_element, successorColor, predecessorsColor, edgeWidth, arrowScale) {
        target_element.style('background-color', nodeActiveColor);
        target_element.style('color', nodeColor);
        target_element.successors().each(function (e) {
                // 상위  엣지와 노드
                if (e.isEdge()) {
                    e.style('width', edgeWidth);
                    e.style('arrow-scale', arrowScale);
                }
                e.style('color', nodeColor);
                e.style('background-color', successorColor);
                e.style('line-color', successorColor);
                e.style('source-arrow-color', successorColor);
                setOpacityElement(e, 0.5);
            }
        );
        target_element.predecessors().each(function (e) {
            // 하위 엣지와 노드
            if (e.isEdge()) {
                e.style('width', edgeWidth);
                e.style('arrow-scale', arrowScale);
            }
            e.style('color', nodeColor);
            e.style('background-color', predecessorsColor);
            e.style('line-color', predecessorsColor);
            e.style('source-arrow-color', predecessorsColor);
            setOpacityElement(e, 0.5);
        });
        target_element.neighborhood().each(function (e) {
                // 이웃한 엣지와 노드
                setOpacityElement(e, 1);
            }
        );
        target_element.style('width', Math.max(parseFloat(target_element.style('width')), nodeActiveSize));
        target_element.style('height', Math.max(parseFloat(target_element.style('height')), nodeActiveSize));
        target_element.style('font-size', Math.max(parseFloat(target_element.style('font-size')), fontActiveSize));
    }

    function setOpacityElement(target_element, degree) {
        target_element.style('opacity', degree);
    }

    function setResetFocus(target_cy) {

        target_cy.nodes().forEach(function (target) {
            target.style('background-color', nodeColor);
            var rank = cytoscape({
                elements: node_data
            }).elements().pageRank().rank(target);
            target.style('width', nodeMaxSize * rank + nodeMinSize);
            target.style('height', nodeMaxSize * rank + nodeMinSize);
            target.style('font-size', fontMaxSize * rank + fontMinSize);
            target.style('color', nodeColor);
            target.style('opacity', 1);
        });
        target_cy.edges().forEach(function (target) {
            target.style('line-color', edgeColor);
            target.style('source-arrow-color', edgeColor);
            target.style('width', edgeWidth);
            target.style('arrow-scale', arrowScale);
            target.style('opacity', 1);
        });

    }

    // }); // on dom ready
</script>
<%-- 모달 조작 함수 --%>
<script>
    // 모달 오픈
    function fnOpenModal(id){
        // $('#m2-o').css("display", "flex");
        if (document.getElementById("modal__mindId").innerText === "<%=mindMapInfo.get(0).getStudyRoadNodeId()%>"
        && (id === '#m4-o' || id === '#m5-o')) {
            id = '#m6-o'
        }
        $(id).css("display", "flex");
    }
    // 모달 종료
    function fnCloseModal(id){
        // $('#m2-o').css("display", "none");
        $(id).css("display", "none");
        if (id === '#m2-o') {
            clearAddInfo();
        }
    }

    // ajax로 마인드 정보 가져오기
    function getMindDataByAjax(target) {

        if (target.data.mindId) document.getElementById("modal__mindId").innerText = target.data.mindId;
        if (target.data.roadId) document.getElementById("modal__roadId").innerText = target.data.roadId;
        if (target.data.nodeId) document.getElementById("modal__nodeId").innerText = target.data.nodeId;
        if (target.data.label) document.getElementById("modal__mindLabel").innerText = target.data.label;
        if (target.data.group) document.getElementById("modal__group").innerText = target.data.group;
        if (target.data.id) document.getElementById("modal__key").innerText = target.data.id;
        if (target.position.x) document.getElementById("modal__x").innerText = target.position.x;
        if (target.position.y) document.getElementById("modal__y").innerText = target.position.y;
        if (target.data.source) document.getElementById("modal__source").innerText = target.data.source;
        if (target.data.target) document.getElementById("modal__target").innerText = target.data.target;

        if (target.data.group === 'nodes') {
            $.ajax({
                url: "/mindmaps/"+target.data.mindId,
                type: "get",
                success: (data) => {

                    if (data) {
                        $(".modal__title").text(data.mindLabel);
                        $(".modal__title").val(data.mindLabel);
                        $(".modal__link").val(data.url);
                        $(".modal__book__title").val(data.bookTitle);
                        $(".modal__book_link").val(data.bookLink);
                        $(".modal__content").val(data.mindContents);
                        $("#modal__link-a").attr("href", data.url);
                        $("#modal__link-a").text(data.url);
                        $("#modal__book__link-a").attr("href", data.bookLink);
                        $("#modal__book__link-a").text(data.bookLink);
                    } else {
                        console.log("data 이상");
                    }
                }
            });
        }

    }

    // 히든 내용 지우기
    function clearNodeInfo() {
        document.getElementById("modal__mindId").innerText = "";
        document.getElementById("modal__roadId").innerText = "";
        document.getElementById("modal__nodeId").innerText = "";
        document.getElementById("modal__key").innerText = "";
        document.getElementById("modal__group").innerText = "";
        document.getElementById("modal__mindLabel").innerText = "";
        document.getElementById("modal__x").innerText = "";
        document.getElementById("modal__y").innerText = "";
        document.getElementById("modal__source").innerText = "";
        document.getElementById("modal__target").innerText = "";
    }

    function clearAddInfo() {
        $("#modal__title").text("");
        $("#modal__title").val("");
        $("#modal__book__title").val("");
        $("#modal__content").val("");
        $("#modal__title-add").val("");
        $("#modal__link-add").val("");
        $("#modal__book_title-add").val("");
        $("#modal__book_link-add").val("");
        $("#modal__content-add").val("");
        $("#modal__link-a").removeAttribute("href");
        $("#modal__link-a").text("");
        $("#modal__book__link-a").removeAttribute("href");
        $("#modal__book__link-a").text("");
    }

</script>
</body>
</html>