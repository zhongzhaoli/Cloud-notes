jQuery(function($) {
  var $bodyEl = $('body'),
      $sidedrawerEl = $('#sidedrawer');
  
  
  // ==========================================================================
  // Toggle Sidedrawer
  // ==========================================================================
  function showSidedrawer() {
    // show overlay
    var options = {
      onclose: function() {
        $sidedrawerEl
          .removeClass('active')
          .appendTo(document.body);
      }
    };
    
    var $overlayEl = $(mui.overlay('on', options));
    
    // show element
    $sidedrawerEl.appendTo($overlayEl);
    setTimeout(function() {
      $sidedrawerEl.addClass('active');
    }, 20);
  }
  
  
  function hideSidedrawer() {
    $bodyEl.toggleClass('hide-sidedrawer');
  }
  
  
/*  $('.js-show-sidedrawer').on('click', showSidedrawer);*/
  $('.js-show-sidedrawer').on('click',function(){
	  if($(".jj_mb").length){
		  hiden_side($(".jj_mb"));
	  }
	  else{
		  $("<div class='jj_mb' onclick='hiden_side(this)'></div>").appendTo($("body"));
		  $("#sidedrawer")[0].style.transform="translate(280px)";
	  }
  })
  $('.js-hide-sidedrawer').on('click', hideSidedrawer);
  
  // ==========================================================================
  // Animate menu
  // ==========================================================================
  var $titleEls = $('strong', $sidedrawerEl);
  
  $titleEls
    .next()
    .hide();
  
  $titleEls.on('click', function() {
    $(this).next().slideToggle(200);
  });
});
