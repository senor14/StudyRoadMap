// cytoscape.use( cola );

window.addEventListener('DOMContentLoaded', function(){ // on dom ready

  // photos from flickr with creative commons license
    
  let data = {
    nodes: [
      { "id": "PJ-mindMap", "label":"Project Driven Study Map"  },
      { "id": "ISSUE-packageJson", "label":"package.json 에러"  },
      { "id": "STUDY-npmInit", "label":"npm 패키지 설치 순서 숙지"  }
    ],
    edges: [
      { "id":"PJ-mindMap->ISSUE-packageJson", "source": "ISSUE-packageJson", "target": "PJ-mindMap" },
      { "id":"ISSUE-packageJson->STUDY-npmInit", "source": "STUDY-npmInit", "target": "ISSUE-packageJson" }
    ]
  }

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

    let nodeid = 1;
    cy.on('tap', 'node', evt => {
  
      cy.nodes().forEach(node => {
          node.lock();
      });
      
      const currentNodeId = create_UUID();
      const currentEdgeId = create_UUID();
      console.log(currentNodeId);
      const targetId = evt.target.data('id'); //cy.nodes()[Math.floor(Math.random() * cy.nodes().length)].data('id')
      
      data.nodes.push({
        data: {
          "id": currentNodeId,
          "url":  "a",
          "label": 'abc'
        }
      })

      data.edges.push({
        data: {
          "id": currentEdgeId,
          "source": currentNodeId,
          "target": targetId
        }
      })

      cy.add([
        {
          group: 'nodes',
          data: {
            "id": currentNodeId,
            "url":  "a",
            "label": 'abc'
          }
        }, 
        {
          group: 'edges',
          data: {
            id: currentEdgeId,
            source: currentNodeId,
            target: targetId
          }
        }
      ]);

      cy.on('cxttap', function (e) {
        console.log('cxttap');
        // cy.remove(cy.$('#'+currentNodeId));
        cy.remove(cy.$('#'+e.target.data('id')));
      });

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