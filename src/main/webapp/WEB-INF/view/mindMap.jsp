<%@ page import="java.util.Map" %>
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
        <h2 class="timeline-header__title">스터디로드맵에서 넘어올때 라벨가져옴</h2>
        <h3 class="timeline-header__subtitle"><%=en_name %></h3>
    </div>
    <div id="MindMap">
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

<%-- modal 기본 --%>
<div class="modal-container" id="m2-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <h1 class="modal__title" id="modal__title">네트워크</h1>
<%--        <p class="modal__text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Facilis ex dicta maiores libero minus obcaecati iste optio, eius labore repellendus.</p>--%>
        <div>링크: <input type="text" class="modal__link" id="modal__link" readonly></div>
        <div>참고서적 제목:
<%--            <img src="https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1596281%3Ftimestamp%3D20211006162308" alt="x">--%>

            <input type="text" class="modal__book__title" id="modal__book__title" readonly>
        </div>
        <div>
            참고서적 링크: <input type="text" class="modal__book_link" id="modal__book__link" readonly>
        </div>
        <div>내용: <textarea rows="5" cols="33" class="modal__content" id="modal__content" readonly></textarea></div>
        <button class="modal__btn" onclick="fnOpenModal('#m3-o');">추가</button>
        <button class="modal__btn" onclick="fnOpenModal('#m4-o');">수정</button>
        <button class="modal__btn" onclick="fnOpenModal('#m5-o');">삭제</button>
        <button class="modal__btn" onclick="fnCloseModal('#m2-o');" >취소</button>
        <a onclick="fnCloseModal('#m2-o');" class="link-2"></a>
        <span type="text" hidden id="modal__mindId" ></span>
    </div>
</div>
<%-- modal 기본 끝 --%>

<%-- modal 추가 --%>
<div class="modal-container" id="m3-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <h1>추가하기</h1>
        <div>제목: <input type="text" id="modal__title-add"></div>
        <div>링크: <input type="text" id="modal__link-add"></div>
        <div>참고서적 제목:
            <%--            <img src="https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1596281%3Ftimestamp%3D20211006162308" alt="x">--%>
            <input type="text"  id="modal__book__title-add">
        </div>
        <div>
            참고서적 링크: <input type="text" id="modal__book_link-add">
        </div>
        <div>내용: <textarea rows="5" cols="33"  id="modal__content-add"></textarea></div>
        <button class="modal__btn" onclick="insertMindAndNodeData(document.getElementById('modal__mindId').innerText)">저장</button>
        <button class="modal__btn" onclick="fnCloseModal('#m3-o');" >취소</button>
        <a onclick="fnCloseModal('#m3-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 추가 끝 --%>

<%-- modal 수정 --%>
<div class="modal-container" id="m4-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <h1>수정하기</h1>
        <div>제목: <input type="text" class="modal__title" id="modal__title-mod"></div>
        <div>링크: <input type="text" class="modal__link" id="modal__link-mod"></div>
        <div>참고서적 제목:
            <%--            <img src="https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1596281%3Ftimestamp%3D20211006162308" alt="x">--%>
            <input type="text" class="modal__book__title" id="modal__book__title-mod">
        </div>
        <div>
            참고서적 링크: <input type="text" class="modal__book_link" id="modal__book_link-mod">
        </div>
        <div>내용: <textarea rows="5" cols="33" class="modal__content" id="modal__content-mod"></textarea></div>
        <button class="modal__btn" onclick="updateMindAndNodeData(document.getElementById('modal__mindId').innerText);">저장</button>
        <button class="modal__btn" onclick="fnCloseModal('#m4-o');" >취소</button>
        <a onclick="fnCloseModal('#m4-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 수정 끝 --%>

<%-- modal 삭제 --%>
<div class="modal-container" id="m5-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <h1 id="modal__title-del">삭제하시겠습니까?</h1>
        <button class="modal__btn" onclick="deleteMindAndNodeData(document.getElementById('modal__mindId').innerText);">예</button>
        <button class="modal__btn" onclick="fnCloseModal('#m5-o');" >아니오</button>
        <a onclick="fnCloseModal('#m5-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 삭제 끝 --%>


