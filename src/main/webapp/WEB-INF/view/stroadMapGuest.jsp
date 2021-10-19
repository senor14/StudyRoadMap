<%@ page import="java.util.List" %>
<%@ page import="domain.*" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>

<%
    String userId = (String)request.getAttribute("userId");
    StudyRoadData roadMapInfo = (StudyRoadData)request.getAttribute("roadMapInfo");
    List<StudyRoadNodeData> nodeInfo = (List<StudyRoadNodeData>)request.getAttribute("nodeInfo");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no, viewport-fit=cover"
    />
    <meta
            name="description"
            content="A workflow diagram showing navigation between web pages, with an editable list of comments and to-dos."
    />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/study_mindMap/modal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/study_roadMap/study_roadMap.css">

<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">--%>
    <link href="https://fonts.googleapis.com/css?family=Montserrat:600&display=swap" rel="stylesheet">
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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tab.css">

<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />--%>
    <!-- Copyright 1998-2021 by Northwoods Software Corporation. -->
    <title>Page Flow</title>
<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>--%>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script src="https://html2canvas.hertzen.com/dist/html2canvas.min.js"></script>
</head>
<body>

<div style="position: fixed; background-color: black; width: 100%; z-index: 3000">
    <a href="/index"><i class="fas fa-home" style="position: fixed; margin-left: 10px; margin-top:10px; font-size: 30px; color:white"></i></a>
    <a href="javascript:history.back();"><i class="far fa-arrow-alt-circle-left" style="position: fixed; margin-left: 50px; margin-top:10px; font-size: 30px; color:white"></i></a>
    <h1 style="color:white; margin-left: 100px;"><%=roadMapInfo.getRoadTitle()%></h1>
    <h2 style="color:white; margin-left: 100px;"><%=userId%></h2>
</div>
<%--노드 클릭시 정보 모달 (노드) [마인드맵, 수정, 삭제, 취소] {category, text} --%>
<div class="modal-container" id="m2-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <div>
            <h1 class="modal__node__text" id="modal__node__text">제목</h1>
        </div>
        <div>
            카테고리:<input type="text" class="modal__node__category" id="modal__node__category" readonly/>
        </div>
        <button class="modal__btn" onclick="hrefMindMap();">마인드맵</button>
        <button class="modal__btn" onclick="fnCloseModal('#m2-o');" >취소</button>
        <a onclick="fnCloseModal('#m2-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 끝 --%>

<script>
    function hrefMindMap() {
        location.href = '/roadmaps/'+(document.getElementById('modal__roadId').innerText)+'/nodes/'+(document.getElementById('modal__nodeId').innerText);
    }
</script>

<%-- 노드 클릭시 정보 모달 (다이어그램) [수정, 취소] {text} --%>
<div class="modal-container" id="m4-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <div>
            <h1 class="modal__diagram__text" id="modal__diagram__text">제목</h1>
        </div>
        <button class="modal__btn" onclick="fnCloseModal('#m4-o');">확인</button>
        <a onclick="fnCloseModal('#m4-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 3 끝 --%>

<%-- 노드 클릭시 정보 모달 (레인) [수정, 삭제, 취소] {key, text, color} --%>
<div class="modal-container" id="m6-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <div>
            <h1 class="modal__lane__key" id="modal__lane__key">제목</h1>
        </div>
        <div>
            표지제목 : <input type="text" class="modal__lane__text" id="modal__lane__text" readonly />
        </div>
        <div>색:
            <input type="color" class="modal__lane__color" id="modal__lane__color" disabled />
        </div>
        <button class="modal__btn" onclick="fnCloseModal('#m6-o');" >확인</button>
        <a onclick="fnCloseModal('#m6-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 5 끝 --%>

<%-- 노드 클릭시 정보 모달 (카테고리) [수정, 취소] {category(제목), color} --%>
<div class="modal-container" id="m8-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <div>
            <h1 class="modal__category__text" id="modal__category__text">카테고리 제목</h1>
        </div>
        <div>색:
            <input type="color" class="modal__category__color" id="modal__category__color" disabled>
        </div>
        <button class="modal__btn" onclick="fnCloseModal('#m8-o');">확인</button>
        <a onclick="fnCloseModal('#m8-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 7 끝 --%>

<%-- 노드 클릭시 정보 모달 (카테고리) [수정, 취소] {category(제목), color} --%>
<div class="modal-container" id="m14-o" style="--m-background: hsla(0, 0%, 0%, .4);">
    <div class="modal">
        <div>
            <h1 class="modal__category__text" id="modal__duplicate__text" style="color:white;">내 로드맵으로 복제하시겠습니까?</h1>
        </div>
        <button class="modal__btn" onclick="duplicateStudyRoadmap();">확인</button>
        <button class="modal__btn" onclick="fnCloseModal('#m14-o');">취소</button>
        <a onclick="fnCloseModal('#m14-o');" class="link-2"></a>
    </div>
</div>
<%-- modal 7 끝 --%>

<div id="hidden__box" >
    <span hidden id="modal__nodeId" ></span>
    <span hidden id="modal__roadId" ></span>
    <span hidden id="modal__canvasClass" ></span>
    <span hidden id="modal__category" ></span>
    <span hidden id="modal__key" ></span>
    <span hidden id="modal__text" ></span>
    <span hidden id="modal__isGroup" ></span>
    <span hidden id="modal__group" ></span>
    <span hidden id="modal__color" ></span>
    <span hidden id="modal__size" ></span>
    <span hidden id="modal__loc" ></span>
    <span hidden id="modal__from" ></span>
    <span hidden id="modal__to" ></span>
