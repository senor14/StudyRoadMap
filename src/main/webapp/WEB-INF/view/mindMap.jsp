<%@ page import="java.util.List" %>
<%@ page import="domain.StudyMindData" %>
<%@ page import="domain.StudyMindNodeData" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
    
<%
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/input.css">
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

        <button class="modal__btn" onclick="fnOpenModal('#m3-o');">추가</button>
        <button class="modal__btn" style="float: right" onclick="fnCloseModal('#m2-o');" >취소</button>
        <button class="modal__btn" style="float: right" onclick="fnOpenModal('#m5-o');">삭제</button>
        <button class="modal__btn" style="float: right" onclick="fnOpenModal('#m4-o');">수정</button>
        <a onclick="fnCloseModal('#m2-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 기본 끝 --%>

<%-- modal 추가 --%>
<div class="modal-container" id="m3-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <div class="text-center">
            <h1>추가</h1>
        </div>
        <div class="input_body">
            <label for="modal__title-add" class="inp" style="max-width: 440px;">
                <input type="text" id="modal__title-add" placeholder="&nbsp;">
                <span class="label">Title</span>
                <svg width="120px" height="26px" viewBox="0 0 120 26">
                    <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                </svg>
                <span class="border"></span>
            </label>
        </div>
        <div class="input_body">
            <label for="modal__link-add" class="inp" style="max-width: 440px;">
                <input type="text" id="modal__link-add" placeholder="&nbsp;">
                <span class="label">Link</span>
                <svg width="120px" height="26px" viewBox="0 0 120 26">
                    <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                </svg>
                <span class="border"></span>
            </label>
        </div>
        <div style="display: grid;">
            <div class="input_body" style="grid-column:1/4">
                <label for="modal__book__title-add" class="inp">
                    <input type="text" id="modal__book__title-add" onKeyDown="javascript: if (event.keyCode == 13) {searchBook(document.getElementById('modal__book__title-add').value, this)}" placeholder="&nbsp;">
                    <span class="label">참고서적 Title</span>
                    <svg width="120px" height="26px" viewBox="0 0 120 26">
                        <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                    </svg>
                    <span class="border"></span>
                </label>
            </div>
            <div style="grid-column: 4"><button class="modal__btn" onclick="searchBook(document.getElementById('modal__book__title-add').value, this)">검색</button></div>
        </div>
        <div class="input_body">
            <label for="modal__book_link-add" class="inp"   style="max-width: 440px;">
                <input type="text" id="modal__book_link-add" placeholder="&nbsp;">
                <span class="label">참고서적 Link</span>
                <svg width="120px" height="26px" viewBox="0 0 120 26">
                    <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                </svg>
                <span class="border"></span>
            </label>
        </div>
        <div>
            <textarea rows="5" cols="33"  id="modal__content-add" placeholder="Contents" ></textarea>
        </div>
<%--        <div>제목: <input type="text" id="modal__title-add"></div>--%>
<%--        <div>링크: <input type="text" id="modal__link-add"></div>--%>
<%--        <div>참고서적 제목:--%>
<%--            &lt;%&ndash;            <img src="https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1596281%3Ftimestamp%3D20211006162308" alt="x">&ndash;%&gt;--%>
<%--            <input type="text"  id="modal__book__title-add" onKeyDown="javascript: if (event.keyCode == 13) {searchBook(document.getElementById('modal__book__title-add').value, this)}">--%>
<%--            <button onclick="searchBook(document.getElementById('modal__book__title-add').value, this)">검색</button>--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            참고서적 링크: <input type="text" id="modal__book_link-add">--%>
<%--        </div>--%>
<%--        <div>내용: <textarea rows="5" cols="33"  id="modal__content-add"></textarea></div>--%>
        <button class="modal__btn" onclick="insertMindAndNodeData(document.getElementById('modal__mindId').innerText)">저장</button>
        <button class="modal__btn" style="float: right" onclick="fnCloseModal('#m3-o');" >취소</button>
        <a onclick="fnCloseModal('#m3-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 추가 끝 --%>

