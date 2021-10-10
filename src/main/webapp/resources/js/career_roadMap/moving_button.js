(function( $, window, document, undefined ){

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

        // MongoDB 데이터의 아이디 값
        let id = $this.attr('id');

        /* 삭제 버튼일 경우 ajax 삭제 쿼리 동작 */
        if($this.hasClass("btn-danger")){
          /* ajax 삭제 쿼리 */

        }
        // processed();

        // ajax 추가시 delay 없애기
        setTimeout(function() { processed(); }, settings.delay);

      };
      
      var processed = function() {
        $this.removeAttr('disabled').addClass('completed').html(settings.verbed);



        setTimeout(function() { done(); }, settings.duration);
      };
      
      var done = function() {
        $this.removeClass('completed').html(ogHtml);
        $this.trigger('btn.interaction.done');


        /* 삭제 버튼일 경우 동작 */
        if($this.hasClass("btn-danger")){
          // 버튼의 줄요소 가져오기
          let row = $this.parent().parent();
          console.log(row);

          // 부모요소 줄 삭제
          row.remove();

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
  alert("I'm done! event triggered via 'btn.interaction.done'");
});

// 삭제 버튼 동작 완료시 동작
$('.btn-danger').on('btn.interaction.done', function(e){
  alert("삭제 되었습니다.");
})