</div>

<div
        class="
        md:flex
        flex-col
        md:flex-row md:min-h-screen
        w-full
        max-w-screen-xl
        mx-auto
      "
>
    <div
            id="navSide"
            class="
          flex flex-col
          w-full
          md:w-48
          text-gray-700
          bg-white
          flex-shrink-0
        "
    ></div>
    <!-- * * * * * * * * * * * * * -->
    <!-- Start of GoJS sample code -->

    <script src="${pageContext.request.contextPath}/resources/js/study_roadMap/go.js"></script>
    <div class="p-4 w-full">
        <script>
            document.getElementById('modal__roadId').innerText = "<%=roadMapInfo.getRoadId()%>";
        </script>
        <script id="code">
            var MINLENGTH = 200; // this controls the minimum length of any swimlane
            var MINBREADTH = 20; // this controls the minimum breadth of any non-collapsed swimlane

            // some shared functions

            // this may be called to force the lanes to be laid out again
            function relayoutLanes() {
                myDiagram.nodes.each(function (lane) {
                    if (!(lane instanceof go.Group)) return;
                    if (lane.category === "Pool") return;
                    lane.layout.isValidLayout = false; // force it to be invalid
                });
                myDiagram.layoutDiagram();
            }

            // this is called after nodes have been moved or lanes resized, to layout all of the Pool Groups again
            function relayoutDiagram() {
                myDiagram.layout.invalidateLayout();
                myDiagram.findTopLevelGroups().each(function (g) {
                    if (g.category === "Pool") g.layout.invalidateLayout();
                });
                myDiagram.layoutDiagram();
            }

            // compute the minimum size of a Pool Group needed to hold all of the Lane Groups
            function computeMinPoolSize(pool) {
                // assert(pool instanceof go.Group && pool.category === "Pool");
                var len = MINLENGTH;
                pool.memberParts.each(function (lane) {
                    // pools ought to only contain lanes, not plain Nodes
                    if (!(lane instanceof go.Group)) return;
                    var holder = lane.placeholder;
                    if (holder !== null) {
                        var sz = holder.actualBounds;
                        len = Math.max(len, sz.height);
                    }
                });
                return new go.Size(NaN, len);
            }

            // compute the minimum size for a particular Lane Group
            function computeLaneSize(lane) {
                // assert(lane instanceof go.Group && lane.category !== "Pool");
                var sz = computeMinLaneSize(lane);
                if (lane.isSubGraphExpanded) {
                    var holder = lane.placeholder;
                    if (holder !== null) {
                        var hsz = holder.actualBounds;
                        sz.width = Math.ceil(Math.max(sz.width, hsz.width));
                    }
                }
                // minimum breadth needs to be big enough to hold the header
                var hdr = lane.findObject("HEADER");
                if (hdr !== null)
                    sz.width = Math.ceil(Math.max(sz.width, hdr.actualBounds.width));
                return sz;
            }

            // determine the minimum size of a Lane Group, even if collapsed
            function computeMinLaneSize(lane) {
                if (!lane.isSubGraphExpanded) return new go.Size(1, MINLENGTH);
                return new go.Size(MINBREADTH, MINLENGTH);
            }

            // define a custom ResizingTool to limit how far one can shrink a lane Group
            function LaneResizingTool() {
                go.ResizingTool.call(this);
            }
            go.Diagram.inherit(LaneResizingTool, go.ResizingTool);

            LaneResizingTool.prototype.isLengthening = function () {
                return this.handle.alignment === go.Spot.Bottom;
            };

            LaneResizingTool.prototype.computeMinPoolSize = function () {
                var lane = this.adornedObject.part;
                // assert(lane instanceof go.Group && lane.category !== "Pool");
                var msz = computeMinLaneSize(lane); // get the absolute minimum size
                if (this.isLengthening()) {
                    // compute the minimum length of all lanes
                    var sz = computeMinPoolSize(lane.containingGroup);
                    msz.height = Math.max(msz.height, sz.height);
                } else {
                    // find the minimum size of this single lane
                    var sz = computeLaneSize(lane);
                    msz.width = Math.max(msz.width, sz.width);
                    msz.height = Math.max(msz.height, sz.height);
                }
                return msz;
            };

            LaneResizingTool.prototype.resize = function (newr) {
                var lane = this.adornedObject.part;
                if (this.isLengthening()) {
                    // changing the length of all of the lanes
                    lane.containingGroup.memberParts.each(function (lane) {
                        if (!(lane instanceof go.Group)) return;
                        var shape = lane.resizeObject;
                        if (shape !== null) {
                            // set its desiredSize length, but leave each breadth alone
                            shape.height = newr.height;
                        }
                    });
                } else {
                    // changing the breadth of a single lane
                    go.ResizingTool.prototype.resize.call(this, newr);
                }
                relayoutDiagram(); // now that the lane has changed size, layout the pool again
            };
            // end LaneResizingTool class

            // define a custom grid layout that makes sure the length of each lane is the same
            // and that each lane is broad enough to hold its subgraph
            function PoolLayout() {
                go.GridLayout.call(this);
                this.cellSize = new go.Size(1, 1);
                this.wrappingColumn = Infinity;
                this.wrappingWidth = Infinity;
                this.isRealtime = false; // don't continuously layout while dragging
                this.alignment = go.GridLayout.Position;
                // This sorts based on the location of each Group.
                // This is useful when Groups can be moved up and down in order to change their order.
                this.comparer = function (a, b) {
                    var ax = a.location.x;
                    var bx = b.location.x;
                    if (isNaN(ax) || isNaN(bx)) return 0;
                    if (ax < bx) return -1;
                    if (ax > bx) return 1;
                    return 0;
                };
                this.boundsComputation = function (part, layout, rect) {
                    part.getDocumentBounds(rect);
                    rect.inflate(-1, -1); // negative strokeWidth of the border Shape
                    return rect;
                };
            }
            go.Diagram.inherit(PoolLayout, go.GridLayout);

            PoolLayout.prototype.doLayout = function (coll) {
                var diagram = this.diagram;
                if (diagram === null) return;
                diagram.startTransaction("PoolLayout");
                var pool = this.group;
                if (pool !== null && pool.category === "Pool") {
                    // make sure all of the Group Shapes are big enough
                    var minsize = computeMinPoolSize(pool);
                    pool.memberParts.each(function (lane) {
                        if (!(lane instanceof go.Group)) return;
                        if (lane.category !== "Pool") {
                            var shape = lane.resizeObject;
                            if (shape !== null) {
                                // change the desiredSize to be big enough in both directions
                                var sz = computeLaneSize(lane);
                                shape.width = !isNaN(shape.width)
                                    ? Math.max(shape.width, sz.width)
                                    : sz.width;
                                shape.height = isNaN(shape.height)
                                    ? minsize.height
                                    : Math.max(shape.height, minsize.height);
                                var cell = lane.resizeCellSize;
                                if (
                                    !isNaN(shape.width) &&
                                    !isNaN(cell.width) &&
                                    cell.width > 0
                                )
                                    shape.width =
                                        Math.ceil(shape.width / cell.width) * cell.width;
                                if (
                                    !isNaN(shape.height) &&
                                    !isNaN(cell.height) &&
                                    cell.height > 0
                                )
                                    shape.height =
                                        Math.ceil(shape.height / cell.height) * cell.height;
                            }
                        }
                    });
                }
                // now do all of the usual stuff, according to whatever properties have been set on this GridLayout
                go.GridLayout.prototype.doLayout.call(this, coll);
                diagram.commitTransaction("PoolLayout");
            };
            // end PoolLayout class

            let palette;
            function init() {
                var $ = go.GraphObject.make; // for conciseness in defining templates

                var yellowgrad = $(go.Brush, "Linear", {
                    0: "rgb(254, 201, 0)",
                    1: "rgb(254, 162, 0)",
                });
                var greengrad = $(go.Brush, "Linear", {
                    0: "#98FB98",
                    1: "#9ACD32",
                });
                var bluegrad = $(go.Brush, "Linear", {
                    0: "#B0E0E6",
                    1: "#87CEEB",
                });
                var redgrad = $(go.Brush, "Linear", { 0: "#C45245", 1: "#871E1B" });
                var whitegrad = $(go.Brush, "Linear", {
                    0: "#F0F8FF",
                    1: "#E6E6FA",
                });

                var bigfont = "bold 13pt Helvetica, Arial, sans-serif";
                var smallfont = "bold 11pt Helvetica, Arial, sans-serif";

                // Common text styling
                function textStyle() {
                    return {
                        margin: 6,
                        wrap: go.TextBlock.WrapFit,
                        textAlign: "center",
                        editable: false,
                        font: bigfont,
                    };
                }

                myDiagram = $(go.Diagram, "myDiagramDiv", {
                    // use a custom ResizingTool (along with a custom ResizeAdornment on each Group)
                    resizingTool: new LaneResizingTool(),
                    // use a simple layout that ignores links to stack the top-level Pool Groups next to each other
                    layout: $(PoolLayout),
                    // don't allow dropping onto the diagram's background unless they are all Groups (lanes or pools)
                    mouseDragOver: function (e) {
                        if (
                            !e.diagram.selection.all(function (n) {
                                return n instanceof go.Group;
                            })
                        ) {
                            e.diagram.currentCursor = "not-allowed";
                        }
                    },
                    mouseDrop: function (e) {
                        if (
                            !e.diagram.selection.all(function (n) {
                                return n instanceof go.Group;
                            })
                        ) {
                            e.diagram.currentTool.doCancel();
                        }
                    },

                    // a clipboard copied node is pasted into the original node's group (i.e. lane).
                    "commandHandler.copiesGroupKey": true,
                    // automatically re-layout the swim lanes after dragging the selection
                    SelectionMoved: relayoutDiagram, // this DiagramEvent listener is
                    SelectionCopied: relayoutDiagram, // defined above
                    "animationManager.isEnabled": false,
                    // enable undo & redo
                    "undoManager.isEnabled": true,
                    // have mouse wheel events zoom in and out instead of scroll up and down
                    "toolManager.mouseWheelBehavior": go.ToolManager.WheelZoom,
                    initialAutoScale: go.Diagram.Uniform,
                    "linkingTool.direction": go.LinkingTool.ForwardsOnly,
                    layout: $(go.LayeredDigraphLayout, {
                        isInitial: false,
                        isOngoing: false,
                        layerSpacing: 500,
                    }),
                    "undoManager.isEnabled": true,
                });

                myDiagram.model.isReadOnly = true;

                function stayInGroup(part, pt, gridpt) {
                    // don't constrain top-level nodes
                    var grp = part.containingGroup;
                    if (grp === null) return pt;
                    // try to stay within the background Shape of the Group
                    var back = grp.resizeObject;
                    if (back === null) return pt;
                    // allow dragging a Node out of a Group if the Shift key is down
                    if (part.diagram.lastInput.shift) return pt;
                    var p1 = back.getDocumentPoint(go.Spot.TopLeft);
                    var p2 = back.getDocumentPoint(go.Spot.BottomRight);
                    var b = part.actualBounds;
                    var loc = part.location;
                    // find the padding inside the group's placeholder that is around the member parts
                    var m = grp.placeholder.padding;
                    // now limit the location appropriately
                    var x =
                        Math.max(
                            p1.x + m.left,
                            Math.min(pt.x, p2.x - m.right - b.width - 1)
                        ) +
                        (loc.x - b.x);
                    var y =
                        Math.max(
                            p1.y + m.top,
                            Math.min(pt.y, p2.y - m.bottom - b.height - 1)
                        ) +
                        (loc.y - b.y);
                    return new go.Point(x, y);
                }
                // // when the document is modified, add a "*" to the title and enable the "Save" button
                // myDiagram.addDiagramListener("Modified", function (e) {
                //     var button = document.getElementById("SaveButton");
                //     if (button) button.disabled = !myDiagram.isModified;
                //     var idx = document.title.indexOf("*");
                //     if (myDiagram.isModified) {
                //         if (idx < 0) document.title += "*";
                //     } else {
                //         if (idx >= 0) document.title = document.title.substr(0, idx);
                //     }
                // });


                // "/roadmaps/{roadId}/nodes/{canvasClass}"
                // 카테고리 드래그앤드롭시
                myDiagram.addDiagramListener("ExternalObjectsDropped", function (e) {
                    clearAddInfo();
                    let droppedOb = e.subject.iterator.tg.af.key.ob;
                    getNodeDataByAjax(droppedOb);
                    myDiagram.model.isReadOnly = true;
                });


                // 리사이징시
                myDiagram.addDiagramListener("PartResized", function (e) {
                  let part = e.subject.part;
                  clearAddInfo();
                  getNodeDataByAjax(part.ob);
                });

                // 드래그해서 객체 이동시
                myDiagram.addDiagramListener("SelectionMoved", function (e) {
                  clearAddInfo();
                  getNodeDataByAjax(e.subject.ga.af.key.ob);

                });

                // 노드, 레인, 다이어그램 클릭시
                myDiagram.addDiagramListener("ObjectSingleClicked", function (e) {
                    // 딜리트, 복사, 언두 제거
                    // relayoutLanes();
                    // relayoutDiagram();
                    myDiagram.model.isReadOnly = true;
                    let part = e.subject.part;
                    console.log("ObjectSingleClicked");
                    console.log(part.ob);
                    clearAddInfo();
                    getNodeDataByAjax(part.ob);
                });

                // 노드, 레인, 다이어그램 더블클릭시
                myDiagram.addDiagramListener("ObjectDoubleClicked", function (e) {
                    // 딜리트, 복사, 언두 제거
                    myDiagram.model.isReadOnly = true;
                    let part = e.subject.part;

                    clearAddInfo();
                    getNodeDataByAjax(part.ob);

                    if (part.ob.canvasClass === 'Node') {
                        fnOpenModal('#m2-o');
                    } else if (part.ob.canvasClass === 'Diagram') {
                        fnOpenModal('#m4-o');
                    } else if (part.ob.canvasClass === 'Lane') {
                        fnOpenModal('#m6-o');
                    }
                });


                var defaultAdornment = $(
                    go.Adornment,
                    "Spot",
                    $(
                        go.Panel,
                        "Auto",
                        $(go.Shape, {
                            fill: null,
                            stroke: "dodgerblue",
                            strokeWidth: 4,
                        }),
                        $(go.Placeholder)
                    ),
                );
                myDiagram.nodeTemplate = $(
                    go.Node,
                    "Auto",
                    new go.Binding("location", "loc", go.Point.parse).makeTwoWay(
                        go.Point.stringify
                    ),
                    $(go.Shape, "Rectangle", {
                        fill: "white",
                        portId: "",
                        cursor: "pointer",
                        fromLinkable: true,
                        toLinkable: true,
                    }),
                    $(go.TextBlock, { margin: 5 }, new go.Binding("text", "key")),
                    { dragComputation: stayInGroup } // limit dragging of Nodes to stay within the containing Group, defined above
                );
                function groupStyle() {
                    // common settings for both Lane and Pool Groups
                    return [
                        {
                            layerName: "Background", // all pools and lanes are always behind all nodes and links
                            background: "transparent", // can grab anywhere in bounds
                            movable: true, // allows users to re-order by dragging
                            copyable: false, // can't copy lanes or pools
                            avoidable: false, // don't impede AvoidsNodes routed Links
                            minLocation: new go.Point(-Infinity, NaN), // only allow horizontal movement
                            maxLocation: new go.Point(Infinity, NaN),
                        },
                        new go.Binding("location", "loc", go.Point.parse).makeTwoWay(
                            go.Point.stringify
                        ),
                    ];
                }

                // hide links between lanes when either lane is collapsed
                function updateCrossLaneLinks(group) {
                    group.findExternalLinksConnected().each(function (l) {
                        l.visible = l.fromNode.isVisible() && l.toNode.isVisible();
                    });
                }
                myDiagram.groupTemplate = $(
                    go.Group,
                    "Vertical",
                    groupStyle(),
                    {
                        selectionObjectName: "SHAPE", // selecting a lane causes the body of the lane to be highlit, not the label
                        resizable: true,
                        resizeObjectName: "SHAPE", // the custom resizeAdornmentTemplate only permits two kinds of resizing
                        layout: $(
                            go.LayeredDigraphLayout, // automatically lay out the lane's subgraph
                            {
                                isInitial: false, // don't even do initial layout
                                isOngoing: false, // don't invalidate layout when nodes or links are added or removed
                                direction: 90,
                                columnSpacing: 10,
                                layeringOption:
                                go.LayeredDigraphLayout.LayerLongestPathSource,
                            }
                        ),
                        computesBoundsAfterDrag: true, // needed to prevent recomputing Group.placeholder bounds too soon
                        computesBoundsIncludingLinks: false, // to reduce occurrences of links going briefly outside the lane
                        computesBoundsIncludingLocation: true, // to support empty space at top-left corner of lane
                        handlesDragDropForMembers: true, // don't need to define handlers on member Nodes and Links
                        mouseDrop: function (e, grp) {
                            // dropping a copy of some Nodes and Links onto this Group adds them to this Group
                            if (!e.shift) return; // cannot change groups with an unmodified drag-and-drop
                            // don't allow drag-and-dropping a mix of regular Nodes and Groups
                            if (
                                !e.diagram.selection.any(function (n) {
                                    return n instanceof go.Group;
                                })
                            ) {
                                var ok = grp.addMembers(grp.diagram.selection, true);
                                if (ok) {
                                    updateCrossLaneLinks(grp);
                                } else {
                                    grp.diagram.currentTool.doCancel();
                                }
                            } else {
                                e.diagram.currentTool.doCancel();
                            }
                        },
                        subGraphExpandedChanged: function (grp) {
                            var shp = grp.resizeObject;
                            if (grp.diagram.undoManager.isUndoingRedoing) return;
                            if (grp.isSubGraphExpanded) {
                                shp.width = grp.data.savedBreadth;
                            } else {
                                if (!isNaN(shp.width))
                                    grp.diagram.model.set(
                                        grp.data,
                                        "savedBreadth",
                                        shp.width
                                    );
                                shp.width = NaN;
                            }
                            updateCrossLaneLinks(grp);
                        },
                    },
                    new go.Binding("isSubGraphExpanded", "expanded").makeTwoWay(),
                    // the lane header consisting of a Shape and a TextBlock
                    $(
                        go.Panel,
                        "Horizontal",
                        {
                            name: "HEADER",
                            angle: 0, // maybe rotate the header to read sideways going up
                            alignment: go.Spot.Center,
                        },
                        $(
                            go.Panel,
                            "Horizontal", // this is hidden when the swimlane is collapsed
                            new go.Binding("visible", "isSubGraphExpanded").ofObject(),
                            $(
                                go.Shape,
                                "Diamond",
                                { width: 8, height: 8, fill: "white" },
                                new go.Binding("fill", "color")
                            ),
                            $(
                                go.TextBlock, // the lane label
                                {
                                    font: "bold 13pt sans-serif",
                                    editable: false,
                                    margin: new go.Margin(2, 0, 0, 0),
                                },
                                new go.Binding("text", "key").makeTwoWay()
                            )
                        ),
                        $("SubGraphExpanderButton", { margin: 5 }) // but this remains always visible!
                    ), // end Horizontal Panel
                    $(
                        go.Panel,
                        "Auto", // the lane consisting of a background Shape and a Placeholder representing the subgraph
                        $(
                            go.Shape,
                            "Rectangle", // this is the resized object
                            { name: "SHAPE", fill: "white" },
                            new go.Binding("fill", "color"),
                            new go.Binding(
                                "desiredSize",
                                "size",
                                go.Size.parse
                            ).makeTwoWay(go.Size.stringify)
                        ),
                        $(go.Placeholder, { padding: 12, alignment: go.Spot.TopLeft }),
                        $(
                            go.TextBlock, // this TextBlock is only seen when the swimlane is collapsed
                            {
                                name: "LABEL",
                                font: "bold 13pt sans-serif",
                                editable: false,
                                angle: 90,
                                alignment: go.Spot.TopLeft,
                                margin: new go.Margin(4, 0, 0, 2),
                            },
                            new go.Binding("visible", "isSubGraphExpanded", function (e) {
                                return !e;
                            }).ofObject(),
                            new go.Binding("text", "text").makeTwoWay()
                        )
                    ) // end Auto Panel
                ); // end Group

                // define a custom resize adornment that has two resize handles if the group is expanded
                myDiagram.groupTemplate.resizeAdornmentTemplate = $(
                    go.Adornment,
                    "Spot",
                    $(go.Placeholder),
                    $(
                        go.Shape, // for changing the length of a lane
                        {
                            alignment: go.Spot.Bottom,
                            desiredSize: new go.Size(50, 7),
                            fill: "lightblue",
                            stroke: "dodgerblue",
                            cursor: "row-resize",
                        },
                        new go.Binding("visible", "", function (ad) {
                            if (ad.adornedPart === null) return false;
                            return ad.adornedPart.isSubGraphExpanded;
                        }).ofObject()
                    ),
                    $(
                        go.Shape, // for changing the breadth of a lane
                        {
                            alignment: go.Spot.Right,
                            desiredSize: new go.Size(7, 50),
                            fill: "lightblue",
                            stroke: "dodgerblue",
                            cursor: "col-resize",
                        },
                        new go.Binding("visible", "", function (ad) {
                            if (ad.adornedPart === null) return false;
                            return ad.adornedPart.isSubGraphExpanded;
                        }).ofObject()
                    )
                );

                myDiagram.groupTemplateMap.add(
                    "Pool",
                    $(
                        go.Group,
                        "Auto",
                        groupStyle(),
                        {
                            // use a simple layout that ignores links to stack the "lane" Groups next to each other
                            layout: $(PoolLayout, { spacing: new go.Size(0, 0) }), // no space between lanes
                        },
                        $(go.Shape, { fill: "white" }, new go.Binding("fill", "color")),
                        $(
                            go.Panel,
                            "Table",
                            { defaultRowSeparatorStroke: "black" },
                            $(
                                go.Panel,
                                "Horizontal",
                                { row: 0, angle: 0 },
                                $(
                                    go.TextBlock,
                                    {
                                        font: "bold 16pt sans-serif",
                                        editable: false,
                                        margin: new go.Margin(2, 0, 0, 0),
                                    },
                                    new go.Binding("text").makeTwoWay()
                                )
                            ),
                            $(go.Placeholder, { row: 1 })
                        )
                    )
                );

                myDiagram.nodeTemplate = $(
                    go.Node,
                    "Auto",
                    { selectionAdornmentTemplate: defaultAdornment },
                    new go.Binding("location", "loc", go.Point.parse).makeTwoWay(
                        go.Point.stringify
                    ),
                    // define the node's outer shape, which will surround the TextBlock
                    $(go.Shape, "Rectangle", {
                        fill: yellowgrad,
                        stroke: "black",
                        portId: "기본",
                        fromLinkable: true,
                        toLinkable: true,
                        cursor: "pointer",
                        toEndSegmentLength: 50,
                        fromEndSegmentLength: 40,
                    }),
                    $(
                        go.TextBlock,
                        "기본",
                        { margin: 6, font: bigfont, editable: false },
                        new go.Binding("text", "text").makeTwoWay()
                    )
                );
                // 카테고리 설정
                <%for (StudyRoadNodeData s: nodeInfo) {%>
                    <%if (s.getCanvasClass().equals("Category")) {%>
                    myDiagram.nodeTemplateMap.add(
                        "<%=s.getText()%>",
                        $(
                            go.Node,
                            "Auto",
                            new go.Binding("location", "loc", go.Point.parse).makeTwoWay(
                                go.Point.stringify
                            ),
                            $(go.Shape, "RoundedRectangle", {
                                fill: "<%=s.getColor()%>",
                                portId: "<%=s.getText()%>",
                                fromLinkable: true,
                                cursor: "pointer",
                                toLinkable: true,
                                fromEndSegmentLength: 40,
                            }),
                            $(
                                go.TextBlock,
                                "<%=s.getText()%>",
                                textStyle(),
                                new go.Binding("text", "text").makeTwoWay()
                            )
                        )
                    );
                    <%}%>
                <%}%>

                // clicking the button on an UndesiredEvent node inserts a new text object into the panel
                function addReason(e, obj) {
                    var adorn = obj.part;
                    if (adorn === null) return;
                    e.handled = true;
                    var arr = adorn.adornedPart.data.reasonsList;
                    myDiagram.startTransaction("add reason");
                    myDiagram.model.addArrayItem(arr, {});
                    myDiagram.commitTransaction("add reason");
                }

                // replace the default Link template in the linkTemplateMap
                myDiagram.linkTemplate = $(
                    go.Link, // the whole link panel
                    new go.Binding("points").makeTwoWay(),
                    { curve: go.Link.Bezier, toShortLength: 15 },
                    new go.Binding("curviness", "curviness"),
                    $(
                        go.Shape, // the link shape
                        { stroke: "#2F4F4F", strokeWidth: 2.5 }
                    ),
                    $(
                        go.Shape, // the arrowhead
                        { toArrow: "kite", fill: "#2F4F4F", stroke: null, scale: 2 }
                    )
                );
                // 다이어그램, 노드, 레인 그리기
                myDiagram.model = new go.GraphLinksModel(
                        // node data (diagram, lane, node)
                    [
                        <%for (StudyRoadNodeData s :nodeInfo){%>
                            <%if (s.getCanvasClass().equals("Diagram")) {%>
                            {
                                nodeId: "<%=s.getNodeId()%>",
                                roadId: "<%=s.getRoadId()%>",
                                canvasClass: "<%=s.getCanvasClass()%>",
                                key: "<%=s.getKey()%>",
                                text: "<%=s.getText()%>",
                                isGroup: "<%=s.getIsGroup()%>",
                                category: "<%=s.getCategory()%>",
                                <%if (s.getLoc()!=null && s.getLoc()!="") {%>
                                loc: "<%=s.getLoc()%>"
                                <%}%>
                            },
                            <%}%>
                            <%if (s.getCanvasClass().equals("Lane")) {%>
                            {
                                nodeId: "<%=s.getNodeId()%>",
                                roadId: "<%=s.getRoadId()%>",
                                canvasClass: "<%=s.getCanvasClass()%>",
                                key: "<%=s.getKey()%>",
                                text: "<%=s.getText()%>",
                                isGroup: "<%=s.getIsGroup()%>",
                                group: "<%=s.getGroup()%>",
                                color: "<%=s.getColor()%>",
                                <%if (s.getSize()!=null && s.getSize()!="") {%>
                                size: "<%=s.getSize()%>",
                                <%}%>
                                <%if (s.getLoc()!=null && s.getLoc()!="") {%>
                                loc: "<%=s.getLoc()%>",
                                <%}%>
                            },
                            <%}%>
                            <%if (s.getCanvasClass().equals("Node")) {%>
                            {
                                nodeId: "<%=s.getNodeId()%>",
                                roadId: "<%=s.getRoadId()%>",
                                canvasClass: "<%=s.getCanvasClass()%>",
                                key: "<%=s.getKey()%>",
                                text: "<%=s.getText()%>",
                                category: "<%=s.getCategory()%>",
                                loc: "<%=s.getLoc()%>"
                            },
                            <%}%>
                        <%}%>
                    ],
                    [
                    ]
                );

                relayoutLanes();

                myDiagram.linkTemplateMap.add(
                    "Comment",
                    $(
                        go.Link,
                        { selectable: false },
                        $(go.Shape, { strokeWidth: 2, stroke: "darkgreen" })
                    )
                );

                myDiagram.model.isReadOnly = true;

                palette = $(
                    go.Palette,
                    "myPaletteDiv", // create a new Palette in the HTML DIV element
                    {
                        // share the template map with the Palette
                        nodeTemplateMap: myDiagram.nodeTemplateMap,
                        autoScale: go.Diagram.Uniform, // everything always fits in viewport
                    }
                );

                // 카테고리 노드 클릭시

                palette.addDiagramListener("ObjectSingleClicked", function (e) {
                    let part = e.subject.part;
                    clearAddInfo();
                    getNodeDataByAjax(part.ob);
                });


                // 카테고리 노드 더블클릭시
                ///*
                palette.addDiagramListener("ObjectDoubleClicked", function (e) {
                    let part = e.subject.part;
                    clearAddInfo();
                    getNodeDataByAjax(part.ob);
                    fnOpenModal('#m8-o');

                });
                //*/

                // 팔레트에서 드래그해서 객체 이동시 다이어그램 읽기전용 해제
                palette.addDiagramListener("SelectionMoved", function (e) {
                    console.log('SelectionMoved');
                    console.log(e.subject.ga.af.key.ob);

                });

                // 팔레트에 포커스를 얻었을 때 다이어그램 읽기전용 해제
                palette.addDiagramListener("GainedFocus", function (e) {
                    console.log('GainedFocus');
                    myDiagram.model.isReadOnly = false;
                })

                // 카테고리 그리기
                palette.model.nodeDataArray = [
                    <%for (StudyRoadNodeData s : nodeInfo) {%>
                        <%if (s.getCanvasClass().equals("Category")) {%>
                            {
                                nodeId: "<%=s.getNodeId()%>",
                                roadId: "<%=s.getRoadId()%>",
                                canvasClass: "<%=s.getCanvasClass()%>",
                                category: "<%=s.getText()%>",
                                text: "<%=s.getText()%>",
                                key: "<%=s.getNodeId()%>",
                                color: "<%=s.getColor()%>",
                            },
                        <%}%>
                    <%}%>
                ];

                // read in the JSON-format data from the "mySavedModel" element
                load();
                layout();

            }

            myDiagram = $(go.Diagram, "myDiagramDiv", {
                // use a custom ResizingTool (along with a custom ResizeAdornment on each Group)
                resizingTool: new LaneResizingTool(),
                // use a simple layout that ignores links to stack the top-level Pool Groups next to each other
                layout: $(PoolLayout),
                // don't allow dropping onto the diagram's background unless they are all Groups (lanes or pools)
                mouseDragOver: function (e) {
                    if (
                        !e.diagram.selection.all(function (n) {
                            return n instanceof go.Group;
                        })
                    ) {
                        e.diagram.currentCursor = "not-allowed";
                    }
                },
                mouseDrop: function (e) {
                    if (
                        !e.diagram.selection.all(function (n) {
                            return n instanceof go.Group;
                        })
                    ) {
                        e.diagram.currentTool.doCancel();
                    }
                },

                // a clipboard copied node is pasted into the original node's group (i.e. lane).
                "commandHandler.copiesGroupKey": false,
                // automatically re-layout the swim lanes after dragging the selection
                SelectionMoved: relayoutDiagram, // this DiagramEvent listener is
                SelectionCopied: relayoutDiagram, // defined above
                "animationManager.isEnabled": false,
                // enable undo & redo
                "undoManager.isEnabled": false,
                // have mouse wheel events zoom in and out instead of scroll up and down
                "toolManager.mouseWheelBehavior": go.ToolManager.WheelZoom,
                initialAutoScale: go.Diagram.Uniform,
                "linkingTool.direction": go.LinkingTool.ForwardsOnly,
                layout: $(go.LayeredDigraphLayout, {
                    isInitial: false,
                    isOngoing: false,
                    layerSpacing: 500,
                }),
                // "undoManager.isEnabled": true,
            });

            palette = $(
                go.Palette,
                "myPaletteDiv", // create a new Palette in the HTML DIV element
                {
                    // share the template map with the Palette
                    nodeTemplateMap: myDiagram.nodeTemplateMap,
                    autoScale: go.Diagram.Uniform, // everything always fits in viewport
                }
            );

            function savePalette() {
                document.getElementById("mySavedModel").value =
                    palette.model.toJson();
                console.log('save');
                console.log(palette.model.nodeDataArray);
                palette.isModified = false;
            }
            function loadPalette() {
                console.log("로드")
                palette.model = go.Model.fromJson(
                    document.getElementById("mySavedModel").value
                );
                console.log('로드2')
                palette.delayInitialization(relayoutDiagram);
            }

            function layout() {
                myDiagram.layoutDiagram(true);
            }

            // Show the diagram's model in JSON format
            function save() {
                document.getElementById("mySavedModel").value =
                    myDiagram.model.toJson();
                myDiagram.isModified = false;
            }
            function load() {
                myDiagram.model = go.Model.fromJson(
                    document.getElementById("mySavedModel").value
                );
                myDiagram.delayInitialization(relayoutDiagram);
            }
            window.addEventListener("DOMContentLoaded", init);

            // 노드 정보 히든에 숨기기
            function getNodeDataByAjax(target) {
                if (target.nodeId) document.getElementById("modal__nodeId").innerText = target.nodeId;
                if (target.roadId) document.getElementById("modal__roadId").innerText = target.roadId;
                if (target.canvasClass) document.getElementById("modal__canvasClass").innerText = target.canvasClass;
                if (target.category) document.getElementById("modal__category").innerText = target.category;
                if (target.key) document.getElementById("modal__key").innerText = target.key;
                if (target.text) document.getElementById("modal__text").innerText = target.text;
                if (target.isGroup) document.getElementById("modal__isGroup").innerText = target.isGroup;
                if (target.group) document.getElementById("modal__group").innerText = target.group;
                if (target.color) document.getElementById("modal__color").innerText = target.color;
                if (target.size) document.getElementById("modal__size").innerText = target.size;
                if (target.loc) document.getElementById("modal__loc").innerText = target.loc;
                if (target.from) document.getElementById("modal__from").innerText = target.from;
                if (target.to) document.getElementById("modal__to").innerText = target.to;

                if (target.canvasClass === 'Node') {
                    $(".modal__node__text").text(target.text);
                    $(".modal__node__text").val(target.text);
                    $(".modal__node__category").val(target.category);
                } else if (target.canvasClass === 'Diagram') {
                    $(".modal__diagram__text").text(target.text);
                    $(".modal__diagram__text").val(target.text);
                } else if (target.canvasClass === 'Lane') {
                    $(".modal__lane__text").text(target.text);
                    $(".modal__lane__text").val(target.text);
                    $(".modal__lane__key").val(target.key);
                    $(".modal__lane__color").val(target.color);
                } else if (target.canvasClass === 'Category') {
                    $(".modal__category__text").text(target.category);
                    $(".modal__category__text").val(target.category);
                    $(".modal__category__color").val(target.color);
                }
            }

            // 히든 내용 지우기
            function clearAddInfo() {
                document.getElementById("modal__nodeId").innerText = "";
                // document.getElementById("modal__roadId").innerText = "";
                document.getElementById("modal__canvasClass").innerText = "";
                document.getElementById("modal__category").innerText = "";
                document.getElementById("modal__key").innerText = "";
                document.getElementById("modal__text").innerText = "";
                document.getElementById("modal__isGroup").innerText = "";
                document.getElementById("modal__group").innerText = "";
                document.getElementById("modal__color").innerText = "";
                document.getElementById("modal__size").innerText = "";
                document.getElementById("modal__loc").innerText = "";
                document.getElementById("modal__from").innerText = "";
            }

            // 모달 오픈
            function fnOpenModal(id){
                // $('#m2-o').css("display", "flex");
                $(id).css("display", "flex");
            }
            // 모달 종료
            function fnCloseModal(id){
                // $('#m2-o').css("display", "none");
                $(id).css("display", "none");
                clearAddInfo();
            }

            function duplicateStudyRoadmap() {
                $.ajax({
                    url: "/roadmaps/"+"<%=roadMapInfo.getRoadId()%>"+"/duplicate",
                    type: "post",
                    success: function (data) {
                        if (data===1) {
                            alert("로그인을 해주세요.");
                        } else if (data===0) {
                            alert("복제완료되었습니다.");
                            location.href="/index";
                        } else {
                            console.log("data 이상!");
                        }
                    }
                })
            }

        </script>

        <div id="sample">
            <div
                    style="width: 100%; display: flex; justify-content: space-between"
            >
                <div
                        id="myPaletteDiv"
                        style="
                width: 100px;
                margin-right: 2px;
                margin-top: 50px;
                background-color: whitesmoke;
                border: solid 1px black;
              "
                ></div>
                <div
                        id="myDiagramDiv"
                        style="margin-top: 50px; flex-grow: 1; height: 480px; border: solid 1px black"
                ></div>
            </div>
            <br />
            <div class="button__box" style="display: flex; justify-content: center; align-items: center;">
                <div class="road__button" id="road__button__copy"><span onclick="fnOpenModal('#m14-o')"></span></div>
            </div>
            <iframe src="/comment/<%=roadMapInfo.getRoadId()%>" style="width: 100%; height:600px; border: 0px; "/>
            <div hidden>
                <textarea id="mySavedModel" style="width: 100%; height: 300px" />
            </div>
            <br />
        </div>
    </div>
    <!-- * * * * * * * * * * * * * -->
    <!--  End of GoJS sample code  -->
</div>

</body>
<!--  This script is part of the gojs.net website, and is not needed to run the sample -->
<script src="${pageContext.request.contextPath}/resources/js/study_roadMap/goSamples.js"></script>

</html>
