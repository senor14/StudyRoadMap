<%@ include file="roadMap.jsp" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>
<meta http-equiv="x-ua-compatible" content="ie=edge">
<script src="${pageContext.request.contextPath}/resources/js/study_roadMap/go-module.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/study_roadMap/go.js"></script>
<script id="code">
    var MINLENGTH = 200;  // this controls the minimum length of any swimlane
    var MINBREADTH = 20;  // this controls the minimum breadth of any non-collapsed swimlane

    // some shared functions

    // this may be called to force the lanes to be laid out again
    function relayoutLanes() {
        myDiagram.nodes.each(function(lane) {
            if (!(lane instanceof go.Group)) return;
            if (lane.category === "Pool") return;
            lane.layout.isValidLayout = false;  // force it to be invalid
        });
        myDiagram.layoutDiagram();
    }

    // this is called after nodes have been moved or lanes resized, to layout all of the Pool Groups again
    function relayoutDiagram() {
        myDiagram.layout.invalidateLayout();
        myDiagram.findTopLevelGroups().each(function(g) { if (g.category === "Pool") g.layout.invalidateLayout(); });
        myDiagram.layoutDiagram();
    }

    // compute the minimum size of a Pool Group needed to hold all of the Lane Groups
    function computeMinPoolSize(pool) {
        // assert(pool instanceof go.Group && pool.category === "Pool");
        var len = MINLENGTH;
        pool.memberParts.each(function(lane) {
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
        if (hdr !== null) sz.width = Math.ceil(Math.max(sz.width, hdr.actualBounds.width));
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
        go.Diagram.inherit(LaneResizingTool, go.ResizingTool);
    }
    LaneResizingTool.prototype.isLengthening = function() {
        return (this.handle.alignment === go.Spot.Bottom);
    };

    LaneResizingTool.prototype.computeMinPoolSize = function() {
        var lane = this.adornedObject.part;
        // assert(lane instanceof go.Group && lane.category !== "Pool");
        var msz = computeMinLaneSize(lane);  // get the absolute minimum size
        if (this.isLengthening()) {  // compute the minimum length of all lanes
            var sz = computeMinPoolSize(lane.containingGroup);
            msz.height = Math.max(msz.height, sz.height);
        } else {  // find the minimum size of this single lane
            var sz = computeLaneSize(lane);
            msz.width = Math.max(msz.width, sz.width);
            msz.height = Math.max(msz.height, sz.height);
        }
        return msz;
    };

    LaneResizingTool.prototype.resize = function(newr) {
        var lane = this.adornedObject.part;
        if (this.isLengthening()) {  // changing the length of all of the lanes
            lane.containingGroup.memberParts.each(function(lane) {
                if (!(lane instanceof go.Group)) return;
                var shape = lane.resizeObject;
                if (shape !== null) {  // set its desiredSize length, but leave each breadth alone
                    shape.height = newr.height;
                }
            });
        } else {  // changing the breadth of a single lane
            go.ResizingTool.prototype.resize.call(this, newr);
        }
        relayoutDiagram();  // now that the lane has changed size, layout the pool again
    };
    // end LaneResizingTool class


    // define a custom grid layout that makes sure the length of each lane is the same
    // and that each lane is broad enough to hold its subgraph
    function PoolLayout() {
        go.GridLayout.call(this);
        this.cellSize = new go.Size(1, 1);
        this.wrappingColumn = Infinity;
        this.wrappingWidth = Infinity;
        this.isRealtime = false;  // don't continuously layout while dragging
        this.alignment = go.GridLayout.Position;
        // This sorts based on the location of each Group.
        // This is useful when Groups can be moved up and down in order to change their order.
        this.comparer = function(a, b) {
            var ax = a.location.x;
            var bx = b.location.x;
            if (isNaN(ax) || isNaN(bx)) return 0;
            if (ax < bx) return -1;
            if (ax > bx) return 1;
            return 0;
        };
        this.boundsComputation = function(part, layout, rect) {
            part.getDocumentBounds(rect);
            rect.inflate(-1, -1);  // negative strokeWidth of the border Shape
            return rect;
        }
    }
    go.Diagram.inherit(PoolLayout, go.GridLayout);

    PoolLayout.prototype.doLayout = function(coll) {
        var diagram = this.diagram;
        if (diagram === null) return;
        diagram.startTransaction("PoolLayout");
        var pool = this.group;
        if (pool !== null && pool.category === "Pool") {
            // make sure all of the Group Shapes are big enough
            var minsize = computeMinPoolSize(pool);
            pool.memberParts.each(function(lane) {
                if (!(lane instanceof go.Group)) return;
                if (lane.category !== "Pool") {
                    var shape = lane.resizeObject;
                    if (shape !== null) {  // change the desiredSize to be big enough in both directions
                        var sz = computeLaneSize(lane);
                        shape.width = (!isNaN(shape.width)) ? Math.max(shape.width, sz.width) : sz.width;
                        shape.height = (isNaN(shape.height) ? minsize.height : Math.max(shape.height, minsize.height));
                        var cell = lane.resizeCellSize;
                        if (!isNaN(shape.width) && !isNaN(cell.width) && cell.width > 0) shape.width = Math.ceil(shape.width / cell.width) * cell.width;
                        if (!isNaN(shape.height) && !isNaN(cell.height) && cell.height > 0) shape.height = Math.ceil(shape.height / cell.height) * cell.height;
                    }
                }
            });
        }
        // now do all of the usual stuff, according to whatever properties have been set on this GridLayout
        go.GridLayout.prototype.doLayout.call(this, coll);
        diagram.commitTransaction("PoolLayout");
    };
    // end PoolLayout class


    function init() {
        if(window.goSamples) goSamples();
        var $ = go.GraphObject.make;  // for conciseness in defining templates

        var yellowgrad = $(go.Brush, "Linear", { 0: "rgb(254, 201, 0)", 1: "rgb(254, 162, 0)" });
        var greengrad = $(go.Brush, "Linear", { 0: "#98FB98", 1: "#9ACD32" });
        var bluegrad = $(go.Brush, "Linear", { 0: "#B0E0E6", 1: "#87CEEB" });
        var redgrad = $(go.Brush, "Linear", { 0: "#C45245", 1: "#871E1B" });
        var whitegrad = $(go.Brush, "Linear", { 0: "#F0F8FF", 1: "#E6E6FA" });

        var bigfont = "bold 13pt Helvetica, Arial, sans-serif";
        var smallfont = "bold 11pt Helvetica, Arial, sans-serif";

        // Common text styling
        function textStyle() {
            return {
                margin: 6,
                wrap: go.TextBlock.WrapFit,
                textAlign: "center",
                editable: true,
                font: bigfont
            }
        }

        myDiagram =
            $(go.Diagram, "myDiagramDiv",
                {
                    // use a custom ResizingTool (along with a custom ResizeAdornment on each Group)
                    resizingTool: new LaneResizingTool(),
                    // use a simple layout that ignores links to stack the top-level Pool Groups next to each other
                    layout: $(PoolLayout),
                    // don't allow dropping onto the diagram's background unless they are all Groups (lanes or pools)
                    mouseDragOver: function(e) {
                        if (!e.diagram.selection.all(function(n) { return n instanceof go.Group; })) {
                            e.diagram.currentCursor = 'not-allowed';
                        }
                    },
                    mouseDrop: function(e) {
                        if (!e.diagram.selection.all(function(n) { return n instanceof go.Group; })) {
                            e.diagram.currentTool.doCancel();
                        }
                    },
                    // a clipboard copied node is pasted into the original node's group (i.e. lane).
                    "commandHandler.copiesGroupKey": true,
                    // automatically re-layout the swim lanes after dragging the selection
                    "SelectionMoved": relayoutDiagram,  // this DiagramEvent listener is
                    "SelectionCopied": relayoutDiagram, // defined above
                    "animationManager.isEnabled": false,
                    // enable undo & redo
                    "undoManager.isEnabled": true,
                    // have mouse wheel events zoom in and out instead of scroll up and down
                    "toolManager.mouseWheelBehavior": go.ToolManager.WheelZoom,
                    initialAutoScale: go.Diagram.Uniform,
                    "linkingTool.direction": go.LinkingTool.ForwardsOnly,
                    layout: $(go.LayeredDigraphLayout, { isInitial: false, isOngoing: false, layerSpacing: 50 }),
                    "undoManager.isEnabled": true
                });
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
            var x = Math.max(p1.x + m.left, Math.min(pt.x, p2.x - m.right - b.width - 1)) + (loc.x - b.x);
            var y = Math.max(p1.y + m.top, Math.min(pt.y, p2.y - m.bottom - b.height - 1)) + (loc.y - b.y);
            return new go.Point(x, y);
        }
        // when the document is modified, add a "*" to the title and enable the "Save" button
        myDiagram.addDiagramListener("Modified", function(e) {
            var button = document.getElementById("SaveButton");
            if (button) button.disabled = !myDiagram.isModified;
            var idx = document.title.indexOf("*");
            if (myDiagram.isModified) {
                if (idx < 0) document.title += "*";
            } else {
                if (idx >= 0) document.title = document.title.substr(0, idx);
            }
        });

        var defaultAdornment =
            $(go.Adornment, "Spot",
                $(go.Panel, "Auto",
                    $(go.Shape, { fill: null, stroke: "dodgerblue", strokeWidth: 4 }),
                    $(go.Placeholder)),
                // the button to create a "next" node, at the top-right corner
                $("Button",
                    {
                        alignment: go.Spot.TopRight,
                        click: addNodeAndLink
                    },  // this function is defined below
                    new go.Binding("visible", "", function(a) { return !a.diagram.isReadOnly; }).ofObject(),
                    $(go.Shape, "PlusLine", { desiredSize: new go.Size(6, 6) })
                )
            );
        myDiagram.nodeTemplate =
            $(go.Node, "Auto",
                new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
                $(go.Shape, "Rectangle",
                    { fill: "white", portId: "", cursor: "pointer", fromLinkable: true, toLinkable: true }),
                $(go.TextBlock, { margin: 5 },
                    new go.Binding("text", "key")),
                { dragComputation: stayInGroup } // limit dragging of Nodes to stay within the containing Group, defined above
            );
        function groupStyle() {  // common settings for both Lane and Pool Groups
            return [
                {
                    layerName: "Background",  // all pools and lanes are always behind all nodes and links
                    background: "transparent",  // can grab anywhere in bounds
                    movable: true, // allows users to re-order by dragging
                    copyable: false,  // can't copy lanes or pools
                    avoidable: false,  // don't impede AvoidsNodes routed Links
                    minLocation: new go.Point(-Infinity, NaN),  // only allow horizontal movement
                    maxLocation: new go.Point(Infinity, NaN)
                },
                new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify)
            ];
        }

        // hide links between lanes when either lane is collapsed
        function updateCrossLaneLinks(group) {
            group.findExternalLinksConnected().each(function(l) {
                l.visible = (l.fromNode.isVisible() && l.toNode.isVisible());
            });
        }
        myDiagram.groupTemplate =
            $(go.Group, "Vertical", groupStyle(),
                {
                    selectionObjectName: "SHAPE",  // selecting a lane causes the body of the lane to be highlit, not the label
                    resizable: true, resizeObjectName: "SHAPE",  // the custom resizeAdornmentTemplate only permits two kinds of resizing
                    layout: $(go.LayeredDigraphLayout,  // automatically lay out the lane's subgraph
                        {
                            isInitial: false,  // don't even do initial layout
                            isOngoing: false,  // don't invalidate layout when nodes or links are added or removed
                            direction: 90,
                            columnSpacing: 10,
                            layeringOption: go.LayeredDigraphLayout.LayerLongestPathSource
                        }),
                    computesBoundsAfterDrag: true,  // needed to prevent recomputing Group.placeholder bounds too soon
                    computesBoundsIncludingLinks: false,  // to reduce occurrences of links going briefly outside the lane
                    computesBoundsIncludingLocation: true,  // to support empty space at top-left corner of lane
                    handlesDragDropForMembers: true,  // don't need to define handlers on member Nodes and Links
                    mouseDrop: function(e, grp) {  // dropping a copy of some Nodes and Links onto this Group adds them to this Group
                        if (!e.shift) return;  // cannot change groups with an unmodified drag-and-drop
                        // don't allow drag-and-dropping a mix of regular Nodes and Groups
                        if (!e.diagram.selection.any(function(n) { return n instanceof go.Group; })) {
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
                    subGraphExpandedChanged: function(grp) {
                        var shp = grp.resizeObject;
                        if (grp.diagram.undoManager.isUndoingRedoing) return;
                        if (grp.isSubGraphExpanded) {
                            shp.width = grp.data.savedBreadth;
                        } else {
                            if (!isNaN(shp.width)) grp.diagram.model.set(grp.data, "savedBreadth", shp.width);
                            shp.width = NaN;
                        }
                        updateCrossLaneLinks(grp);
                    }
                },
                new go.Binding("isSubGraphExpanded", "expanded").makeTwoWay(),
                // the lane header consisting of a Shape and a TextBlock
                $(go.Panel, "Horizontal",
                    {
                        name: "HEADER",
                        angle: 0,  // maybe rotate the header to read sideways going up
                        alignment: go.Spot.Center
                    },
                    $(go.Panel, "Horizontal",  // this is hidden when the swimlane is collapsed
                        new go.Binding("visible", "isSubGraphExpanded").ofObject(),
                        $(go.Shape, "Diamond",
                            { width: 8, height: 8, fill: "white" },
                            new go.Binding("fill", "color")),
                        $(go.TextBlock,  // the lane label
                            { font: "bold 13pt sans-serif", editable: true, margin: new go.Margin(2, 0, 0, 0) },
                            new go.Binding("text", "key").makeTwoWay())
                    ),
                    $("SubGraphExpanderButton", { margin: 5 })  // but this remains always visible!
                ),  // end Horizontal Panel
                $(go.Panel, "Auto",  // the lane consisting of a background Shape and a Placeholder representing the subgraph
                    $(go.Shape, "Rectangle",  // this is the resized object
                        { name: "SHAPE", fill: "white" },
                        new go.Binding("fill", "color"),
                        new go.Binding("desiredSize", "size", go.Size.parse).makeTwoWay(go.Size.stringify)),
                    $(go.Placeholder,
                        { padding: 12, alignment: go.Spot.TopLeft }),
                    $(go.TextBlock,  // this TextBlock is only seen when the swimlane is collapsed
                        {
                            name: "LABEL",
                            font: "bold 13pt sans-serif", editable: true,
                            angle: 90, alignment: go.Spot.TopLeft, margin: new go.Margin(4, 0, 0, 2)
                        },
                        new go.Binding("visible", "isSubGraphExpanded", function(e) { return !e; }).ofObject(),
                        new go.Binding("text", "text").makeTwoWay())
                )  // end Auto Panel
            );  // end Group

        // define a custom resize adornment that has two resize handles if the group is expanded
        myDiagram.groupTemplate.resizeAdornmentTemplate =
            $(go.Adornment, "Spot",
                $(go.Placeholder),
                $(go.Shape,  // for changing the length of a lane
                    {
                        alignment: go.Spot.Bottom,
                        desiredSize: new go.Size(50, 7),
                        fill: "lightblue", stroke: "dodgerblue",
                        cursor: "row-resize"
                    },
                    new go.Binding("visible", "", function(ad) {
                        if (ad.adornedPart === null) return false;
                        return ad.adornedPart.isSubGraphExpanded;
                    }).ofObject()),
                $(go.Shape,  // for changing the breadth of a lane
                    {
                        alignment: go.Spot.Right,
                        desiredSize: new go.Size(7, 50),
                        fill: "lightblue", stroke: "dodgerblue",
                        cursor: "col-resize"
                    },
                    new go.Binding("visible", "", function(ad) {
                        if (ad.adornedPart === null) return false;
                        return ad.adornedPart.isSubGraphExpanded;
                    }).ofObject())
            );

        myDiagram.groupTemplateMap.add("Pool",
            $(go.Group, "Auto", groupStyle(),
                { // use a simple layout that ignores links to stack the "lane" Groups next to each other
                    layout: $(PoolLayout, { spacing: new go.Size(0, 0) })  // no space between lanes
                },
                $(go.Shape,
                    { fill: "white" },
                    new go.Binding("fill", "color")),
                $(go.Panel, "Table",
                    { defaultRowSeparatorStroke: "black" },
                    $(go.Panel, "Horizontal",
                        { row: 0, angle: 0 },
                        $(go.TextBlock,
                            { font: "bold 16pt sans-serif", editable: true, margin: new go.Margin(2, 0, 0, 0) },
                            new go.Binding("text").makeTwoWay())
                    ),
                    $(go.Placeholder,
                        { row: 1 })
                )
            ));

        myDiagram.nodeTemplate =
            $(go.Node, "Auto",
                { selectionAdornmentTemplate: defaultAdornment },
                new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
                // define the node's outer shape, which will surround the TextBlock
                $(go.Shape, "Rectangle",
                    {
                        fill: yellowgrad, stroke: "black",
                        portId: "Theory", fromLinkable: true, toLinkable: true, cursor: "pointer",
                        toEndSegmentLength: 50, fromEndSegmentLength: 40
                    }),
                $(go.TextBlock, "Theory",{margin: 6,font: bigfont,editable: true},
                    new go.Binding("text", "text").makeTwoWay()),
            );
        myDiagram.nodeTemplateMap.add("Source",
            $(go.Node, "Auto",
                new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
                $(go.Shape, "RoundedRectangle",
                    {
                        fill: bluegrad,
                        portId: "Source", fromLinkable: true, cursor: "pointer",  toLinkable: true,fromEndSegmentLength: 40
                    }),
                $(go.TextBlock, "BackEnd", textStyle(),
                    new go.Binding("text", "text").makeTwoWay())
            ));

        myDiagram.nodeTemplateMap.add("DesiredEvent",
            $(go.Node, "Auto",
                new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
                $(go.Shape, "RoundedRectangle",
                    {
                        fill: greengrad,
                        portId: "DesiredEvent", fromLinkable: true,  toLinkable: true,cursor: "pointer", fromEndSegmentLength: 40
                    }),
                $(go.TextBlock, "FrontEnd", textStyle(),
                    new go.Binding("text", "text").makeTwoWay())
            ));

        // Undesired events have a special adornment that allows adding additional "reasons"
        var UndesiredEventAdornment =
            $(go.Adornment, "Spot",
                $(go.Panel, "Auto",
                    $(go.Shape, { fill: null, stroke: "dodgerblue", strokeWidth: 4 }),
                    $(go.Placeholder)),
                // the button to create a "next" node, at the top-right corner
                $("Button",
                    {
                        alignment: go.Spot.BottomRight,
                        click: addReason
                    },  // this function is defined below
                    new go.Binding("visible", "", function(a) { return !a.diagram.isReadOnly; }).ofObject(),
                    $(go.Shape, "TriangleDown", { desiredSize: new go.Size(10, 10) })
                )
            );

        var reasonTemplate = $(go.Panel, "Horizontal",
            $(go.TextBlock, "",
                {
                    margin: new go.Margin(4, 0, 0, 0),
                    maxSize: new go.Size(200, NaN),
                    wrap: go.TextBlock.WrapFit,
                    stroke: "whitesmoke",
                    editable: true,
                    font: smallfont
                },
                new go.Binding("text", "text").makeTwoWay())
        );


        // myDiagram.nodeTemplateMap.add("UndesiredEvent",
        //   $(go.Node, "Auto",
        //     new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
        //     { selectionAdornmentTemplate: UndesiredEventAdornment },
        //     $(go.Shape, "RoundedRectangle",
        //       { fill: redgrad, portId: "", toLinkable: true, toEndSegmentLength: 50 }),
        //     $(go.Panel, "Vertical", { defaultAlignment: go.Spot.TopLeft },

        //       $(go.TextBlock, "FrontEnd", textStyle(),
        //         {
        //           stroke: "whitesmoke",
        //           minSize: new go.Size(80, NaN)
        //         },
        //         new go.Binding("text", "text").makeTwoWay()),

        //       $(go.Panel, "Vertical",
        //         {
        //           defaultAlignment: go.Spot.TopLeft,
        //           itemTemplate: reasonTemplate
        //         },
        //         new go.Binding("itemArray", "reasonsList").makeTwoWay()
        //       )
        //     )
        //   ));

        myDiagram.nodeTemplateMap.add("Comment",
            $(go.Node, "Auto",
                new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
                $(go.Shape, "Rectangle",
                    { portId: "", fill: whitegrad, fromLinkable: true }),
                $(go.TextBlock, "A comment",
                    {
                        margin: 9,
                        maxSize: new go.Size(200, NaN),
                        wrap: go.TextBlock.WrapFit,
                        editable: true,
                        font: smallfont
                    },
                    new go.Binding("text", "text").makeTwoWay())
                // no ports, because no links are allowed to connect with a comment
            ));

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

        // clicking the button of a default node inserts a new node to the right of the selected node,
        // and adds a link to that new node
        function addNodeAndLink(e, obj) {
            var adorn = obj.part;
            if (adorn === null) return;
            e.handled = true;
            var diagram = adorn.diagram;
            diagram.startTransaction("Add State");
            // get the node data for which the user clicked the button
            var fromNode = adorn.adornedPart;
            var fromData = fromNode.data;
            // create a new "State" data object, positioned off to the right of the adorned Node
            var toData = { text: "new" };
            var p = fromNode.location;
            toData.loc = p.x + 200 + " " + p.y;  // the "loc" property is a string, not a Point object
            // add the new node data to the model
            var model = diagram.model;
            model.addNodeData(toData);
            // create a link data from the old node data to the new node data
            var linkdata = {};
            linkdata[model.linkFromKeyProperty] = model.getKeyForNodeData(fromData);
            linkdata[model.linkToKeyProperty] = model.getKeyForNodeData(toData);
            // and add the link data to the model
            model.addLinkData(linkdata);
            // select the new Node
            var newnode = diagram.findNodeForData(toData);
            diagram.select(newnode);
            diagram.commitTransaction("Add State");
        }

        // replace the default Link template in the linkTemplateMap
        myDiagram.linkTemplate =
            $(go.Link,  // the whole link panel
                new go.Binding("points").makeTwoWay(),
                { curve: go.Link.Bezier, toShortLength: 15 },
                new go.Binding("curviness", "curviness"),
                $(go.Shape,  // the link shape
                    { stroke: "#2F4F4F", strokeWidth: 2.5 }),
                $(go.Shape,  // the arrowhead
                    { toArrow: "kite", fill: "#2F4F4F", stroke: null, scale: 2 })
            );
        myDiagram.model = new go.GraphLinksModel(
            [ // node data
                { key: "Pool1", text: "Course", isGroup: true, category: "Pool" },
                { key: "Lane1", text: "1학기", isGroup: true, group: "Pool1", color: "lightblue", width: "300px"},
                { key: "Lane2", text: "2학기", isGroup: true, group: "Pool1", color: "lightgreen" },
                { key: "Lane3", text: "3학기", isGroup: true, group: "Pool1", color: "lightyellow" },
                { key: "Lane4", text: "4학기", isGroup: true, group: "Pool1", color: "orange" },
                { key: "Lane5", text: "5학기", isGroup: true, group: "Pool1", color: "lightpink" },
                // { key: "oneA", group: "Lane1" },
                // { key: "oneB", group: "Lane1" },
                // { key: "oneC", group: "Lane1" },
                // { key: "oneD", group: "Lane1" },
                // { key: "twoA", group: "Lane2" },
                // { key: "twoB", group: "Lane2" },
                // { key: "twoC", group: "Lane2" },
                // { key: "twoD", group: "Lane2" },
                // { key: "twoE", group: "Lane2" },
                // { key: "twoF", group: "Lane2" },
                // { key: "twoG", group: "Lane2" },
                // { key: "fourA", group: "Lane4" },
                // { key: "fourB", group: "Lane4" },
                // { key: "fourC", group: "Lane4" },
                // { key: "fourD", group: "Lane4" },
                // { key: "fiveA", group: "Lane5" },
                // { key: "sixA", group: "Lane6" }
            ],
            [ // link data
                // { from: "oneA", to: "oneB" },
                // { from: "oneA", to: "oneC" },
                // { from: "oneB", to: "oneD" },
                // { from: "oneC", to: "oneD" },
                // { from: "twoA", to: "twoB" },
                // { from: "twoA", to: "twoC" },
                // { from: "twoA", to: "twoF" },
                // { from: "twoB", to: "twoD" },
                // { from: "twoC", to: "twoD" },
                // { from: "twoD", to: "twoG" },
                // { from: "twoE", to: "twoG" },
                // { from: "twoF", to: "twoG" },
                // { from: "fourA", to: "fourB" },
                // { from: "fourB", to: "fourC" },
                // { from: "fourC", to: "fourD" }
            ]);
        relayoutLanes();

        myDiagram.linkTemplateMap.add("Comment",
            $(go.Link, { selectable: false },
                $(go.Shape, { strokeWidth: 2, stroke: "darkgreen" })));


        var palette =
            $(go.Palette, "myPaletteDiv",  // create a new Palette in the HTML DIV element
                {
                    // share the template map with the Palette
                    nodeTemplateMap: myDiagram.nodeTemplateMap,
                    autoScale: go.Diagram.Uniform  // everything always fits in viewport
                });

        palette.model.nodeDataArray = [
            { category: "Source" },
            { category: "DesiredEvent" },
            { category: "UndesiredEvent", reasonsList: [{}] },
            { category: "Comment" }
        ];

        // read in the JSON-format data from the "mySavedModel" element
        load();
        layout();
    }

    function layout() {
        myDiagram.layoutDiagram(true);
    }

    // Show the diagram's model in JSON format
    function save() {
        document.getElementById("mySavedModel").value = myDiagram.model.toJson();
        myDiagram.isModified = false;
    }
    function load() {
        myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
        myDiagram.delayInitialization(relayoutDiagram);

    }
    window.addEventListener('DOMContentLoaded', init);
</script>
</head>

<body>
<!-- This top nav is not part of the sample code -->
<nav id="navTop" class="w-full z-30 top-0 text-white bg-nwoods-primary">
    <div class="w-full container max-w-screen-lg mx-auto flex flex-wrap sm:flex-nowrap items-center justify-between mt-0 py-2">
        <div class="md:pl-4">
            <a class="text-white hover:text-white no-underline hover:no-underline
        font-bold text-2xl lg:text-4xl rounded-lg hover:bg-nwoods-secondary "
               href="../">
                <h1 class="mb-0 p-1 " size="50">HOME</h1>
            </a>
        </div>
        <button id="topnavButton"
                class="rounded-lg sm:hidden focus:outline-none focus:ring"
                aria-label="Navigation">
            <svg fill="currentColor" viewBox="0 0 20 20" class="w-6 h-6">
                <path id="topnavOpen" fill-rule="evenodd"
                      d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM9 15a1 1 0 011-1h6a1 1 0 110 2h-6a1 1 0 01-1-1z"
                      clip-rule="evenodd"></path>
                <path id="topnavClosed" class="hidden" fill-rule="evenodd"
                      d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                      clip-rule="evenodd"></path>
            </svg>
        </button>
        <div id="topnavList"
             class="hidden sm:block items-center w-auto mt-0 text-white p-0 z-20">
            <ul class="list-reset list-none font-semibold flex justify-end flex-wrap sm:flex-nowrap items-center px-0 pb-0">

            </ul>
        </div>
    </div>
    <hr class="border-b border-gray-600 opacity-50 my-0 py-0"/>
</nav>
<div class="md:flex flex-col md:flex-row md:min-h-screen w-full max-w-screen-xl mx-auto">
    <div id="navSide"
         class="flex flex-col w-full md:w-48 text-gray-700 bg-white flex-shrink-0"></div>
    <!-- * * * * * * * * * * * * * -->
    <div class="timeline-header">
        <h2 class="timeline-header__title">Study_RoadMap</h2>
        <h3 class="timeline-header__subtitle"><%=en_name %>
        </h3>
    </div>
    <div id="sample">
        <div style="width: 100%; display: flex; justify-content: space-between">
            <div id="myPaletteDiv"
                 style="width: 100px; margin-right: 2px; background-color: whitesmoke; border: solid 1px black"></div>
            <div id="myDiagramDiv"
                 style="flex-grow: 1; height: 480px; border: solid 1px black"></div>
        </div>
        <%--      <p>--%>
        <%--        <b>GoJS</b> registers itself as an anonymous Asynchronous Module Definition (AMD) module,--%>
        <%--        so that you have the option of loading "go.js" asynchronously on demand by just calling <code>require</code>.--%>
        <%--      </p>--%>
        <br>
        <button id="SaveButton" onclick="save()">Save</button>
        <button onclick="load()">Load</button>
        <button onclick="layout()">Layout</button>
        <textarea id="mySavedModel" style="width:100%;height:300px"/>
        <br/>
    </div>

</div>
</body>
<%--<script src="${pageContext.request.contextPath}/assets/js/goSamples.js"></script>--%>
<!--  This script is part of the gojs.net website, and is not needed to run the sample -->
</html>