<%-- modal 수정 --%>
<div class="modal-container" id="m4-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <div class="text-center">
            <h1>수정하기</h1>
        </div>
        <div class="input_body">
            <label for="modal__title-mod" class="inp" style="max-width: 440px;">
                <input type="text" class="modal__title" id="modal__title-mod" placeholder="&nbsp;">
                <span class="label">Title</span>
                <svg width="120px" height="26px" viewBox="0 0 120 26">
                    <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                </svg>
                <span class="border"></span>
            </label>
        </div>
        <div class="input_body">
            <label for="modal__link-mod" class="inp" style="max-width: 440px;">
                <input type="text" class="modal__link" id="modal__link-mod" placeholder="&nbsp;">
                <span class="label">Link</span>
                <svg width="120px" height="26px" viewBox="0 0 120 26">
                    <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                </svg>
                <span class="border"></span>
            </label>
        </div>
        <div style="display: grid;">
            <div class="input_body" style="grid-column:1/4">
                <label for="modal__book__title-mod" class="inp">
                    <input type="text" class="modal__book__title" id="modal__book__title-mod" onKeyDown="javascript: if (event.keyCode == 13) {searchBook(document.getElementById('modal__book__title-mod').value, this)}" placeholder="&nbsp;">
                    <span class="label">참고서적 Title</span>
                    <svg width="120px" height="26px" viewBox="0 0 120 26">
                        <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                    </svg>
                    <span class="border"></span>
                </label>
            </div>
            <div style="grid-column: 4"><button class="modal__btn" onclick="searchBook(document.getElementById('modal__book__title-mod').value, this)">검색</button></div>
        </div>
        <div class="input_body">
            <label for="modal__book_link-mod" class="inp" style="max-width: 440px;">
                <input type="text" class="modal__book_link" id="modal__book_link-mod" placeholder="&nbsp;">
                <span class="label">참고서적 Link</span>
                <svg width="120px" height="26px" viewBox="0 0 120 26">
                    <path d="M0,25 C21,25 46,25 74,25 C102,25 118,25 120,25"></path>
                </svg>
                <span class="border"></span>
            </label>
        </div>
        <div>
            <textarea rows="5" cols="33" onclick="searchBook(document.getElementById('modal__book__title-mod').value, this)" placeholder="Contents" ></textarea>
        </div>

        <%--
        <div>제목: <input type="text" class="modal__title" id="modal__title-mod"></div>
        <div>링크: <input type="text" class="modal__link" id="modal__link-mod"></div>
        <div>참고서적 제목:
            &lt;%&ndash;            <img src="https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1596281%3Ftimestamp%3D20211006162308" alt="x">&ndash;%&gt;
            <input type="text" class="modal__book__title" id="modal__book__title-mod" onKeyDown="javascript: if (event.keyCode == 13) {searchBook(document.getElementById('modal__book__title-mod').value, this)}">
            <button onclick="searchBook(document.getElementById('modal__book__title-mod').value, this)">검색</button>
        </div>
        <div>
            참고서적 링크: <input type="text" class="modal__book_link" id="modal__book_link-mod">
        </div>
        <div>내용: <textarea rows="5" cols="33" class="modal__content" id="modal__content-mod"></textarea></div>
        --%>
        <button class="modal__btn" onclick="updateMindAndNodeData(document.getElementById('modal__mindId').innerText);">저장</button>
        <button class="modal__btn" style="float:right;" onclick="fnCloseModal('#m4-o');" >취소</button>
        <a onclick="fnCloseModal('#m4-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 수정 끝 --%>

<%-- modal 삭제 --%>
<div class="modal-container" id="m5-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal alert_modal">
        <div class="text-center">
            <h1 id="modal__title-del">삭제하시겠습니까?</h1>
        </div>
        <button class="modal__btn" onclick="deleteMindAndNodeData(document.getElementById('modal__mindId').innerText);">예</button>
        <button class="modal__btn" style="float:right;" onclick="fnCloseModal('#m5-o');" >아니오</button>
        <a onclick="fnCloseModal('#m5-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 삭제 끝 --%>

<%-- modal 삭제 불가능--%>
<div class="modal-container" id="m6-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <h1 id="modal__title-del__impossible">루트 노드는 수정/삭제할 수 없습니다.</h1>
        <button class="modal__btn" onclick="fnCloseModal('#m6-o');" >확인</button>
        <a onclick="fnCloseModal('#m6-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 삭제 불가능 끝 --%>

<%-- modal 책 검색 --%>
<div class="modal-container" id="m7-o" style="--m-background: hsla(0, 0%, 0%, .4); ">
    <div class="modal" id="modal__search" style="width: 560px; height: 560px; overflow: auto;">
        <h1 class="modal__book__search" id="modal__booksearch">검색 결과</h1>
        <hr/>
        <a onclick="clearSearchList();" class="link-2"></a>
    </div>
</div>
<%-- modal 책 검색 끝 --%>

<form id="uploadForm" enctype="multipart/form-data">
    <input type="file" id="file" name="fileUpload" style="display:none"/>
</form>

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