<
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/study_mindMap/cytoscape.min.js"></script>
<%--    <script src="https://unpkg.com/webcola@3.4.0/WebCola/cola.min.js"></script>--%>
<script src="http://marvl.infotech.monash.edu/webcola/cola.min.js"></script>
<%--    <script src="https://unpkg.com/cytoscape-cola@2.3.0/cytoscape-cola.js"></script>--%>
<script src="https://cdn.jsdelivr.net/npm/cytoscape-cola@2.3.0/cytoscape-cola.js"></script>
<%--<script type="module" src="${pageContext.request.contextPath}/resources/js/study_mindMap/study_mindMap.js"></script>--%>

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
                            "id": "<%=node.getMindId()%>",
                            "label": "<%=node.getMindLabel()%>"
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
                                "source": "<%=node.getSource()%>",
                                "target": "<%=node.getTarget()%>"
                            }
                    }
            );
            console.log(node_data);
            console.log("node_data.nodes.mindId");
            console.log(node_data.nodes);
    <%
            }
        }
    %>

//노드 그리기
// window.addEventListener('DOMContentLoaded', function(){ // on dom ready

    // photos from flickr with creative commons license

    // // Rank
    // const cy_for_rank = cytoscape({
    //   elements: data
    // });
    // const pageRank = cy_for_rank.elements().pageRank();

    const nodeMaxSize = 50;
    const nodeMinSize = 5;
    const nodeActiveSize = 28;
    const fontMaxSize = 8;
    const fontMinSize = 5;
    const fontActiveSize = 7;

    const edgeWidth = '6px';
    var edgeActiveWidth = '6px';
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
                    'width': 80,
                    'height': 80,
                    'label': 'data(label)',
                    'border-color': '#000',
                    'border-width': 3,
                    'border-opacity': 0.5
                }
            },
            {
                selector: 'edge',
                style: {
                    'width': 6,
                    'curve-style': 'bezier',
                    'line-color': '#ffaaaa',
                    'source-arrow-color': '#ffaaaa',
                    'source-arrow-shape': 'triangle'
                }
            }
        ],

        layout: {
            name: 'cola',
            directed: true,
            animate: false,
            graviryRangeCompound: 1.5,
            fit: true,
            tile: true
        },
        wheelSensitivity: 0.25
    }); // cy init


    // 노드 레이아웃 설정
    const layoutConfig = {
        name: "cola",
        handleDisconnected: true,
        animate: true,
        avoidOverlap: true,
        infinite: false,
        unconstrIter: 1,
        userConstIter: 0,
        allConstIter: 1,
        ready: e => {
            e.cy.fit()
            e.cy.center()
        }
    }

    // 클릭시 반응
    cy.on('tap', 'node', evt => {

        cy.nodes().forEach(node => {
            node.lock();
        });

        const currentNodeId = create_UUID();
        const currentEdgeId = create_UUID();

        const targetId = evt.target.data('id'); //cy.nodes()[Math.floor(Math.random() * cy.nodes().length)].data('id')

        getMindDataByAjax(targetId)

        fnOpenModal('#m2-o');

        const layout = cy.makeLayout(layoutConfig);
        layout.run();

        layout.on("layoutstop", () => {
            cy.nodes().forEach(node => {
                node.unlock();
            })
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
            // var rank = pageRank.rank(target);
            // target.style('width', nodeMaxSize * rank + nodeMinSize);
            // target.style('height', nodeMaxSize * rank + nodeMinSize);
            // target.style('font-size', fontMaxSize * rank + fontMinSize);
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

    // UUID 생성기
    function create_UUID(){
        var dt = new Date().getTime();
        var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = (dt + Math.random()*16)%16 | 0;
            dt = Math.floor(dt/16);
            return (c=='x' ? r :(r&0x3|0x8)).toString(16);
        });
        return uuid;
    }

    function insertMindAndNodeData(mindmind) {

        let query = {
            mindLabel : $('#modal__title-add').val(),
            url: $('#modal__book_link-add').val(),
            bookTitle: $('#modal__book__title-add').val(),
            bookLink: $('#modal__book_link-add').val(),
            mindContents: $('#modal__content-add').val()
        }

        console.log("query",query);
        console.log(query);
        console.log("mindmind:"+mindmind);
        console.log(mindmind);

        $.ajax({
            url: "/mindmap/"+"<%=mindMapInfo.get(0).getStudyRoadNodeId()%>/"+mindmind,
            type: "post",
            data: query,
            success: function (data) {
                if (data) {
                    cy.nodes().forEach(node => {
                        node.lock();
                    });
                    cy.add([
                        {
                            group: 'nodes',
                            data: {
                                id: data.source,
                                label: $('#modal__title-add').val()
                            }
                        },
                        {
                            group: 'edges',
                            data: {
                                id: data.mindId,
                                source: data.source,
                                target: data.target
                            }
                        }
                    ]);
                    const layout = cy.makeLayout(layoutConfig);
                    layout.run();

                    layout.on("layoutstop", () => {
                        cy.nodes().forEach(node => {
                            node.unlock();
                        })
                    })
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
            "url": $('#modal__book_link-mod').val(),
            "bookTitle": $('#modal__book__title-mod').val(),
            "bookLink": $('#modal__book_link-mod').val(),
            "mindContents": $('#modal__content-mod').val()
        }
        console.log("##############################")
        console.log(query)

        $.ajax({
            url: "/mindmap/"+"<%=mindMapInfo.get(0).getStudyRoadNodeId()%>/"+mindmind,
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
                        }
                    }
                    const layout = cy.makeLayout(layoutConfig);
                    layout.run();

                    layout.on("layoutstop", () => {
                        cy.nodes().forEach(node => {
                            node.unlock();
                        })
                    })
                    fnCloseModal('#m4-o');
                    fnCloseModal('#m2-o');
                } else {
                    console.log("data 이상")
                }
            }
        });
    }

    // 마인드 정보, 노드 정보 삭제
    function deleteMindAndNodeData(mindmind) {
        $.ajax({
            url: "/mindmap/"+"<%=mindMapInfo.get(0).getStudyRoadNodeId()%>/"+mindmind,
            type: "delete",
            success: function (data) {
                if (data===0) {
                    cy.nodes().forEach(node => {
                        node.lock();
                    });
                    cy.remove(cy.$('#'+mindmind));

                    const layout = cy.makeLayout(layoutConfig);
                    layout.run();

                    layout.on("layoutstop", () => {
                        cy.nodes().forEach(node => {
                            node.unlock();
                        })
                    })
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
        $(id).css("display", "flex");

        // if (id==='#m-3') {
        //     getMindDataByAjax($('modal__mindId').text());
        // }
    }
    // 모달 종료
    function fnCloseModal(id){
        // $('#m2-o').css("display", "none");
        $(id).css("display", "none");
    }

    // ajax로 마인드 정보 가져오기
    function getMindDataByAjax(targetId) {
        $.ajax({
            url: "/mindmap/"+"<%=mindMapInfo.get(0).getStudyRoadNodeId()%>/"+targetId,
            type: "get",
            success: (data) => {
                console.log(data);
                if (data) {
                    $(".modal__title").text(data.mindLabel);
                    $(".modal__title").val(data.mindLabel);
                    $(".modal__link").val(data.url);
                    $(".modal__book__title").val(data.bookTitle);
                    $(".modal__book_link").val(data.bookLink);
                    $(".modal__content").val(data.mindContents);
                    $("#modal__mindId").text(targetId);
                } else {
                    console.log("data 이상");
                }
            }
        });
    }




</script>
</body>
</html>