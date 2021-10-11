(function ( $, window, document, undefined ){

  $.fn.btnInteraction = function( options ) {

    // Setup default options
    var pluginName = 'btnInteraction',
        defaults = {
          'saveIcon' : 'fa-cog',
          'verbing' : 'Saving...',
          'verbed' : 'Saved',
          'delay' : 1000,
          'duration' : 1000
        };

    return this.each(function() {
      
      // Store the object
      var self = this,
          $this = $(this),
          ogHtml = $this.html();
      
      var settings = $.extend({}, defaults, options, $this.data());

      $this.on('click', function() {
        start();
      })


      var start = function () {
        var iconHtml = (settings.saveIcon !== false) ? '<i class="fa fa-spin ' + settings.saveIcon + '"></i> ' : '';
        $this.attr('disabled', 'disabled').html(iconHtml + settings.verbing);

        if($this.hasClass("btn-success")){

          inputNullChk();

          let date = $('#new_date').val(); // 날짜
          let dates = date.split("-"); // 날짜 분리
          let content = $('#new_content').val();
          let importance = false;
          if($('#new_importance').is(':checked')){
            importance = true;
          }


          let node_type = $this.attr("node_type");
          /* 로그 체크 */
          console.log(date);
          console.log(content);
          console.log(importance);
          console.log(node_type);

          /* ajax 입력 */
          $.ajax({
            url : "/career/insert",
            type : "post",
            data : {
              "date" : date,
              "year" :  dates[0],
              "month" : dates[1],
              "day" : dates[2],
              "importance" : importance,
              "nodeType" : node_type,
              "content" : content
            },
            success : function(data) {
              processed();
            }
          });
        }

        // ajax 추가시 delay 없애기
        // setTimeout(function() { processed(); }, settings.delay);

      };
      
      var processed = function() {
        $this.removeAttr('disabled').addClass('completed').html(settings.verbed);



        setTimeout(function() { done(); }, settings.duration);
      };
      
      var done = function() {
        $this.removeClass('completed').html(ogHtml);
        $this.trigger('btn.interaction.done');

        if($this.hasClass("btn-success")){

          input_refresh();
          search($this.attr("node_type"));
        }

      };

    });

  };

})( jQuery, window, document );

// You can define all options as data attributes
jQuery("[data-btn-interaction]").btnInteraction();

// Or however you'd like!
jQuery("[data-event='delete:animate']").btnInteraction({
  verbing: 'Deleting...',
  verbed: 'Deleted'
});
jQuery("[data-event='cancel:animate']").btnInteraction({
  verbing: 'Cancelling...',
  verbed: 'Cancelled'
});
jQuery("[data-event='revise:animate']").btnInteraction({
  saveIcon: 'fa-refresh',
  verbing: 'Revising...',
  verbed: 'Revised'
});
jQuery("[data-event='noicon:animate']").btnInteraction({
  saveIcon: false,
  verbing: 'Processing...',
  verbed: 'Processed'
});

// Example of event trigger :done
jQuery('#btn-save').on('btn.interaction.done', function(e) {
  alert("저장 되었습니다.");
});


// insert 버튼 null check
function inputNullChk(){
  if($('#new_date').val()==null){
    return false;
  }
  if($('#new_content').val()==null || $('#new_content')==''){
    return false;
  }
}


// 조회 검색 기능
function search(type_id){

  //
  let pageId = $('#Career_Id').text();
  let sessionId = $('#session_id').text();

  $.ajax({
    url : "/career/"+pageId+"/search",
    type : "post",
    data : {
      "nodeType":type_id
    },
    dataType : "json",
    success : function(data) {
      console.log(data);

      let innerHTML = "";
      let year = "";
      $.each(data, function(idx, val) {
        if(year!=val.nodeYear){
          year=val.nodeYear;
          innerHTML+='<h3 class="year">'+val.nodeYear+'년</h3>'
        }
        innerHTML+='<div class="grid_row">';
        innerHTML+='<div class="col_month col_center">'+val.nodeMonth+'월</div>';
        innerHTML+='<div class="col_day col_center">'+val.nodeDay+'</div>';
        innerHTML+='<div class="col_content col_center">'+val.nodeContent+'</div>';
        if(pageId==sessionId){
          if(val.importance==true){
            innerHTML+='<div class="col_check col_center"><input type="checkbox" checked onclick="checkbox_chk(this, \''+val.careerRoadNodeId+'\')"></div>';
          }else{
            innerHTML+='<div class="col_check col_center"><input type="checkbox" onclick="checkbox_chk(this, \''+val.careerRoadNodeId+'\')"></div>';
          }
          innerHTML+='<div class="col_del col_center"> <button class="btn btn-danger btn-lg" style="font-size: 1rem;" onclick="delete_node(\''+val.careerRoadNodeId+'\')"><i class="fa fa-trash"></i> Delete</button> </div>';
        }
        innerHTML+='</div>';
      });
      $('#year_info').html(innerHTML);
      /*
      참고 자료
         <h3 class="year">2020년</h3>
          <div class="grid_row">
              <div class="col_month col_center">8월</div>
              <div class="col_day col_center">20일</div>
              <div class="col_content col_center">토익</div>
              <div class="col_check col_center"><input type="checkbox"></div>
              <div class="col_del col_center">
                  <button class="btn btn-danger btn-lg" style="font-size: 1rem;" data-event="delete:animate" id="a"><i class="fa fa-trash"></i> Delete</button>
              </div>
          </div>
      */
    }
  });
}

/* 입력 폼 초기화 */
function input_refresh(){
  // 날짜 초기화
  refreshDate();
  // content 내용 박스 초기화
  $('#new_content').val(null);
  // 체크박스 초기화
  document.getElementById('new_importance').checked=false;
  console.log("입력 폼 초기화 완료 ");
}
function refreshDate(){
  document.getElementById('new_date').valueAsDate = new Date();
}

/*  input 버튼 동작  */
$('.input_button').click(function () {
  $(this).parent().toggleClass('expand');
});

// 모달 오픈
function fnOpenModal(id,type,type_id){

  if(id=='show') {
    /* modal 셋팅 */
    $('#modal_title').text(type);
    $('#btn-save').attr("node_type", type_id);
    input_refresh();
    search(type_id)

  }else if(id=="card"){

    important_search()

  }
  /* 모달 오픈 */
  $('#'+id).css("display", "flex");
}

// 모달 종료
function fnCloseModal(id){
  $('#'+id).css("display", "none");

  if($('#input_container').hasClass('expand')){
    $('#input_container').toggleClass('expand');
  }
}

/* 노드 삭제 */
function delete_node(nodeId){
  console.log(nodeId);

  $.ajax({
    url : "/roadMap/delete",
    type : "post",
    data : {
      "careerRoadNodeId" : nodeId
    },
    success : function(data) {
      let node_type = $('#btn-save').attr("node_type");
      search(node_type);
      alert("삭제 되었습니다.");
    }
  });
}

// 중요 체크 업데이트
function checkbox_chk(chk, nodeId){
  let importance = chk.checked;

  $.ajax({
    url: "/career/chkupdate",
    type: "post",
    data: {
      "careerRoadNodeId": nodeId,
      "importance": importance
    },
    success: function (data) {
      console.log(data);
    }
  })
}

// 중요 체크한것들 조회
function important_search(){
  let pageId = $('#Career_Id').text();
  $.ajax({
    url: "/career/"+pageId+"/important_search",
    type: "post",
    data: {
    },
    dataType : "json",
    success: function (data) {
      console.log(data);
    }
  })
}