<script>
    function searchBook(keyword, target) {
        console.log("keyword: ",keyword)
        fnOpenModal("#m7-o");
        let addOrMod;
        let id = target.parentNode.parentNode.parentNode.parentNode.parentNode.id;

        if (id === 'm4-o') {
            addOrMod = "mod";
        } else if (id === 'm3-o') {
            addOrMod = "add";
        }
        $.ajax({
            url: "https://dapi.kakao.com/v3/search/book?target=title",
            type: "get",
            data: {
                query: keyword
            },
            headers: {
                Authorization: "KakaoAK 15a6456fd0ac32b2bce9d44610d4f72a"
            },
        }).done(function (msg) {
                console.log(msg);
                for (var i = 0; i < 10; i++){
                    $("#modal__search").append(
                        "<div class='search__list' id='"+ addOrMod +"' style='display: flex; padding : 10px;' onclick='insertBookInfo(this)'>" +
                            "<div><img src='" + msg.documents[i].thumbnail + "'/></div>" +
                            "<div style='display: flex; flex-direction: column; margin: 10px;'>" +
                                "<div><h2>" + msg.documents[i].title + "</h2></div>" +
                                "<div>" + msg.documents[i].authors + "</div>" +
                                "<div hidden><a href='" + msg.documents[i].url + "'>" + msg.documents[i].url + "</a></div>" +
                            "</div>" +
                        "</div>" +
                        "<hr class='search__list'/>"
                    );
                }
            });
    }

    function insertBookInfo(target) {
        // console.log("target");
        // console.log(target);
        console.log("target: ", target)
        let bookTitle = target.lastChild.firstChild.textContent;
        // console.log(target.lastChild.firstChild.textContent)
        let bookUrl = target.lastChild.lastChild.textContent;
        // console.log(target.lastChild.lastChild.textContent)
        if (target.id === "mod") {
            document.getElementById('modal__book__title-mod').value = bookTitle;
            document.getElementById('modal__book_link-mod').value = bookUrl;
        } else if (target.id === "add") {
            document.getElementById('modal__book__title-add').value = bookTitle;
            document.getElementById('modal__book_link-add').value = bookUrl;
        }


        clearSearchList();
    }

    function clearSearchList() {
        $(".search__list").remove();
        fnCloseModal('#m7-o');
    }
</script>

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

