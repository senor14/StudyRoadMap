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
<div class="modal-container" id="m2-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <h1 class="modal__title">네트워크</h1>
<%--        <p class="modal__text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Facilis ex dicta maiores libero minus obcaecati iste optio, eius labore repellendus.</p>--%>
        <div>링크: <input type="text"></div>
        <div>참고서적 제목:
<%--            <img src="https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1596281%3Ftimestamp%3D20211006162308" alt="x">--%>

            <input type="text">
        </div>
        <div>
            참고서적 링크: <input type="text">
        </div>
        <div>내용: <textarea rows="5" cols="33"></textarea></div>
        <button class="modal__btn">추가</button>
        <button class="modal__btn">수정</button>
        <button class="modal__btn" onclick="window.location.href='#m1-o'">삭제</button>
        <button class="modal__btn">취소</button>
        <a onclick="fnCloseModal();" class="link-2"></a>
    </div>
</div>

<div class="modal-container" id="m1-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <a href="#" class="link-2"></a>
    <div class="team-boxed">
        <div class="item">
                <h1>정말 삭제하시겠습니까?</h1>
                <button class="modal__btn">확인</button>
                <button class="modal__btn link-2" onclick="window.location.href='#'">취소</button>
        </div>
    </div>
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
    let data = {
        nodes: [],
        edges: []
    }

        // 껍데기에 노드 정보 넣는 로직
        <%
            for (StudyMindNodeData node : mindMapNode) {
                if (node.getGroup().equals("nodes")) {
        %>
                    data.nodes.push(
                        {
                            data:
                                {
                                    "id": "<%=node.getMindId()%>",
                                    "label": "<%=node.getMindLabel()%>"
                                }
                        }
                    );
                    console.log(data);
        <%
                } else if (node.getGroup().equals("edges")) {
        %>
                    data.edges.push(
                        {
                            data:
                                {
                                    "id": "<%=node.getMindId()%>",
                                    "source": "<%=node.getSource()%>",
                                    "target": "<%=node.getTarget()%>"
                                }
                        }
                    );
                    console.log(data);
        <%
                }
            }
        %>

    //노드 그리기
    window.addEventListener('DOMContentLoaded', function(){ // on dom ready

        // photos from flickr with creative commons license


        // let data = {
        //     nodes: [],
        //     edges: []
        // }


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

        console.log("data");
        const cy = cytoscape({

            container: document.getElementById('cy'),
            elements: data,
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

            fnOpenModal(targetId);

            // cy.add([
            //     {
            //         group: 'nodes',
            //         data: {
            //             "id": currentNodeId,
            //             "url":  "a",
            //             "label": 'abc'
            //         }
            //     },
            //     {
            //         group: 'edges',
            //         data: {
            //             id: currentEdgeId,
            //             source: currentNodeId,
            //             target: targetId
            //         }
            //     }
            // ]);
            //
            // cy.on('cxttap', function (e) {
            //     console.log('cxttap');
            //     // cy.remove(cy.$('#'+currentNodeId));
            //     cy.remove(cy.$('#'+e.target.data('id')));
            // });

            // cy.on('tap', function (e) {
            //   const url = e.target.data('url')
            //   if (url && url !== '') {
            //     window.open(url);
            //   }
            // });

            const layout = cy.makeLayout(layoutConfig);
            layout.run();

            layout.on("layoutstop", () => {
                cy.nodes().forEach(node => {
                    node.unlock();
                })
            })


        });



        <!--노드 생성 백업 -->
        // cy.on('tap', 'node', evt => {
        //
        //     cy.nodes().forEach(node => {
        //         node.lock();
        //     });
        //
        //     const currentNodeId = create_UUID();
        //     const currentEdgeId = create_UUID();
        //     console.log(currentNodeId);
        //     const targetId = evt.target.data('id'); //cy.nodes()[Math.floor(Math.random() * cy.nodes().length)].data('id')
        //
        //     // data.nodes.push({
        //     //     data: {
        //     //         "id": currentNodeId,
        //     //         "url":  "a",
        //     //         "label": 'abc'
        //     //     }
        //     // })
        //     //
        //     // data.edges.push({
        //     //     data: {
        //     //         "id": currentEdgeId,
        //     //         "source": currentNodeId,
        //     //         "target": targetId
        //     //     }
        //     // })
        //
        //     cy.add([
        //         {
        //             group: 'nodes',
        //             data: {
        //                 "id": currentNodeId,
        //                 "url":  "a",
        //                 "label": 'abc'
        //             }
        //         },
        //         {
        //             group: 'edges',
        //             data: {
        //                 id: currentEdgeId,
        //                 source: currentNodeId,
        //                 target: targetId
        //             }
        //         }
        //     ]);
        //
        //     cy.on('cxttap', function (e) {
        //         console.log('cxttap');
        //         // cy.remove(cy.$('#'+currentNodeId));
        //         cy.remove(cy.$('#'+e.target.data('id')));
        //     });
        //
        //     // cy.on('tap', function (e) {
        //     //   const url = e.target.data('url')
        //     //   if (url && url !== '') {
        //     //     window.open(url);
        //     //   }
        //     // });
        //
        //     const layout = cy.makeLayout(layoutConfig);
        //     layout.run();
        //
        //     layout.on("layoutstop", () => {
        //         cy.nodes().forEach(node => {
        //             node.unlock();
        //         })
        //     })
        //
        //
        // });
        <!--노드 생성 백업 -->



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

    }); // on dom ready
</script>
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
</body>
</html>