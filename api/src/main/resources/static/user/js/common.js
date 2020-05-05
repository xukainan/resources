// JavaScript Document
$(function(){


if($(".tip_t").length > 0){

$(".tip_t").bind("selectstart",function(){return false;}); 

$(".tip em").toggle(
  function () {
	if($(this).html()!='合起'){ $(this).html("合起");}else{ $(this).html("展开");}
  $("#fbsm").slideToggle("fast");
  },
  function () {
	if($(this).html()!='合起'){ $(this).html("合起");}else{ $(this).html("展开");}
   $("#fbsm").slideToggle("fast");
  }
);
}

$(".trlist tr").hover( function () {
    $(this).addClass("hover");
  },
  function () {
    $(this).removeClass("hover");
  }
)
	
if($("#appTab").length > 0){
				$("#appTab").append('<div  class="curBg" ></div><div class="cl"></div>');
				
				 liCur = $("#appTab ul li.thisTab");
				  curP = liCur.position().left;
				  curW = liCur.outerWidth(true);
				  slider = $(".curBg");
				  navBox = $("#appTab");
				 targetEle = $("#appTab ul li a");

				slider.animate({
				  "left":curP,
				  "width":curW
				});
				
				targetEle.mouseenter(function () {
				  var $_parent = $(this).parent(),
					_width = $_parent.outerWidth(true),
					posL = $_parent.position().left;
				  slider.stop(true, true).animate({
					"left":posL,
					"width":_width
				  }, "fast");
				});

				navBox.mouseleave(function (cur, wid) {
				  cur = curP;
				  wid = curW;
				  slider.stop(true, true).animate({
					"left":cur,
					"width":wid
				  }, "fast");
				});}

	});
	
	