//노드 그리기
// window.addEventListener('DOMContentLoaded', function(){ // on dom ready

    // photos from flickr with creative commons license

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
        console.log("이벤트")
        console.log(evt.target)
        const target = evt.target._private; //cy.nodes()[Math.floor(Math.random() * cy.nodes().length)].data('id')

        clearNodeInfo();
        getMindDataByAjax(target);

        fnOpenModal('#m2-o');

        const layout = cy.makeLayout(layoutConfig);
        layout.run();

        // layout.on("layoutstop", () => {
            cy.nodes().forEach(node => {
                node.unlock();
            })
        // })
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

    cy.on('tapend', 'node', function (e) {
        document.getElementById("modal__x").innerText = e.target.renderedPosition('x');
        document.getElementById("modal__y").innerText = e.target.renderedPosition('y');
        updateNodePosition(e.target.data('id'), e.target.renderedPosition())
    })

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

    function insertMindAndNodeData(mindmind) {
        let query = {
            mindId : $('#modal__mindId').text(),
            key : $('#modal__key').text(),
            roadId : $('#modal__roadId').text(),
            nodeId : $('#modal__nodeId').text(),
            group: $('#modal__group').text(),
            mindLabel : $('#modal__title-add').val(),
            url: $('#modal__book_link-add').val(),
            bookTitle: $('#modal__book__title-add').val(),
            bookLink: $('#modal__book_link-add').val(),
            mindContents: $('#modal__content-add').val(),
            x: $('#modal__x').text(),
            y: $('#modal__y').text()
        }
        $.ajax({
            url: "/mindmaps/",
            type: "post",
            data: query,
            success: function (data) {
                console.log("data.nodeMindId:",data.nodeMindId);
                console.log("data.key:",data.key);
                console.log("data.roadId:",data.roadId);
                console.log("data.nodeId:",data.nodeId);
                console.log("data.mindLabel:",data.mindLabel);
                console.log("data.edgeMindId:",data.edgeMindId);
                console.log("data.source:",data.source);
                console.log("data.target:",data.target);
                if (data) {
                    cy.nodes().forEach(node => {
                        node.lock();
                    });
                    node_data.nodes.push(
                        {
                            data:
                                {
                                    "mindId": data.nodeMindId,
                                    "id": data.nodeMindId,
                                    "roadId": data.roadId,
                                    "nodeId": data.nodeId,
                                    "group": "nodes",
                                    "label": data.mindLabel
                                },
                            renderedPosition:
                                {
                                    "x": document.getElementById("modal__x").innerText,
                                    "y": String(Number(document.getElementById("modal__y").innerText)+200*cy.zoom())
                                }
                        }
                    );

                    node_data.edges.push(
                        {
                            data:
                                {
                                    "id": data.edgeMindId,
                                    "roadId": data.roadId,
                                    "nodeId": data.nodeId,
                                    "group": "edges",
                                    "source": data.nodeMindId,
                                    "target": data.target
                                }
                        }
                    );

                    cy.add([
                        {
                            group: 'nodes',
                            data: {
                                mindId: data.nodeMindId,
                                id: data.source,
                                roadId: data.roadId,
                                nodeId: data.nodeId,
                                group: "nodes",
                                label: data.mindLabel
                            },
                            renderedPosition: {
                                x: document.getElementById("modal__x").innerText,
                                y: String(Number(document.getElementById("modal__y").innerText)+200*cy.zoom())
                            }
                        },
                        {
                            group: 'edges',
                            data: {
                                id: data.mindId,
                                roadId: data.roadId,
                                nodeId: data.nodeId,
                                group: "edges",
                                source: data.source,
                                target: data.target
                            }
                        }
                    ]);



                    const layout = cy.makeLayout(layoutConfig);
                    layout.run();
                    //
                    // layout.on("layoutstop", () => {
                        cy.nodes().forEach(node => {
                            node.unlock();
                        })
                    // })
                    fnCloseModal('#m3-o');
                    fnCloseModal('#m2-o');
                } else {
                    console.log("data 이상")
                }
            }
        });

    }

    // 마인드 정보, 노드 정보 수정
    function updateMindAndNodeData(mindmind) {

        let query = {
            "mindLabel" : $('#modal__title-mod').val(),
            "url": $('#modal__link-mod').val(),
            "bookTitle": $('#modal__book__title-mod').val(),
            "bookLink": $('#modal__book_link-mod').val(),
            "mindContents": $('#modal__content-mod').val()
        }

        $.ajax({
            url: "/mindmaps/"+mindmind,
            type: "put",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(query),
            success: function (data) {
                if (data===0) {
                    cy.nodes().forEach(node => {
                        node.lock();
                    });
                    for (let d in node_data.nodes) {
                        if (node_data.nodes[d].data.id === mindmind) {
                            node_data.nodes[d].data.label = $('#modal__title-mod').val()
                            break
                        }
                    }
                    const layout = cy.makeLayout(layoutConfig);
                    layout.run();
                    //
                    // layout.on("layoutstop", () => {
                        cy.nodes().forEach(node => {
                            node.unlock();
                        })
                    // })
                    fnCloseModal('#m4-o');
                    fnCloseModal('#m2-o');
                } else {
                    console.log("data 이상")
                }
            }
        });
    }

    // 노드 위치 정보 수정
    function updateNodePosition(mindmind, position) {

        let query = {
            "x" : position.x,
            "y": position.y
        }

        $.ajax({
            url: "/mindmaps/"+mindmind+"/position",
            type: "put",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(query),
            success: function (data) {
                if (data) {
                    cy.nodes().forEach(node => {
                        node.lock();
                    });
                    for (let d in node_data.nodes) {
                        if (node_data.nodes[d].data.id === mindmind) {
                            node_data.nodes[d].renderedPosition.x = data.x
                            node_data.nodes[d].renderedPosition.y = data.y
                            break
                        }
                    }
                    const layout = cy.makeLayout(layoutConfig);
                    layout.run();
                    //
                    // layout.on("layoutstop", () => {
                        cy.nodes().forEach(node => {
                            node.unlock();
                        })
                    // })
                } else {
                    console.log("data 이상")
                }
            }
        });
    }

    // 마인드 정보, 노드 정보 삭제
    function deleteMindAndNodeData(mindmind) {
        if (mindmind === "<%=mindMapInfo.get(0).getStudyRoadNodeId()%>") {
            return;
        }
        $.ajax({
            url: "/mindmaps/"+mindmind,
            type: "delete",
            success: function (data) {
                if (data===0) {
                    cy.nodes().forEach(node => {
                        node.lock();
                    });
                    cy.remove(cy.$('#'+mindmind));

                    const layout = cy.makeLayout(layoutConfig);
                    layout.run();
                    //
                    // layout.on("layoutstop", () => {
                        cy.nodes().forEach(node => {
                            node.unlock();
                        })
                    // })
                    fnCloseModal('#m5-o');
                    fnCloseModal('#m2-o');
                } else {
                    console.log("data 이상")
                }
            }
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

                    console.log("data:")
                    console.log(data)
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
        $("#modal__link-a").removeAttr("href");
        $("#modal__link-a").text("");
        $("#modal__book__link-a").removeAttr("href");
        $("#modal__book__link-a").text("");
    }

</script>


</body>
</html>